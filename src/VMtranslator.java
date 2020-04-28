package com.company.nand2teris;

import java.io.IOException;

public class VMtranslator {
    public static void main(String[] args) throws IOException {
        args = new String[]{"C:\\Users\\dengy\\Desktop\\OS\\nand2teris\\nand2tetris\\projects\\07\\MemoryAccess\\StaticTest\\StaticTest.vm"};
        VMParser parser = new VMParser(args[0]);
        VMCodeWriter code = new VMCodeWriter("C:\\Users\\dengy\\Desktop\\OS\\nand2teris\\nand2tetris\\projects\\07\\MemoryAccess\\StaticTest\\StaticTest.asm");
        while (parser.hasMoreCommands()) {
            parser.advance();
            switch (parser.commandType()) {
                case VMParser.ARITHMETIC:
                    code.writeArithmetic(parser.arg1());break;
                case VMParser.PUSH: case VMParser.POP:
                    code.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());break;
                case VMParser.CALL:
                    ;
            }
        }
        code.close();
    }
}
