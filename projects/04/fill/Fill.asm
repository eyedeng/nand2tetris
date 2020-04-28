// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

@w
M = 1
@8192
D = A
@n 
M = D
@SCREEN
D = A  // != M
@adr
M = D
(LOOP)
	@KBD
	D = M
	@NOPRESS
	D;JEQ
	@PRESSED
	D;JGT
	@LOOP
	0;JMP
(NOPRESS)
	@w 
	D = M
	@WHITE
	D;JEQ
	@LOOP  
	0;JMP  // need!
(PRESSED)
	@w 
	D = M
	@BLACK
	D;JGT
	@LOOP
	0;JMP
(WHITE)
	@w 
	M = 1
	@i
	M = 0
	(FOR1)
		@i
		D = M
		@n 
		D = M - D
		@LOOP
		D;JEQ
		@i 
		D = M
		@adr
		A = M + D
		M = 0
		@i 
		M = M + 1
		@FOR1
		0;JMP
(BLACK)
	@w 
	M = 0
	@i
	M = 0
	(FOR2)
		@i
		D = M
		@n 
		D = M - D
		@LOOP
		D;JEQ
		@i 
		D = M
		@adr
		A = M + D
		M = -1
		@i 
		M = M + 1
		@FOR2
		0;JMP
	