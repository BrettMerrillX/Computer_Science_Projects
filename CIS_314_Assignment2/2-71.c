//
//  2-71.c
//  BrettMerrillAssignment2
//
//  Created by Brett Merrill on 1/27/17.
//  Copyright © 2017 Brett Merrill. All rights reserved.
//

#include <stdio.h>
// Part A. 1
// His solution did not return a 32-bit int.

typedef unsigned packed_t;
int xbyte(packed_t word, int bytenum);

int main(int argc, const char * argv[]) {
    printf("0x""%X\n",xbyte(0x11AA3388, 2));
    printf("0x""%X\n",xbyte(0x11AA3388, 1));
    return 0;
}

int xbyte(packed_t word, int bytenum) {
    int byte = (word >> (bytenum<<3))<<24;  // bit shifts the input word to get the desired set
    return byte>>24;
}

