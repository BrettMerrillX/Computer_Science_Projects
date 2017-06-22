//
//  2-72.c
//  BrettMerrillAssignment2
//
//  Created by Brett Merrill on 1/27/17.
//  Copyright © 2017 Brett Merrill. All rights reserved.
//

#include <stdio.h>
#include <string.h>
void copy_int(int val, void *buf, int maxbytes);

int main(int argc, const char * argv[]) {
    int a = 3;
    int array[5];
    
    copy_int(a, array, 5);	//this should get in to the if statement
    copy_int(a, array, 3);	//this should FAIL to get in to the if statement
    return 0;
}
void copy_int(int val, void *buf, int maxbytes) {
    if (maxbytes >0 && maxbytes >= sizeof(val))
        memcpy(buf, (void *) &val, sizeof(val));
}
// Part A. 2
// The comparison of unsigned expression is always true
