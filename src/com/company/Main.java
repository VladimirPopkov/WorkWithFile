package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String dir;
        Scanner in = new Scanner(System.in);
        System.out.println("Введите директорию: ");
        dir = in.nextLine();

        String result = rec(dir, 0);

        boolean isOk = saveToFile(dir + "/List.txt", result);

        if (isOk) {
            try {
                System.out.println(readFromFile(new File(dir + "/List.txt")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Файл уже существует");
        }
    }

    public static String rec(String aDir, int depth) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= depth; i++) {
            builder.append("  ");
        }
        String indent = builder.toString();
        StringBuilder str = new StringBuilder();

        File direct = new File(aDir);
        File[] listFiles = direct.listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    str.append(listFiles[i].getParent());
                    str.append(System.getProperty("line.separator"));
                    str.append("\t");
                    str.append(indent);
                    str.append(listFiles[i].getPath());
                    str.append("\\");
                    str.append(System.getProperty("line.separator"));
                    str.append(rec(listFiles[i].getPath(), depth + 1));
                } else {
                    str.append("\t");
                    str.append(indent);
                    str.append(listFiles[i].getPath());
                    str.append(System.getProperty("line.separator"));
                }
            }
        }
        return str.toString();
    }

    public static boolean saveToFile(String pathFile, String sourse) {
        File file1 = new File(pathFile);

        boolean res = false;
        try {
            res = file1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (res) {
            try {
                PrintWriter out = new PrintWriter(file1.getAbsoluteFile());
                try {
                    out.print(sourse);
                } finally {
                    out.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public static String readFromFile(File file) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }

        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
