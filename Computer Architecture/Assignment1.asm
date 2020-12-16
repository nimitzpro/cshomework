.data
	prompt: .asciiz "Enter an integer : "
	intresp: .asciiz "You entered : "
	binaryresp: .asciiz "\nThe binary representation of your integer is: "
	errorresp: .asciiz "Please run this again and enter an integer. \n"
	inputStr: .space 32
	str2Int: .word 0
	gap: .asciiz " "
	
.globl main

.text
main:
	la $a0, prompt
	li $v0, 4
	syscall
	
	li $v0, 8
	la $a0, inputStr # inputStr Ready
	li $a1, 32
	syscall
	
	la $t9, ($sp) # %t9 holds initial sp location
	
	la $s0, inputStr # $s0 now holds the string address
	
	li $s3, 1 # Holds positive or negative value, default positive
	
	li $s6, 0 # Registers to flip the bits if needed
	li $s7, 1
	li $t4, 0
	li $t7, 0
	
	lb $t1, ($s0)
	beq $t1, 45, setnegative
	j loop

setnegative:
	li $s3, -1
	add $s0, $s0, 1
	j loop

error:
	la $a0, errorresp
	li $v0, 4
	syscall
	j exit

loop:
    	lb $s1, ($s0)    # $s1 holds char
    	
    	beq $s1, 10, next # Exit if done
    	    	
 	sub $t5, $s1, 48
    	blt $t5, 0, error
    	bgt $t5 9, error
    	
	
	sub $s1, $s1, 48
	
	mul $s2, $s2, 10
	
	add $s2, $s2, $s1 # $s2 holds int
	
	add $s0, $s0, 1
	j loop
	
next:
	blt $s3, $zero, negative
	move $t0, $s2
	j next2
	
negative:
	move $t0, $s2
	mul $t0, $t0, -1
	j next2
next2:
	sw $t0, str2Int # str2Int
	la $a0, intresp
	li $v0, 4
	syscall
	lw $a0, str2Int
	li $v0, 1
	syscall

	blt $s3, $zero, bitreverser
	j binary
	
bitreverser:
	li $s6, 1
	li $s7, 0
	li $t4, 1
	j binaryR	
	
binary:
	beq $s2, 1, binaryfin
	li $t0, 2
	div $s2, $t0
	mfhi $t1
	
	div $s2, $s2, $t0
	
	
	sb $t1, ($sp)
	add $t7, $t7, 1
	
	
	sub $sp, $sp, 1
	j binary
	
binaryR:
	beq $s2, 1, binaryfin
	li $t0, 2
	div $s2, $t0
	mfhi $t1
	div $s2, $s2, $t0
	beq $t1, $zero, binaryR1
	
	
	add $t4, $t4, $s7
	beq $t4, 2, carry
	sb $t4, ($sp)
	li $t4, 0
	add $t7, $t7, 1
	sub $sp, $sp, 1
	j binaryR

binaryR1:
	add $t4, $t4, $s6
	beq $t4, 2, carry
	sb $t4, ($sp)
	li $t4, 0
	add $t7, $t7, 1
	sub $sp, $sp, 1
	j binaryR

carry:
	sb $zero, ($sp)
	add $t7, $t7, 1
	sub $sp, $sp, 1
	li $t4, 1
	j binaryR

binaryfin:
	add $t4, $t4, $s7
	beq $t4, 2, carry
	sb $t4, ($sp)
	li $t4, 0
# 	sub $sp, $sp, 1
	
	add $t7, $t7, 1
	
	la $a0, binaryresp
	li $v0, 4
	syscall
	
	j prepzeroes
	
prepzeroes:
	li $t0, 4
	div $t7, $t0
	mfhi $t7
	beq $t7, $zero, printbinary
	li $t1, 4
	j addzeroes
	
addzeroes:
	beq $t7, $t1, printbinary
	
	sub $sp, $sp, 1
	sb $s6, ($sp)
	
	add $t7, $t7, 1
	j addzeroes

addones:
	sub $t0, $t9, $sp
	beq $t0, 32, breaktoprint
	sb $s6, ($sp)
	sub $sp, $sp, 1
	j addones

breaktoprint:
	li $s3, 1
	add $sp, $sp, 1
	j printbinary

printbinary:
	
	beq $s3, -1, addones
	
	bgt $sp, $t9, exit
	
	beq $t8, 4, space # $t8 holds counter to add spaces when needed
	
	lb $a0, ($sp)
	li $v0, 1
	syscall
	
	add $t8, $t8, 1
	
	add $sp, $sp, 1
	j printbinary
	
space:
	li $t8, 0
	la $a0 gap
	li $v0, 4
	syscall
	j printbinary
	
exit:
	li $v0, 10
	syscall
	
	
