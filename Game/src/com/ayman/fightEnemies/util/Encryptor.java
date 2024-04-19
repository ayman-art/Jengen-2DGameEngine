package com.ayman.fightEnemies.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
        return (int) Math.sqrt(sum / 10);
    }

    public static void main(String[] args) {
        int levelIndex = 2;
        String encrypted = encrypt(levelIndex);
        System.out.println("Encrypted: " + encrypted);
    }
}
