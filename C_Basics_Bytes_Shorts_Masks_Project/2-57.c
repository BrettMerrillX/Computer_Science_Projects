//
//  2-57.c
//  BrettMerrillAssignment1
//
//  Created by Brett Merrill on 1/19/17.
//  Copyright © 2017 Brett Merrill. All rights reserved.
//

#include <stdio.h>

void show_short(short int x);
void show_long(long int x);
void show_double(double);

int main() {
    
    show_short(1);
    show_long(1);
    show_double(1);
}
typedef unsigned char *byte_pointer;
void show_bytes(byte_pointer start, int len){
    int i;
    for(i = 0 ; i < len; i++)
        printf(" %.2x", start[i]);
    printf("\n");
}
void show_short(short int x){
    // show short int byte representation of C object
    show_bytes((byte_pointer) &x, sizeof(short int));
}
void show_long(long int x){
    // show long int byte representation of C object
    show_bytes((byte_pointer) &x, sizeof(long int));
}
void show_double(double x){
    // show double byte representation of C object
    show_bytes((byte_pointer) &x, sizeof(double));
}