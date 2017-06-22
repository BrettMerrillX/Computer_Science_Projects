//
//  2-64.c
//  BrettMerrillAssignment1
//
//  Created by Brett Merrill on 1/19/17.
//  Copyright © 2017 Brett Merrill. All rights reserved.
//


#include <stdio.h>
int any_odd_one(unsigned x);
int main() {
    // Test cases
    printf("%X\n",any_odd_one(0x0));
    printf("%X\n",any_odd_one(0x1));
    printf("%X\n",any_odd_one(0x2));
    printf("%X\n",any_odd_one(0x3));
    printf("%X\n",any_odd_one(0xFFFFFFFF));
    printf("%X\n",any_odd_one(0x55555555));
}


int any_odd_one(unsigned x){
    // Since A accounts for all odd bits we can compare
    // x to A and it will result in a 1 if x has a 1
    // anywhere in it.
    return (x&0xAAAAAAAA) !=0;
}