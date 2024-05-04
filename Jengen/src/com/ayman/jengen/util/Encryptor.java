package com.ayman.jengen.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class is used to encrypt the level index to a string, and decrypt it back.
 The encryption is done by generating a string of characters with a sum of their ASCII values equal to the level index squared times 10.
 The decryption is done by calculating the sum of the ASCII values of the characters in the string, and then taking the square root of the sum divided by 10.
 The goal of this class is to provide a way to store the level index in a quite secure way.
 */
public class Encryptor {
    public static String encrypt(int levelIndex) {
        String ret = "";
        int target = levelIndex * levelIndex * 10;
        while(sumOfChars(ret) < target){
            ret += (char)(Math.min(target - sumOfChars(ret), new Random().nextInt(26)) + 'A');
        }
        return shuffleString(ret);

    }
    public static String shuffleString(String input) {

        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters);
        StringBuilder sb = new StringBuilder();
        for (char c : characters) {
            sb.append(c);
        }
        return sb.toString();
    }
    public static int sumOfChars(String s){
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            sum += s.charAt(i) - 'A';
        }
        return sum;
    }
    public static int decrypt(String encrypted){
        int sum = 0;
        for (int i = 0; i < encrypted.length(); i++) {
            sum += encrypted.charAt(i) - 'A';
        }
        System.out.println("Sum: " + sum);
        return (int) Math.sqrt(sum / 10.0);
    }

    public static void main(String[] args) {
        int levelIndex = 2;
        String encrypted = encrypt(levelIndex);
        System.out.println("Encrypted: " + encrypted);
    }
}
