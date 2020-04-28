package com.company.nand2teris;

import java.io.*;

public class VMCodeWriter {
    private BufferedWriter writer;
    private int ariJudgeCnt = 0;

    public VMCodeWriter(String outFileName) {
        try {
            DataOutputStream stream = new DataOutputStream(new FileOutputStream(outFileName));
            writer = new BufferedWriter(new OutputStreamWriter(stream, "utf-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeArithmetic(String op) throws IOException {
        String temp1 = "@SP\n" +
                       "AM=M-1\n" +
                       "D=M\n" +     // D=y
                       "A=A-1\n";    // M:x
        switch (op) {
            case "add":
                writer.write(temp1 + "M=M+D\n");break;  // break!!!
            case "sub":
                writer.write(temp1 + "M=M-D\n");break;
            case "and":
                writer.write(temp1 + "M=M&D\n");break;
            case "or":
                writer.write(temp1 + "M=M|D\n");break;
            case "eq":
                writer.write(temp2("JNE"));break;  // 若不等,跳转,赋值为0,否则(即eq)赋值为-1
            case "gt":
                writer.write(temp2("JLE"));break;  // 若<=,跳转,赋值为0,否则(即gt)赋值为-1
            case "lt":
                writer.write(temp2("JGE"));break;
            case "neg":
                writer.write("@SP\nA=M-1\nM=-M\n");break;
            case "not":
                writer.write("@SP\nA=M-1\nM=!M\n");break;
        }
    }

    private String temp2(String judge) {
        ariJudgeCnt++;
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "A=A-1\n" +
                "D=M-D\n" +

                "@NOT" + ariJudgeCnt + "\n" +
                "D;" + judge + "\n" +

                "@SP\n" +
                "A=M-1\n" +
                "M=-1\n" +    // true

                "@NEXT" + ariJudgeCnt + "\n" +  // jump to last to continue
                "0;JEQ\n" +
                "(NOT" + ariJudgeCnt + ")\n" +
                "@SP\n" +
                "A=M-1\n" +
                "M=0\n" +     // false
                "(NEXT" + ariJudgeCnt + ")\n"
                ;
    }

    public void writePushPop(int op, String seg, int idx) throws IOException {
        if (op == VMParser.PUSH) {
            switch (seg) {
                case "constant":
                    writer.write("@" + idx + "\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
                    break;
                case "local":
                    writer.write(pushTemp1("LCL", idx));
                    break;
                case "argument":
                    writer.write(pushTemp1("ARG", idx));
                    break;
                case "this":
                    writer.write(pushTemp1("THIS", idx));
                    break;
                case "that":
                    writer.write(pushTemp1("THAT", idx));
                    break;
                case "temp":
                    writer.write(pushTemp2(String.valueOf(idx+5)));
                    break;
                case "pointer":
                    if (idx==0)
                        writer.write(pushTemp2("THIS"));
                    else
                        writer.write(pushTemp2("THAT"));
                case "static":
                    writer.write(pushTemp2(String.valueOf(idx+16)));
            }
        } else {
            switch (seg) {
                case "local":
                    writer.write(popTemp1("LCL", idx));
                    break;
                case "argument":
                    writer.write(popTemp1("ARG", idx));
                    break;
                case "this":
                    writer.write(popTemp1("THIS", idx));
                    break;
                case "that":
                    writer.write(popTemp1("THAT", idx));
                    break;
                case "temp":
                    writer.write(popTemp2(String.valueOf(idx+5)));
                    break;
                case "pointer":
                    if (idx==0)
                        writer.write(popTemp2("THIS"));
                    else
                        writer.write(popTemp2("THAT"));
                case "static":
                    writer.write(popTemp2(String.valueOf(idx+16)));
            }
        }
    }

    // addr = segBase+idx; *SP = *addr; SP++
    private String pushTemp1(String seg, int idx) {
        return "@"+idx+"\n"+
                "D=A\n"+
                "@"+seg+"\n"+
                "A=M+D\n"+
                "D=M\n"+        // seg[idx]
                "@SP\n"+
                "A=M\n"+
                "M=D\n"+
                "@SP\n"+
                "M=M+1\n"
                ;
    }
    // addr = 5 + idx; *SP = *addr; SP++
    //                 *SP = THIS/THAT; SP++
    private String pushTemp2(String idx) {
        return "@"+idx+"\n"+
                "D=M\n"+
                "@SP\n"+
                "A=M\n"+
                "M=D\n"+
                "@SP\n"+
                "M=M+1\n"
                ;
    }
    // *SP = THIS/THAT; SP++
//    private String pushTemp3(int idx) {
//        String judge = (idx==0)? "THIS":"THAT";
//        return "@"+judge+"\n"+
//                "D=M\n"+
//                "@SP\n"+
//                "A=M\n"+
//                "M=D\n"+
//                "@SP\n"+
//                "M=M+1\n"
//                ;
//    }
    // addr = segBase+idx; SP--; *addr = *SP
    private String popTemp1(String seg, int idx) {
        return "@"+idx+"\n"+
                "D=A\n"+
                "@"+seg+"\n"+
                "D=M+D\n"+
                "@R13\n"+
                "M=D\n"+      // M[13] = seg+idx 临时寄存地址
                "@SP\n"+
                "AM=M-1\n"+   // M--
                "D=M\n"+
                "@R13\n"+
                "A=M\n"+
                "M=D\n"
                ;
    }
    private String popTemp2(String idx) {
        return "@"+idx+"\n"+
                "D=A\n"+
                "@R13\n"+
                "M=D\n"+      // M[13] = seg+idx 临时寄存地址
                "@SP\n"+
                "AM=M-1\n"+   // M--
                "D=M\n"+
                "@R13\n"+
                "A=M\n"+
                "M=D\n"
                ;
    }
    // SP--; THIS/THAT = *SP
//    private String popTemp3(int idx) {
//        String judge = (idx==0)? "THIS":"THAT";
//        return "@SP\n"+
//                "AM=M-1\n"+
//                "D=M\n"+
//                "@"+judge+"\n"+
//                "M=D\n"
//                ;
//    }

    public void close() throws IOException {
        writer.close();
    }

    public static void main(String[] args) {
        String op = "add";
        switch (op) {
            case "sub":
                System.out.println("sub");break;
            case "add":
                System.out.println("add");break;
            case "mul":
                System.out.println("mul");break;
        }

    }
}
