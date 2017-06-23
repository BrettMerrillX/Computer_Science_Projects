#===============================================================================
# P4_Utility.py
# Created by Brett Merrill - 6-1-2016
# Version 0.2
# Implementation for Spring 2016 CIS 211 Project 3
# Derived in part from Dave Kieras' design and implementation of EECS 381 Proj 4
#===============================================================================
class BadLineError(Exception):
    def __init__(self, Line):
        self.Line = Line

    def __str__(self):
        return "Unrecognized command: " + str(self.Line)


class BadMsgError(Exception):
    def __init__(self, message):
        self.message = message
    def __str__(self):
        if type(self.message) == list:
            for i in self.message:
                print(i, end='')
        return 'Error: ' + str(self.message)