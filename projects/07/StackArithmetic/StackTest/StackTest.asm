@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@NOT1
D;JNE
@SP
A=M-1
M=-1
@NEXT1
0;JEQ
(NOT1)
@SP
A=M-1
M=0
(NEXT1)
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@NOT2
D;JNE
@SP
A=M-1
M=-1
@NEXT2
0;JEQ
(NOT2)
@SP
A=M-1
M=0
(NEXT2)
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@NOT3
D;JNE
@SP
A=M-1
M=-1
@NEXT3
0;JEQ
(NOT3)
@SP
A=M-1
M=0
(NEXT3)
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@NOT4
D;JGE
@SP
A=M-1
M=-1
@NEXT4
0;JEQ
(NOT4)
@SP
A=M-1
M=0
(NEXT4)
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@NOT5
D;JGE
@SP
A=M-1
M=-1
@NEXT5
0;JEQ
(NOT5)
@SP
A=M-1
M=0
(NEXT5)
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@NOT6
D;JGE
@SP
A=M-1
M=-1
@NEXT6
0;JEQ
(NOT6)
@SP
A=M-1
M=0
(NEXT6)
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@NOT7
D;JLE
@SP
A=M-1
M=-1
@NEXT7
0;JEQ
(NOT7)
@SP
A=M-1
M=0
(NEXT7)
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@NOT8
D;JLE
@SP
A=M-1
M=-1
@NEXT8
0;JEQ
(NOT8)
@SP
A=M-1
M=0
(NEXT8)
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@NOT9
D;JLE
@SP
A=M-1
M=-1
@NEXT9
0;JEQ
(NOT9)
@SP
A=M-1
M=0
(NEXT9)
@57
D=A
@SP
A=M
M=D
@SP
M=M+1
@31
D=A
@SP
A=M
M=D
@SP
M=M+1
@53
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
M=M+D
@112
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
M=M-D
@SP
A=M-1
M=-M
@SP
AM=M-1
D=M
A=A-1
M=M&D
@82
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
M=M|D
@SP
A=M-1
M=!M
