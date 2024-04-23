package com.ayman.fightEnemies.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileCodeChecker {

    static boolean checkFileCode(String filePath) {
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

        String encryptedCode = FileCodeEncryptor.getEncryptedCode(filePath, Integer.parseInt(levelNumber.toString()));
        File file = new File(enc_code_path);
        String actualCode = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(enc_code_path));
            actualCode = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(encryptedCode);
        System.out.println(actualCode);
        return encryptedCode.equals(actualCode);

    }



    public static void main(String[] args) {
        String filePath = "C:\\Users\\ayman\\Desktop\\FightLevels\\level_1\\entities.txt";
        FileCodeEncryptor.encryptFile(filePath);
        System.out.println(checkFileCode(filePath));
    }
}
