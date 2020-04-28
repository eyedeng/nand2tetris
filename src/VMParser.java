package com.company.nand2teris;

import java.io.*;
import java.util.ArrayList;

public class VMParser {

    private ArrayList<String[]> commands = new ArrayList<>();
    private String[] curCom;
    private int curComIdx = 0;
    private int curComType = -1;
    private String argument1;
    private int argument2;

    public static final int ARITHMETIC = 0;
    public static final int PUSH = 1;
    public static final int POP = 2;
    public static final int LABEL = 3;
    public static final int GOTO = 4;
    public static final int IF = 5;
    public static final int FUNCTION = 6;
    public static final int RETURN = 7;
    public static final int CALL = 8;

    public VMParser(String inFileName) {
        try {
            DataInputStream stream = new DataInputStream(new FileInputStream(inFileName));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
            String line = "";
            String[] splits;
//            String[] parts = new String[3];
            boolean flag;
            while ((line = reader.readLine()) != null) {
                String[] parts = new String[3];  // 语法:不能放外面，否则只一份
                splits = line.split(" ");
                int cnt = 0;
                flag = false;
                for (int i = 0; i < splits.length; i++) {
                    if(splits[i].equals(""))
                        continue;
                    if(splits[i].equals("//"))
                        break;
                    parts[cnt++] = splits[i];
                    flag = true;
                }
                if (flag)
                    commands.add(parts);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean hasMoreCommands() {
        return curComIdx < commands.size();
    }

    public void advance() {
        curCom = commands.get(curComIdx++);

        if (curCom[2] != null) {
            if (curCom[0].equals("push")) {
                curComType = PUSH;
            } else if (curCom[0].equals("pop")) {
                curComType = POP;
            }
            argument1 = curCom[1];
            argument2 = Integer.parseInt(curCom[2]);
        } else if (curCom[1] != null) {

        } else {
            if (curCom[0].equals("return")) {

            } else {
                curComType = ARITHMETIC;
                argument1 = curCom[0];
            }
        }

    }

    public int commandType() {
        return curComType;
    }

    public String arg1() {
        return argument1;
    }

    public int arg2() {
        return argument2;
    }

    public static void main(String[] args) {


//        String[] pa = new String[3];
//        pa[0] = "p0";
//        System.out.println(pa[1] == null);  // true 不是""
//        String t = " push constant 7 ";
//        String[] sp = t.split(" ");
//        for (String s : sp) {
//            System.out.println(s.equals(""));
//        }
//        System.out.println(t.indexOf('/'));

    }

}
