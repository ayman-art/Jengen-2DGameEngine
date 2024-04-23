package com.ayman.fightEnemies.util;

import java.io.*;

public class FileCodeEncryptor {
    final static int sasa = 3;
    static {
        System.out.println("Hello");
    }

    public static void encryptFile(final String filePath) {
        int index = filePath.length() - 1;
        while (filePath.charAt(index) != '/' && filePath.charAt(index) != '\\'){
            index--;
        }
        String enc_code_path = filePath.substring(0, index + 1);
        StringBuilder levelNumber = new StringBuilder();
        index--;
        while (Character.isDigit(filePath.charAt(index))) {
            levelNumber.insert(0, filePath.charAt(index));
            index--;
        }
        enc_code_path += "code.enc";
        System.out.println(filePath);
        File file = new File(enc_code_path);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(getEncryptedCode(filePath, Integer.parseInt(levelNumber.toString())));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEncryptedCode(final String filePath, int levelNumber) {
        long ret = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    ret += (long) line.charAt(i) * i * i % 1000000000000L;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return String.valueOf(ret + levelNumber * 923L %1000000000000L);
    }


    public static void main(String[] args) {
        String filePath =
        """
        C:\\Users\\ayman\\Desktop\\FightLevels\\level_1\\entities.txt""";
        encryptFile(filePath);
    }
}
