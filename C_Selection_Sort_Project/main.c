//
//  main.c
//  BrettMerrillAssignment3
//
//  Created by Brett Merrill on 2/1/17.
//  Copyright © 2017 Brett Merrill. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>

int main(int argc, const char * argv[]) {
    
    // variables to be used
    int position, arrayLen, arrayNum, i, j, swap;
    int *array;
    
    // Asks user to create an array
    printf("Enter an integer array length: ");
    scanf("%d", &arrayLen);
    printf("%d\n",arrayLen);
    // allocates momory for the array with desired size
    array = (int*) malloc(sizeof(int) * arrayLen);
    for (i = 0; i<arrayLen; i++){
        printf("Enter Number: ");
        scanf("%d", &arrayNum);
        array[i] = arrayNum;
    }
    // Selection Sort
    for(i = 0 ; i < arrayLen - 1 ; i++){
        position = i;
        for(j = i+1; j<arrayLen; j++){
            if (array[position] > array[j]){
                position = j;
            }
        
        }
        if(position != i){
            swap = array[i];
            array[i] =array[position];
            array[position] = swap;
        }
        
    }
    // Prints the input array
    for(i = 0; i<arrayLen;i++){
        printf("%d\n", array[i]);
    }
    
    free(array);
    
    return 0;
}
