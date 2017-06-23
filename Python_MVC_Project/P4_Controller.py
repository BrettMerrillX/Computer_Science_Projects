#===============================================================================
# P4_Controller.py
# Created by Anthony Hornof - 4-28-2016
# Modified by Brett Merrill - 6-1-2016
# Version 0.1
# Implementation for Spring 2016 CIS 211 Project 3
# Derived in part from Dave Kieras' design and implementation of EECS 381 Proj 4
#===============================================================================

import sys  # for argv

import P4_Model
import P4_View
from P4_Utility import *


#===============================================================================
def main ():
#===============================================================================
# Create a Model object and assign it to a global variable called "the_model"
# Create a local instance of a Controller and have it start the run() function.

    c = Controller()
    c.run()

#===============================================================================
class Controller:
#===============================================================================
    '''
    The controller object handles user keyboard input and provides text output to
    the console.  It follows the model view-controller software design pattern.
    '''

    #===========================================================================
    def __init__(self):

        # Local "private" member variables
        # global the_model
        self.__the_model = P4_Model.Model()

        # Create the View object and attach it to the Model.
        self.__view = P4_View.View()
        self.__the_model.attach_view(self.__view)

        # File with list of commands to be executed (initialized to default).
        self.__input_filename = "commands.txt"

        # File object currently being read from.  This is also used to indicate
        #   whether the system is currently getting lines from the user or a file.
        self.__input_file_object = None


    #===========================================================================
    def run(self):
    #===========================================================================
        '''
        () -> None.
        Process the command lines for the human-robot simulation.
        '''

        print("Starting Human-Robot Interaction Simulation.")


        # Attempt to open an input file for an initial set of commands
        self.open_initial_input_file()

        #=======================================================================
        # Command loop
        while True:
            try:
                # Get the next line of input whether it is from the user or a file.
                line = self.get_next_input_line()
                line_list = line.lower().split()

                # If there are no arguments to process, just loop.
                if len(line_list) == 0:
                    continue    # Loop to the top of the while loop.

                #=======================================
                # In this loop, make sure that:
                # 1. The first word of the command line is viable, such as that the
                #    command truly exists, or the human truly exists.
                # 2. The number of arguments that come after the first word are the
                #    correct number that would be needed given the first word, or
                #    provide at least the minimum number of arguments that could be
                #    needed based on the first word in the line.
                # Two exceptions:
                # 3. For 'ee',  confirm there are no additional arguments.
                # 4. For 'quit', process the entire command within the loop.

                # If the command is...

                #=======================================
                # '<human>' or '<robot>' command - tell a human object to do something
                if self.__the_model.get_human(line_list[0]) or self.__the_model.get_robot(line_list[0]):
                    self.do_human_robot_command(line_list)

                #=======================================
                # 'create'
                elif line_list[0]=='create' and len(line_list) > 2:     # Change 2 to 3 for P3.
                    # Send the arguments to the Model's create object function.
                    if self.__the_model.create_sim_object(line_list[1:]):
                        # If an error
                        raise BadLineError(line)
                        # print("Unrecognized command:", line)

                #=======================================
                # 'status'                      (Make sure there are NO args.)
                elif line_list[0]=='status' and len(line_list) == 1:
                    # Get and print a text string from the Model of each
                    #   simulation object describing itself.
                    self.__the_model.describe_all( )

                #=======================================
                # 'show'
                elif line_list[0]=='show':
                    if self.do_show_command(line_list[1:]):
                        raise BadLineError(line)
                        # print("Unrecognized command:", line)


                # =======================================
                # 'go'
                elif line_list [0]=='go':
                    self.__the_model.update()


                #=======================================
                # 'open'
                elif line_list[0]=='open' and len(line_list) == 2:
                    # Overwrite the input-filename member-variable.
                    self.__input_filename = line_list[1]
                    # Attempt to open the file.
                    self.open_input_file()

                #=======================================
                # 'quit'
                # Prompt again and quit.
                elif line_list[0] == 'quit' and line_list[1:]==[]:
                    print("Are you sure you want to quit? (Y/N) ", end='')
                    line = self.get_next_input_line()
                    if line.lower() == 'y':
                        break

                #=======================================
                # 'q'
                # A hidden convenient fast way to quit.  Remove before distributing or testing.
                elif line_list[0] == 'q' and line_list[1:]==[]:
                    break

                #=======================================
                # Unrecognized command
                else:
                    raise BadLineError(line)
                    # print("Unrecognized command:", line)

            except BadLineError as L:
                print(L)

            except BadMsgError as M:
                print(M)


    #===========================================================================
    # Manage the command line input file
    #===========================================================================

    def get_next_input_line(self):
        '''
        ( ) -> string
        • Displays the prompt.
        • Returns the next line to be processed, or '' if there is no line.
        • Gets the next line of text either from an input file or from the user,
          depending on the current setting of current_input_mode.
        • When reading from an input file, and either a blank line or an end of file
          is encountered, close the input file and set the file object var to None.
        '''

        # If there is currently a command-line input file open, then use it.
        if self.__input_file_object:

            # Get the line.
            getline = self.__input_file_object.readline().strip()

            # If it is not empty, show it and return it.
            if getline:
                print ('Time',self.__the_model.get_time(), "FILE>", getline)
                return getline
            else:
                # Else close the file and set self.__input_file_object to None.
                self.__input_file_object.close()
                self.__input_file_object = None
                print ("Closing file.")
                return ''

        # Else, input is currently being provided by the user's keystrokes.
        else:

            # Issue the prompt
            print("> ", end='')

            # Get and return the command line from the user.
            return input()


    #===========================================================================
    def open_initial_input_file (self):
        '''
        Attempt to open a file for an initial set of commands.
        ( ) -> None
        If a filename was entered as a command line argument, overwrite the
          controller's member variable with that new filename.
        '''

        # If a filename was entered as an argument, overwrite the default initial
        #   input filename with this new filename.
        if len(sys.argv) > 1 and sys.argv[1]:
            self.__input_filename = sys.argv[1]
        # Attempt to open the input file.
        self.open_input_file( )


    #===========================================================================
    def open_input_file (self):
        '''
        ( ) -> None
        Attempts to open the filename in the input file member variable to
          execute a set of commands.
        '''

        # If an initial file exists and is readable, open it.
        try:
            self.__input_file_object = open(self.__input_filename)
        # Handle the error if the file does not exist.
        except FileNotFoundError:
            print ("File not found:", self.__input_filename)
        # Handle the error if the user does not have permission to read the file.
        except PermissionError:
            print ("You do not have permission to open file:", self.__input_filename)
        # If no exceptions were raised....
        else:
            print ("Reading file:", self.__input_filename)

        # Note that we specifically do NOT check for IsADirectoryError because
        # we will save that one for testing, to make sure it crashes the program.


    #===========================================================================
    # Execute commands
    #===========================================================================

    def do_human_robot_command(self, args):
        '''
        Parameters: args, a list of arguments that is already confirmed to be
                          nonempty with the first argument a human in the model.
        Returns:    None (All errors are reported within, so no need to return
                          an error flag.)

        Processes the remainder of the arguments to insure that they at least
        represent valid waypoints or locations on the map.  If they are valid,
        call the appropriate function calls in the model to build them.
        '''

        # Keep a pointer to the human or robot
        sim_obj = self.__the_model.get_object(args[0])

        # Make sure the rest of the command is in a correct form.

        #=======================================================================
        if len(args) > 1 and args[1] == 'move':

            args_copy = args[2:]
            location_list = []
            count = 0
            for i in args_copy:
                # Checks for valid waypoints
                if len(i) == 1:
                    way_point_check = (self.__the_model.get_waypoint_location(i))
                    if way_point_check is None:
                        raise BadMsgError(i + " is not a valid locationx for this to 'move'")
                    else:
                        location_list.append(way_point_check)

                    # Appends tuple location to location_list
                # Checks for valid location inputs
                else:
                    x = i.split(',')
                    if len(x) == 2:
                        if self.__the_model.get_valid_location(x[0],x[1]):
                            # Appends tuple location to the location_list
                            location_list.append(self.__the_model.get_valid_location(x[0],x[1]))
                            count += 1
                    else:
                        # Error involving bad location or non-existing waypoint
                        raise BadMsgError(+ " is not a valid location for this to 'move'")

                # If it is a valid location, move the human to it.
            if len(location_list) == len(args_copy):
                # Then move the human to that location.
                sim_obj.journey_to(location_list)
            else:
                raise BadMsgError("Invalid location.")

            # # move <waypoint>
            # if len(args) == 3:
            #     # Get the location of the waypoint.
            #     location = self.__the_model.get_waypoint_location(args[2])   # Could be None
            #
            # # move <x> <y>
            # elif len(args) == 4:
            #     # Create a location tuple from the final two args
            #
            #     location = (args[2], args[3])
            #
            # # Invalid move command.
            # else:
            #     raise BadMsgError("Invalid move command.")

        elif self.__the_model.get_robot(args[0]) and args[1] == 'attack':
            if args[2] == (self.__the_model.get_fire(args[2]).get_name()):
                if (self.__the_model.get_fire(args[2]).get_location()) == self.__the_model.get_robot(args[0]).get_location():
                    self.__the_model.get_robot(args[0]).stop()
                    self.__the_model.get_robot(args[0]).fight_fire(self.__the_model.get_fire(args[2]))
                    print('Robot ', args[0] + ' at location ', self.__the_model.get_robot(args[0]).get_location(), 'fighting fire ',args[2] )
                else:
                    raise BadMsgError('Robot '+ args[0] + ' is not in the same location as fire ' + args[2])
            else:
                raise BadMsgError('Fire '+ args[2] + 'does not exist')
        #=======================================================================

        # To maintain consistencey with Project 2 error messages.
        else:
            if type(sim_obj) == P4_Model.Human:
                raise BadMsgError("Invalid human command.")
            elif type(sim_obj) == P4_Model.Robot:
                raise BadMsgError("Invalid robot command.")
            else:
                raise BadMsgError("Error: Invalid command.")


    #===========================================================================
    def do_show_command(self, arg_list):
        '''
        Instruct the View to display objects on the grid.
        Parameters: arg_list, list of strings entered after "show" command
        Returns:    True if the line CANNOT be parsed, False if it can be.
        '''

        # If there were no arguments
        if len(arg_list) == 0:
            # Plot everything
            self.__view.draw()

        else:
            # Either the argument did not match, or there were too many arguments.
            raise BadLineError(arg_list)


#===============================================================================
main ()
#===============================================================================

