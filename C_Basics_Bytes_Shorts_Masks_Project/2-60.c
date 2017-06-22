//
//  2-60.c
//  BrettMerrillAssignment1
//
//  Created by Brett Merrill on 1/19/17.
//  Copyright © 2017 Brett Merrill. All rights reserved.
//

#include <stdio.h>
unsigned replace_byte(unsigned x, int i, unsigned char b);
int main() {
    printf("0x");
    printf("%X\n",replace_byte(0x12345678,2,0xAB));
    printf("0x");
    printf("%X\n",replace_byte(0x12345678,0,0xAB));
}

unsigned replace_byte(unsigned x, int i, unsigned char b){
    // shifts the desired char into the correct position
    int shift = (b << (8 *i));
    // shifts the mask FF into the right position
    int mask = 0xff << (8 *i);
    // compares x to a ~mask which will change the
    // desired position to zero and then fill it
    // with the the unsigned char b.
    return (~mask & x) | shift;
}