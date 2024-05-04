package com.ayman.jengen.util;

import java.io.*;

/**
 * This class is used to encrypt the code files to prevent tampering with the game files.
 */
public class FileCodeEncryptor {
    public static void encryptFile(final String filePath, final int levelNumber, final String extension) {

        String enc_code_path = filePath +"\\level_" + levelNumber + "\\code.enc";
        System.out.println(filePath);
        File file = new File(enc_code_path);

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(getEncryptedCode(filePath + "\\level_" + levelNumber +"\\level." + extension, levelNumber));
            writer.write(getEncryptedCode(filePath + "\\level_" + levelNumber + "\\entities.txt", levelNumber));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void encryptFile(final String filePath, final int levelNumber) {
        encryptFile(filePath, levelNumber, "png");
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
        String levelsPath = "FightLevels";
        int numberOfLevels = 2;
        for (int i = 1; i <= numberOfLevels; i++)
            encryptFile(levelsPath, i);
        System.out.println("Level files encrypted successfully!");
        }



}
