//
//  3-54.c
//  BrettMerrillAssignment3
//
//  Created by Brett Merrill on 2/1/17.
//  Copyright © 2017 Brett Merrill. All rights reserved.
//

#include <stdio.h>

int decode(int x, int y, int z);

int main(int argc, const char * argv[]) {
    printf("%d\n", decode(1,2,4));
    printf("%d\n", decode(-4,-8,-12));
    return 0;
}

int decode(int x, int y, int z){
    return  (x * (y-z)) ^ ((y-z) << 31 >> 31);
}

//1 movl    12(%ebp), %edx   y into %edx
//2 subl   16(%ebp), %edx   computes y-z
//3 movl   %edx, %eax       y-z into %eax
//4 sall   $31, %eax        (y-z) <<= $31
//5 sarl   $31, %eax        (y-z) >>= $31
//6 imull  8(%ebp), %edx    x * (y-z)
//7 xorl   %edx, %eax       x * (y-z) ^ ((y-z) <<= $31 >> $31
