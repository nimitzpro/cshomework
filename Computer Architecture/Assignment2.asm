.data
	prompt: .asciiz "Enter a real number [xxx.yyy]: "
	inputStr: .space 20
	str2float: .float 0.0
	test: .asciiz "Handling decimals..."
	errormsg: .asciiz "Please re-run the program and enter a real number..."
	signBitTxt: .asciiz "The sign bit of your number is: "
	exponentTxt: .asciiz "The exponent of your number is: "
	fractionTxt: .asciiz "The fraction of your number is: "
	newline: .asciiz "\n"
	space: .asciiz " "
	temp: .word 0
	A: .asciiz "A"
	B: .asciiz "B"
	C: .asciiz "C"
	D: .asciiz "D"
	E: .asciiz "E"
	F: .asciiz "F"
	
.globl main	
.text

main:
	li $v0, 4
	la $a0, prompt
	syscall
	
	li $v0, 8
	la $a0, inputStr
	la $a1, 32
	syscall
	
	li $t7, 1 # Initial Sign bit
	li $t9, 1 # Exponent multiplier for decimal places
		
	la $s0, inputStr
	
	lb $s1, ($s0)
	beq $s1, 45, negative
	
	j convert2ints
	
negative:
	li $t7, -1 # Change sign bit to negative
	add $s0, $s0, 1
	j convert2ints

convert2ints:
	lb $s1, ($s0)    # $s1 holds char
    	
    	beq $s1, 10, noDecs # Exit if done
    	
    	beq $s1, 46, preDecs  
    	
 	sub $t0, $s1, 48
    	blt $t0, 0, error
    	bgt $t0 9, error
    	
	
	sub $s1, $s1, 48
	
	mul $s2, $s2, 10
	
	add $s2, $s2, $s1 # $s2 holds int
	
	add $s0, $s0, 1
	
	j convert2ints
	
preDecs:
	mul $s2, $s2, $t7
	add $s0, $s0, 1
	li $s3, 1
	j handleDecimals
	
handleDecimals:
	li $v0, 4
	la $a0, test
	syscall
	
	
	lb $s1, ($s0)    # $s1 holds char
	
    	beq $s1, 10, next # Exit if done  
    	
 	sub $t0, $s1, 48
    	blt $t0, 0, error
    	bgt $t0 9, error
	
	
	sub $s1, $s1, 48
	
	mul $s3, $s3, 10
	
	add $s3, $s3, $s1 # $s3 holds decimals
	
 	add $s0, $s0, 1
 	
	mul $t9, $t9, 10
	 
	j handleDecimals
	
noDecs:
	add $s2, $s2, 1
	j next
next:
	mtc1 $s2, $f2
	cvt.s.w $f1, $f2
	la $a0, str2float
	swc1 $f1, ($a0)
	
	mtc1 $s3, $f2
	cvt.s.w $f0, $f2
	
	
	mtc1 $t9, $f9
	cvt.s.w $f9, $f9
	
	div.s $f0, $f0, $f9
	
	# Removing the added 1
	li $t0, 1
	mtc1 $t0, $f3
	cvt.s.w $f3, $f3
	sub.s $f0, $f0, $f3
	
	beq $t7, -1, addDecsNegative # Jumps to subtracting the fraction part if the number is negative
	
	# Adding the fraction to the int part (positive numbers)
	add.s $f1, $f1, $f0
	swc1 $f1, ($a0)
	
	j hex
	
addDecsNegative:
	# Adding the fraction to the int part (positive numbers)
	sub.s $f1, $f1, $f0
	swc1 $f1, ($a0)
	j hex
	
hex:
	mov.s $f12, $f1
	li $v0, 2
	syscall
	
	la $a0, newline
	li $v0, 4
	syscall
	
	j xinit
	
	# Printing sign bit, exponent and fraction
	
	la $a0, newline
	li $v0, 4
	syscall
	la $a0, signBitTxt
	li $v0, 4
	syscall
	mfc1 $t0, $f1
	srl $a0, $t0, 31 # move the bastard right 31
	li $v0, 1
	syscall
	
#	la $a0, newline
#	li $v0, 4
#	syscall
#	la $a0, exponentTxt
#	li $v0, 4
#	syscall
	
#	la $a0, newline
#	li $v0, 4
##	syscall
#	la $a0, fractionTxt
#	li $v0, 4
#	syscall
	
	# ----------
	
	j end

# $t3 = mask, $t4 = mask counter
# $t7 = hex number
# $t5 = 4s hex typer
# $t6 = 8s splitter

xinit:
	li $t7, 0
	mfc1 $t0, $f1
	li $t3, 1
	sll $t3, $t3, 30
	li $t4, 8
	li $t9, 0
	li $t6, 0
	li $t5, 0
	j exponent
	
exponent:
	beq $t5, 4, splitReset
	

	beq $zero, $t4, finit
	
	and $t1, $t0, $t3
	beq $t1, $zero, xprint
	li $t1, 1
	j xprint

xprint:
	move $a0, $t1
	li $v0, 1
	syscall

	srl $t3, $t3, 1
	sub $t4, $t4, 1
	
	add $t5, $t5, 1
	add $t6, $t6, 1

	mul $t7, $t7, 10
	add $t7, $t7, $t1
	j exponent
	
splitReset:
	move $s0, $t7
	li $t5, 0
	li $t7, 0
	j hexloopinit
	
hexloopinit:
	li $s4, 1000
	li $s3, 8
	li $s7, 0
	j hexloop
hexloop:
	beq $s3, 1, hexloopfin
	and $s5, $s4, $s0
	mul $s5, $s5, $s3
	add $s7, $s7, $s5
	srl $s4, $s4, 1
	div $s3, $s3, 2
	j hexloop
hexloopfin:
	and $s5, $s4, $s0
	add $s7, $s7, $s5
	j hexify
hexify:
	blt $s7, 10, num
	beq $s7, 10, a
	beq $s7, 11, bb
	beq $s7, 12, c
	beq $s7, 13, d
	beq $s7, 14, e
	beq $s7, 15, f
	
num:
	move $a0, $s7
	li $v0, 1
	syscall
	li $s7, 0
	j exponent
a:
	la $a0, A
	li $v0, 4
	syscall
	li $s7, 0
	j exponent
bb:
	la $a0, B
	li $v0, 4
	syscall
	li $s7, 0
	j exponent
c:
	la $a0, C
	li $v0, 4
	syscall
	li $s7, 0
	j exponent
d:
	la $a0, D
	li $v0, 4
	syscall
	li $s7, 0
	j exponent
e:
	la $a0, E
	li $v0, 4
	syscall
	li $s7, 0
	j exponent
f:
	la $a0, F
	li $v0, 4
	syscall	
	li $s7, 0
	j exponent	
finit:
	li $t7, 0
	la $a0, newline
	li $v0, 4
	syscall
	la $a0, newline
	li $v0, 4
	syscall
	
	li $t9, 1
	li $t4, 23
	j fractions
	
fractions:
	beq $zero, $t4, end
	
	and $t1, $t0, $t3
	beq $t1, $zero, fprint
	li $t1, 1
	j fprint

fprint:
    move $a0, $t1
    li $v0, 1
    syscall

    srl $t3, $t3, 1
    sub $t4, $t4, 1
    
    j fractions


error:
	li $v0, 4
	la $a0, errormsg
	syscall
	
	j end
	
end:
	li $v0, 10
	syscall
