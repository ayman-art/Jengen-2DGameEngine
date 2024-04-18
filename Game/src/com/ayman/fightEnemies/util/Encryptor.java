package com.ayman.fightEnemies.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Encryptor {
    public static String encrypt(int levelIndex) {
        String ret = "";
        int target = levelIndex * 100;
        while(sumOfChars(ret) < target){
            ret += (char)(Math.min(target - sumOfChars(ret), 25) + 'A');
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
            sum += s.charAt(i);
        }
        return sum;
    }
    public static int decrypt(String encrypted){
        int sum = 0;
        for (int i = 0; i < encrypted.length(); i++) {
            sum += encrypted.charAt(i);
        }
        return sum / 100;
    }
}
