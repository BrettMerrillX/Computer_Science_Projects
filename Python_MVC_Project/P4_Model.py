#===============================================================================
# P4_Model.py
# Created by Anthony Hornof - 4-28-2016
# Modified by Brett Merrill - 6-1-2016
# Version 0.1
# Implementation for Spring 2016 CIS 211 Project 3
# Derived in part from Dave Kieras' design and implementation of EECS 381 Proj 4
#===============================================================================

from P4_Utility import *

# global constants. (You must define them as global if you intend to re-assign.)
WORLD_STRING = 'world'
WAYPOINT_STRING = 'waypoint'
HUMAN_STRING = 'human'
ROBOT_STRING = 'robot'
FIRE_STRING = 'fire'


class Model:
    '''
    The Model object keeps track of everything in the simulated world.
    Only one Model should be created in each run of the simulation.
    '''

    #===========================================================================
    # Initializer and other Special Functions
    #===========================================================================
    def __init__(self):
        self.__world_size = None
        self._time = 0
        self.__sim_objects = []  # all SimObjects but the Waypoints
        self.__waypoints = []
        self.__humans = []
        self.__robots = []
        self.__fires = []
        global the_model
        the_model = self

        # A pointer to the view in the MVC design. If there were
        # more than one view, this would be a list of all views.
        self.__view = None

    #===========================================================================
    def describe_all(self):
        '''
        Each of the simulation objects describes itself in text.
        ( ) -> None
        '''
        print("The contents of the world are as follows:")
        if self.__world_size:
            print ("The world is of size ", self.__world_size, ".", sep='')
        for i in self.__waypoints:
            print (i)
        for i in self.__humans:
            print (i)
        for i in self.__robots:
            print (i)
        for i in self.__fires:
            print (i)

    #===========================================================================
    # Model-View-Controller coordination methods
    #===========================================================================
    def attach_view(self, v):
        self.__view = v

    #===========================================================================
    def notify_location(self, name, location):
        '''
        The model has been notified, probably by a SimObject, that a SimObject's
        location may have changed.  Broadcast this information to the View.
        '''
        self.__view.update_object(name, location)

    # ===========================================================================
    def update(self): #
        '''
         update all simulation objects in the order that they were created
         advance time by 1
        '''
        for i in self.__sim_objects:
            i.update()
        self._time += 1

    #===========================================================================
    # Creation Methods
    #===========================================================================
    def create_sim_object(self, arg_list):
        '''
        Create a simulation object based on the contents of the arg_list.
        Parameters: arg_list, list of strings entered after "create" command
        Returns:    True for if the line cannot be parsed, False if it can be.

        The only assumption that can be made about the arg_list when entering
        this function is that there was at least one string in the command line
        after "create".
        '''

        MIN_WORLD_SIZE = 5
        MAX_WORLD_SIZE = 30

        # Continue here checking for all of the different objects that "create"
        # could be called to build.  For each, after checking for the string
        # that appeared after "create", make sure that any additional arguments
        # on the line are all permissable given the project specification.


        #=======================================================================
        # World
        if arg_list[0] == WORLD_STRING:
            # Else return error.

            # Verify that there is one additional argument and it is an integer.
            if len(arg_list) == 2 and arg_list[1].isdigit():
                size = int(arg_list[1])
            else:
                raise BadLineError(" ".join(arg_list))

            # If a world has aleady been created
            if (self.__world_size):
                # Then return an error.
                raise BadMsgError('World already exists.')

            # Verify size is in range.
            if size < MIN_WORLD_SIZE or size > MAX_WORLD_SIZE:
                raise BadMsgError("World size is out of range.")
            else:
                # Else create world, and notify all views that the world is
                #   being created."

                print('Creating world of size ', size, '.', sep='')
                # Set the World size member variable.
                self.__world_size = size

                # Update the View object(s) world size.
                self.__view.create(size)

        #=======================================================================
        # Make sure a world exists.
        # If there is no world yet, then there can be no waypoints.
        elif not self.__world_size:
            raise BadMsgError("A world must be created before any other objects.")

        #=======================================================================
        # Waypoint, human, robot, or fire
        # Verify the number and type of arguments, and location.
        elif arg_list[0] in (WAYPOINT_STRING, HUMAN_STRING, ROBOT_STRING, FIRE_STRING):

            # Verify that there are two arguments, two integers each of which
            #   is <= world_size.

            # If there are NOT three more arguments, the second and third being ints...
            if not (len(arg_list) == 4 and arg_list[2].isdigit() and
                        arg_list[3].isdigit()):
                # Then return error flag.
                raise BadLineError(" ".join(arg_list))

            # Convert the two integer arguments into a tuple of integers.
            location = (int(arg_list[2]), int(arg_list[3]))

            # Get the name for all subsequent operations. Store all names as lowercase.
            name = arg_list[1].lower()

            #===================================================================
            # Waypoint

            if arg_list[0] == WAYPOINT_STRING:

                # If the waypoint name is not a single letter
                if not name.isalpha() or not len(name) == 1:
                    raise BadMsgError('Waypoint names must be single letters.')

                # If a waypoint already exists with that name or at that location, report error.
                for w in self.__waypoints:
                    if name == w.get_name() or location == w.get_location():
                        raise BadMsgError(("Waypoint" + str(w.get_name().upper()) + "already exists at location" + str(w.get_location())))

                # Okay, there is NO Waypoint at that location.
                else:

                    # If the location is invalid, return an error.
                    if not the_model.get_valid_location(location):
                        raise BadMsgError("Invalid location.")

                    else:
                        # Create the waypoint! All of the parameters checked out.
                        print("Creating waypoint", name.upper(), "at location", location)
                        # Create the new waypoint, and add it to the list of Waypoints
                        self.__waypoints.append( Waypoint(name, location) )
                        # Update the view with the new waypoint
                        self.__view.add_landmark(name, location)


            #=======================================================================
            # Human, Robot, or Fire
            elif arg_list[0] in (HUMAN_STRING, ROBOT_STRING, FIRE_STRING):

                # If the name is not alphanumeric
                if not arg_list[1].isalnum():
                    raise BadMsgError("Name must be alphanumeric.")

                # If an object already exists with that name.
                if self.get_object(arg_list[1]):

                    # Get a nice pritable class name using __class__.__name__
                    raise BadMsgError((str(self.get_object(arg_list[1]).get_class_name()) + " already exists with that name."))

                # If the location is invalid, return an error.
                if not the_model.get_valid_location(location):
                    raise BadMsgError("Invalid location.")

                # If we got this far, it seems to be a valid input,  so
                #   create an object at the location.
                location = self.get_valid_location(arg_list[2], arg_list[3])
                name = arg_list[1]
                print("Creating ", arg_list[0]," ", name.capitalize(), " at location ", location,".", sep='')

                # For each type of sim object, create the object and add it to its list.
                if arg_list[0] == HUMAN_STRING:
                    # Create the Human
                    new_sim_obj = Human(name, location)
                    # Add the human to the list of Humans.
                    self.__humans.append(new_sim_obj)
                elif arg_list[0] == ROBOT_STRING:
                    # Create the Robot
                    new_sim_obj = Robot(name, location)
                    # Add the robot to the list of SimObjects.
                    self.__robots.append(new_sim_obj)
                else: # arg_list[0] == FIRE_STRING:
                    # Create the Robot
                    new_sim_obj = Fire(name, location)
                    # Add the robot to the list of SimObjects.
                    self.__fires.append(new_sim_obj)

                # Also add the new sim object to the list of all SimObjects as well.
                self.__sim_objects.append(new_sim_obj)

                # Update the view with the new Sim Object
                self.__view.update_object(name, location)

            else:
                raise BadLineError(" ".join(arg_list))

        else:
            raise BadLineError(" ".join(arg_list))

    #===========================================================================
    # More Complex Accessor Methods
    #===========================================================================

    #===========================================================================
    def get_waypoint_location(self, name):
        '''
        # Takes a name string.  Looks for a waypoint with that name.  If one exists,
        #   returns its location.  If one does not, returns None.

        Parameters: name, a string
        Returns:    Either a location of a Waypoint, or False
        '''

        for i in self.__waypoints:
            if i.get_name() == name:
                return i.get_location()
        else:
            return None

    # ===========================================================================
    def fire_at_location(self, location):
        for i in self.__fires:
            if i.get_location() == location:
                return i
        else:
            return None
    #===========================================================================
    def get_human(self, name):
        '''
        Takes a name string.  Looks for a human with that name.  If one exists,
            returns that human.  If one does not, returns None.
        '''
        for i in self.__humans:
            if i.get_name() == name:
                return i
        else:
            return None

    #===========================================================================
    def get_robot(self, name):
        '''
        Takes a name string.  Looks for a robot with that name.  If one exists,
            returns that robot.  If one does not, returns None.
        '''
        for i in self.__robots:
            if i.get_name() == name:
                return i
        else:
            return False

    #===========================================================================
    def get_object(self, name):
        '''
        Takes a name string.  Looks for any sim object with that name.  If one
            exists, returns that sim object.  If one does not, returns None.
        '''
        for i in self.__sim_objects:
            if i.get_name() == name:
                return i
        else:
            return False

    # ===========================================================================
    def get_fire(self, name):
        for i in self.__fires:
            if i.get_name() == name:
                return i
        else:
            return False

    # ===========================================================================
    def get_time(self):
        return self._time

    #===========================================================================
    def get_valid_location(self, arg1, arg2=None):
        # Possibly change to get_location() and have it always return a tuple of
        # two ints
        '''
        Determine if a location is in the world.  If yes, returns a tuple of ints.
        This function is made polymorphic by using "switch on type".

        Parameters: arg1 and arg2 are ints, OR
                    arg1 and arg2 are strings, OR
                    arg1 is a tuple of two ints, and no arg2 is provided, OR
                    arg1 is a tuple of two strings, and no arg2 is provided
        Returns:    a tuple of ints if the location is in the world
                    None otherwise.

        Examples of use if the world is of size 30:
        self.get_valid_location(10, 20) -> (10, 20)
        self.get_valid_location('10', '20') -> (10, 20)
        self.get_valid_location((10, 20)) -> (10, 20)
        self.get_valid_location('a', '20') -> None
        self.get_valid_location(1.0, 20) -> None
        '''

        # Switch on type.  If arguments are...

        # (int, int)
        if type(arg1) == type(arg2) == int:
            x = arg1
            y = arg2

        # (str, str)
        elif type(arg1) == type(arg2) == str and arg1.isdigit() and arg2.isdigit():
            x = int(arg1)
            y = int(arg2)

        # (tuple of two ints)
        elif (arg2 == None and type(arg1) == tuple and len(arg1)==2 and
                type(arg1[0])==int and type(arg1[1])==int):
            x = arg1[0]
            y = arg1[1]

        # (tuple of two strings which can convert into digits)
        elif (arg2 == None and type(arg1) == tuple and len(arg1)==2 and
                type(arg1[0])==str and type(arg1[1])==str and
                arg1[0].isdigit() and arg1[1].isdigit()):
            x = int(arg1[0])
            y = int(arg1[1])

        # Arguments not handled, or invalid location.
        else:
            return None # The provided arguments are not handled.

        # If the location is in the world, return True.
        if (x >= 0 and y >= 0 and
                x <= self.__world_size and y <= self.__world_size):
            return (x, y)
        else:
            return None

    #===========================================================================
    def delete_fire(self, name):
        for i in self.__fires:
            num = self.__fires.index(i)
            if i.get_name() == name:
                del self.__fires[num]
        for i in self.__sim_objects:
            num2 = self.__sim_objects.index(i)
            if i.get_name() == name:
                del self.__sim_objects[num2]
        self.__view.update_object(name, None)

#===============================================================================
# OTHER CLASSES
#===============================================================================

#===============================================================================
class SimObject:

    def __init__(self, name, location):
        # Private member variables.
        self._name = name.lower()
        self._location = location  # a tuple of integers

    def __str__(self):
        return self._name.capitalize() + " at location " + str(self._location)

    def get_name(self):
        return self._name

    def get_class_name(self):
        ''' Returns 'Human', 'Robot', or 'Fire' as appropriate.'''
        return self.__class__.__name__

    def get_location(self):
        return self._location

#===============================================================================
class Waypoint (SimObject):
    '''
    A Waypoint in the simulation.
    '''

    def __str__(self):
        return "Waypoint " + super().__str__()

#===============================================================================
class Fire (SimObject):
    '''
    A Waypoint in the simulation.
    '''

    def __init__(self, name, location):
        # Private member variables.
        self._strength = 5
        super().__init__(name, location)

    def __str__(self):
        return "Fire: Fire " + super().__str__() + " of strength " + str(self.get_strength())

    def __del__(self):
        if self.get_strength() == 0:
            print("Fire ", self.get_name()," has disappeared from the simulation.")

    def get_strength(self):
        return self._strength

    def reduce_strength(self):
        self._strength = self._strength - 1
        # if self.get_strength == 0:
        #     the_model.delete_fire(self.get_name())
    def update(self):
        '''
        Does nothing.
        '''
        pass


#===============================================================================
class Traveler (SimObject):

        # Update the view
    def __init__(self, name, location):
        super().__init__(name, location)
        self._destination_list = []
        self._moving = False

    def stop(self):
        self._moving = False
        self._destination_list = []
    def set_moving(self):
        self._moving = True

    def move_to(self, location):

        if self._destination_list == []:
            self._moving = False
            if self.get_class_name() == 'robot':
                self.stop_fighting_fire()
        elif (location[0] == self._destination_list[0][0]) and (location[1] == self._destination_list[0][1]):
            self._destination_list.remove(self._destination_list[0])
            print(self.get_class_name(), " ", self.get_name().capitalize(), " arrived at location ",
                  location, '.', sep='')
            if self.get_class_name() == 'robot':
                self.stop_fighting_fire()

            if len(self._destination_list) == 0:
                self.stop()
                if self.get_class_name() == 'robot':
                    self.stop_fighting_fire()


        # Change the object's location
        # self._location = location  # a tuple of integers

        # Notify the Model that an object of this name now has this location.
        # the_model.notify_location(self._name, location)

    def journey_to(self, destination_list):
        '''
        Sets the move to
        '''
        if self.get_class_name() == 'robot':
            self.stop_fighting_fire()

        if len(destination_list) == 1:
            current_location = self.get_location()
            self.check_location_ability(current_location, destination_list[0])

        elif len(destination_list) > 1:
            current_location = self.get_location()
            self.check_location_ability(current_location, destination_list[0])

            for i in range(len(destination_list[1:])):
                self.check_location_ability(destination_list[i],destination_list[i+1])
        print(self.get_class_name(), " ", self.get_name().capitalize(), " at location ", self.get_location(),
              ' moving to ', destination_list, '.', sep='')
        self._moving = True

    def check_location_ability(self, location, destination):
        '''
        Checks if the destination only moves in one direction
        so the human or robot is able to move.
        '''
        if the_model.get_valid_location(destination):
            if location[0] == destination[0]:
                if location[1] == destination[1]:
                    raise BadMsgError((str(destination), " is not validx for this 'move'."))
                else:
                    self._destination_list.append(destination)
            elif location[1] == destination[1]:
                if location[0] == destination[0]:
                    raise BadMsgError((str(destination), " is not validy for this 'move'."))
                else:
                    self._destination_list.append(destination)
            else:
                raise BadMsgError((str(destination), " is not validz for this 'move'."))
        else:
            raise BadMsgError((str(destination) + " is not validb for this 'move'."))

    def get_next_moving_location(self):
        if self._moving == True:
            next_location = self._destination_list[0]
            current_location = self._location
            if next_location[0] == current_location[0]:
                if next_location[1] > current_location[1]:
                    return (current_location[0],(current_location[1] + 1))
                if next_location[1] < current_location[1]:
                    return (current_location[0],(current_location[1] - 1))
                if next_location[1] == current_location[1]:
                    return current_location
            if next_location[1] == current_location[1]:
                if next_location[0] > current_location[0]:
                    return ((current_location[0] + 1), current_location[1])
                if next_location[0] < current_location[0]:
                    return ((current_location[0] - 1), current_location[1])
                if next_location[0] == current_location[0]:
                    return current_location
            else:
                return None


#===============================================================================
class Human (Traveler):
    '''
    A human in the simulation.
    '''

    def __str__(self):
        if self._moving:
            return "Moving: Human " + super().__str__() + " moving to " + str(self._destination_list)
        else:
            return "Stopped: Human " + super().__str__()

    def update(self):
        if self._moving:
            new_location = self.get_next_moving_location()
            if the_model.fire_at_location(new_location):
                print(self.get_name().capitalize(), ' arrived at location ', self._location)
                print(self.get_name().capitalize(), " stopping short of fire ",the_model.fire_at_location(new_location).get_name().capitalize() )
                self.stop()
            else:
                self._location = new_location
                self.move_to(new_location)
                the_model.notify_location(self._name, self._location)

#===============================================================================
class Robot (Traveler):
    '''
    A robot in the simulation.
    '''
    def __init__(self, name, location):
        # Private member variables.
        # self._carrying_human = None
        # self._water_capacity = 100 # gallons
        # self._water_level = 0
        self._extinguishing_fire = None
        super().__init__(name, location)

    def __str__(self):
        if self._moving:
            return "Moving: Robot " + super().__str__() + " moving to " + str(self._destination_list)
        elif self._extinguishing_fire:
            return "Extinguishing: Robot " + super().__str__() + " extinguishing fire " + self._extinguishing_fire.get_name()
        else:
            return "Standing by: Robot " + super().__str__()

    def fight_fire(self, fire_object):
        self._extinguishing_fire = fire_object

    def stop_fighting_fire(self):
        self._extinguishing_fire = None

    def update(self):
        if self._moving:
            new_location = self.get_next_moving_location()
            self._location = new_location
            if the_model.fire_at_location(new_location):
                self.move_to(new_location)
                the_model.notify_location(self._name, self._location)
                # self.fight_fire(the_model.fire_at_location(new_location))
                self.stop()
                self.stop_fighting_fire()
            else:
                self.move_to(new_location)
                the_model.notify_location(self._name, self._location)
        elif self._extinguishing_fire:
            self._extinguishing_fire.reduce_strength()
            if self._extinguishing_fire.get_strength() == 0:
                the_model.delete_fire(self._extinguishing_fire.get_name())
                self._extinguishing_fire = None
        elif self._extinguishing_fire is None:
            self.stop_fighting_fire()
            self.stop()
