//
//  3-69A.c
//  BrettMerrillAssignment4
//
//  Created by Brett Merrill on 2/13/17.
//  Copyright © 2017 Brett Merrill. All rights reserved.
//

#include <stdio.h>
//3.
//A.

//typedef 
typedef struct ELE *tree_ptr;

struct ELE {
    
    long val;
    tree_ptr left;
    tree_ptr right;
};

//Initializes trace
long trace(tree_ptr);

int main(int argc, const char * argv[]) {
    
    //Creates a tree node
    tree_ptr TP;
    TP->val = 199;
    TP->left = NULL;
    TP->right = NULL;
    trace(TP);
    
    
    return 0;
}
long trace(tree_ptr tp){
    
    long val1 = 0;
    //Checks for null tree
    if(tp != NULL){
        //Runs until tp is empty
        while(tp != NULL){
            //replaces val with the new val
            val1 = tp->val;
            tp = tp->left;
            
        }
    }else{
        val1 = 0;
        
    }
    return val1;
    
}
//B.   The function will return the value of the left-most node

