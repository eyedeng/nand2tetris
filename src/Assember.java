package com.company.nand2teris;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Assember {

    public static HashMap<String,String> compAMap = new HashMap<>();
    public static HashMap<String,String> compMMap = new HashMap<>();
    public static HashMap<String,String> dstMap = new HashMap<>();
    public static HashMap<String,String> jmpMap = new HashMap<>();

    private static HashMap<String, Integer> cMap = new HashMap<>();  // symbols table

    static {

        //put all comp posibilities with A into a HashMap,a=0
        compAMap.put("0","101010");compAMap.put("1","111111");compAMap.put("-1","111010");
        compAMap.put("D","001100");compAMap.put("A","110000");compAMap.put("!D","001101");
        compAMap.put("!A","110001");compAMap.put("-D","001111");compAMap.put("-A","110011");
        compAMap.put("D+1","011111");compAMap.put("A+1","110111");compAMap.put("D-1","001110");
        compAMap.put("A-1","110010");compAMap.put("D+A","000010");compAMap.put("D-A","010011");
        compAMap.put("A-D","000111");compAMap.put("D&A","000000");compAMap.put("D|A","010101");

        //put all comp posibilities with M into a HashMap,a=1
        compMMap.put("M","110000");compMMap.put("!M","110001");compMMap.put("-M","110011");
        compMMap.put("M+1","110111");compMMap.put("M-1","110010");compMMap.put("D+M","000010");
        compMMap.put("D-M","010011");compMMap.put("M-D","000111");compMMap.put("D&M","000000");
        compMMap.put("D|M","010101");

        //put all dst posibilities into a HashMap
        dstMap.put("","000");dstMap.put("M","001");dstMap.put("D","010");dstMap.put("MD","011");
        dstMap.put("A","100");dstMap.put("AM","101");dstMap.put("AD","110");dstMap.put("AMD","111");

        //put all jmp posibilities into a HashMap
        jmpMap.put("","000");jmpMap.put("JGT","001");jmpMap.put("JEQ","010");jmpMap.put("JGE","011");
        jmpMap.put("JLT","100");jmpMap.put("JNE","101");jmpMap.put("JLE","110");jmpMap.put("JMP","111");

        cMap.put("SP",0);cMap.put("LCL",1);cMap.put("ARG",2);cMap.put("THIS",3);
        cMap.put("THAT",4);cMap.put("R0",0);cMap.put("R1",1);cMap.put("R2",2);
        cMap.put("R3",3);cMap.put("R4",4);cMap.put("R5",5);cMap.put("R6",6);
        cMap.put("R7",7);cMap.put("R8",8);cMap.put("R9",9);cMap.put("R10",10);
        cMap.put("R11",11);cMap.put("R12",12);cMap.put("R13",13);cMap.put("R14",14);
        cMap.put("R15",15);cMap.put("SCREEN",16384);cMap.put("KBD",24576);

    }

    public Assember(String file, String out) {
        // 去掉空格、注释
        ArrayList<String> commands = initCommands(file);
        // 替换符号(预定义符号、标签、变量)为内存地址
        ArrayList<String> commandsL = rmLables(commands);
        // 据指令集(语法规则)翻译成机器码
        ArrayList<String> instructs = trans(commandsL);
//        out = "C:\\Users\\dengy\\Desktop\\OS\\nand2teris\\nand2tetris\\projects\\06\\pong\\out.hack";
        writeInstructs(instructs, out);
    }

    private ArrayList<String> rmLables(ArrayList<String> commands) {
        int row = 0;
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).charAt(0) == '(') {
                cMap.put(commands.get(i).substring(1, commands.get(i).length()-1), row);
            }else
                row++;
        }

        ArrayList<String> commandsL = new ArrayList<>();
        int varN = 16;
        String label = "";
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).charAt(0) == '(')
                continue;
            if (commands.get(i).charAt(0) == '@') {
                label = commands.get(i).substring(1);
                if ('0' <= label.charAt(0) && label.charAt(0) <= '9') {
                    commandsL.add(commands.get(i));
                } else if (cMap.containsKey(label)) {
                    commandsL.add("@" + cMap.get(label));
                } else {
                    cMap.put(label, varN++);
                    commandsL.add("@" + cMap.get(label));
                }
            } else {
                commandsL.add(commands.get(i));
            }
        }
/*        for (String s : commandsL) {
            System.out.println(s);
        }*/
        return commandsL;
    }

    private ArrayList<String> initCommands(String file) {
        ArrayList<String> commands = new ArrayList<>();
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
//            reader.readLine();
            String line = null;
            while((line=reader.readLine())!=null){
                String command = "";
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ' ')
                        continue;
                    if (line.charAt(i) == '/')
                        break;
                    command += line.charAt(i);
                }
                if (!command.equals(""))
                    commands.add(command);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commands;
    }

    private void writeInstructs(ArrayList<String> instructs, String file) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
            for (String s : instructs) {
                bw.write(s);
                bw.newLine();
            }
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> trans(ArrayList<String> commands) {
        ArrayList<String> instructions = new ArrayList<>();
        for (String command : commands) {
            String instruct = "";
            if (command.charAt(0) == '@') {
                String sn = command.substring(1);
                int n = Integer.parseInt(sn);
                String bn = Integer.toBinaryString(n);
                String fbn = String.format("%16s", bn).replace(' ', '0');
                instruct = fbn;
            } else {
                //for c instructions dest=comp;jump
                String[] splits = command.split(";");
                String dest="", comp="", jump="";
                if (splits.length == 1) {
                    String[] splits2 = splits[0].split("=");
                    dest = splits2[0];
                    comp = splits2[1];
                    jump = "";
                    instruct = getCode(dest, comp, jump);
                } else {
                    jump = splits[1];
                    // guess none "="
                    comp = splits[0];
                    dest = "";
                    instruct = getCode(dest, comp, jump);
                }
            }
            instructions.add(instruct);
        }
//        for (String s : instructions) {
//            System.out.println(s);
//        }
        return instructions;
    }

    private String getCode(String dest, String comp, String jump) {
        String instruct = "111";
        if (comp.contains("M")) {
            instruct += "1";
            instruct += compMMap.get(comp);
        } else {
            instruct += "0";
            instruct += compAMap.get(comp);
        }
        instruct += dstMap.get((dest));
        instruct += jmpMap.get(jump);
        return instruct;
    }


    public static void main(String[] args) {
//        args = new String[]{"C:\\Users\\dengy\\Desktop\\OS\\nand2teris\\nand2tetris\\projects\\06\\pong\\Pong.asm"};
        if (args.length == 0) {
            System.out.println("none input file");
            return;
        }
        Assember asm = new Assember(args[0], args[1]);

    }

}
