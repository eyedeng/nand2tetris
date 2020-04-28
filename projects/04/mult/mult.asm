// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)


@i
M = 1
@R1
D = M 
@n 
M = D
@R0
D = M 
@num 
M = D
@sum
M = D
(LOOP)
	@n 
	D = M 
	@i 
	D = M - D
	@STOP
	D;JEQ
	@num
	D = M 
	@sum
	M = M + D
	@i
	M = M + 1
	@LOOP
	0;JMP
(STOP)
	@sum
	D = M 
	@R2
	M = D
(END)
	@END
	0;JMP

