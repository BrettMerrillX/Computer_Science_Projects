//
//  2-68.c
//  BrettMerrillAssignment1
//
//  Created by Brett Merrill on 1/19/17.
//  Copyright © 2017 Brett Merrill. All rights reserved.
//

#include <stdio.h>
int lower_one_mask(int n);

int main() {
    // test cases 
    printf("%d\n",lower_one_mask(1));
    printf("%d\n",lower_one_mask(2));
    printf("%d\n",lower_one_mask(3));
    printf("%d\n",lower_one_mask(5));

}


int lower_one_mask(int n){
    // Shifts the binary value of 2 to the left n-1
    // times and subtracts one to compensate for
    // the start at 2 instead of 1
        
    return (2<<(n-1)) -1;
}