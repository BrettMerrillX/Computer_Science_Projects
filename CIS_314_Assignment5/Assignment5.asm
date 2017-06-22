.pos 0
Init:
    irmovl Stack, %ebp
    irmovl Stack, %esp
    call Main
    halt
    

## int f(int x) 
f:
    pushl %ebp
    rrmovl %esp, %ebp
    mrmovl 8(%ebp), %ebx
    rrmovl %ebx, %edx
    addl %ebx,%ebx
    addl %edx, %ebx
    rrmovl %ebx, %eax
    popl %ebp
    ret


g:
    ## uses %ebx and %
    pushl %ebp
    rrmovl %esp, %ebp
    mrmovl 8(%ebp), %ebx
    pushl %ebx
    call f 
    rrmovl %eax, %ecx
    mrmovl 12(%ebp), %ebx
    pushl %ebx
    call f
    rrmovl %eax, %edx
    addl %edx, %ecx
    rrmovl %ecx, %eax
    popl %ebp
    ret
    
   ## mrmovl 

    
Main:
    
     ## function Prolougue 
    pushl %ebp
    rrmovl %esp, %ebp
    pushl %ebx
    
    
    ##body
    irmovl $3, %ecx
    pushl %ecx
    irmovl $5, %edx
    pushl %edx
    
    call g
    
    
    

    
    
    ##Function Epilogue 
    popl %ebx
    popl %ebp
    ret
    
    
.pos 0x100
Stack:
    