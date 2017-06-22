//
//  2-83.c
//  BrettMerrillAssignment2
//
//  Created by Brett Merrill on 1/27/17.
//  Copyright © 2017 Brett Merrill. All rights reserved.
//

#include <stdio.h>
int float_le(float x, float y);
unsigned f2u(float f) ;
int main(int argc, const char * argv[]) {
    printf("%X\n",float_le(1., 2.));
    printf("%X\n",float_le(-5., 5.));
    printf("%X\n",float_le(12.375, 11.375));
    return 0;
}


unsigned f2u(float f) {
    return *((unsigned*)&f);
}

int float_le(float x, float y) {
    unsigned ux = f2u(x);
    unsigned uy = f2u(y);
    /* Get the sign bits */
    unsigned sx = ux >> 31;
    
    unsigned sy = uy >> 31;
    
    /* Give an expression using only ux, uy, sx, and sy */
    return (uy << 1==0 && ux << 1 ==0) ||  // when both are zero
    (sx == 1 &&  sy == 0) || // when x is negative
    ( !sx && !sy && ux<=uy) || // when x >= 0, y >=0
    ( sx && sy && ux>=uy); } // when x < 0, y <0
