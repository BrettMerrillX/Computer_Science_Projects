//
//  3-62C.c
//  BrettMerrillAssignment4
//
//  Created by Brett Merrill on 2/13/17.
//  Copyright © 2017 Brett Merrill. All rights reserved.
//

#include <stdio.h>

// 2.C)

#define M 13
typedef int Marray_t[M][M];

// transposes the input matrix
void transpose(Marray_t A){
    int i,j;
    for (i=0; i<M; ++i){
        int *A1 = &A[i][0];
        int *A2 = &A[0][i];
        
        for (j=0; j< i; ++j){
            // swaps  and increments pointers
            int t = *A1;
            *A1 = *A2;
            *A2 = t;
            ++A1;
            A2 += M;
        }
    }
}


int main(int argc, const char * argv[]) {
    
    
    //Sample array
    Marray_t array = {{1,2,3,4},{5,6,7,8},{9,1,2,3}};
    
    int i,j;
    //Prints the original generated array
    printf("Original\n");
    for(i=0; i < M ; ++i){
        for(j=0; j < M ; ++j){
            printf("%d",array[i][j]);
        }
        printf("\n");
    }
    printf("\n");
    
    //Prints the transposed array
    printf("Transposed\n");
    transpose(array);
    for(i=0; i < M ; ++i){
        for(j=0; j < M ; ++j){
            printf("%d",array[i][j]);
        }
        printf("\n");
    }
    printf("\n");
    
    return 0;
}








