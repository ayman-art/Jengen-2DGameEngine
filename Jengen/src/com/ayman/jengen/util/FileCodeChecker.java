package com.ayman.jengen.util;

import java.io.*;

/**
 * This class is used to check that the user has not tampered with the code files.
 * */
public class FileCodeChecker {

    static boolean checkFileCode(String filePath, int levelNumber, String extension) {
        String expectedCode = "";

        expectedCode += FileCodeEncryptor.getEncryptedCode(filePath + "\\level_" + levelNumber +"\\level." + extension, levelNumber);
        expectedCode += FileCodeEncryptor.getEncryptedCode(filePath + "\\level_" + levelNumber + "\\entities.txt", levelNumber);

        String enc_code_path = filePath + "\\level_" + levelNumber + "\\code.enc";
        System.out.println(filePath);
        File file = new File(enc_code_path);
        String actualCode;
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            actualCode = bufferedReader.readLine();
            bufferedReader.close();
            reader.close();
            } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(expectedCode);
        System.out.println(actualCode);

        if(!expectedCode.equals(actualCode)) {
            System.out.println("Code has been tampered with, what are you trying to do?");
            System.exit(86086);
        }
        return true;
    }

    public static boolean checkFileCode(String filePath, int levelNumber) {
        return checkFileCode(filePath, levelNumber, "png");
    }



    public static void main(String[] args) {
        String filePath = "FightLevels";
        FileCodeChecker.checkFileCode(filePath, 2);

    }
}
