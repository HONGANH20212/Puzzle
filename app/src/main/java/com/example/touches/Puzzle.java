package com.example.touches;

import java.util.Random;

public class Puzzle {
    public static final int NUMBER_PARTS = 5;
    String[] parts;
    Random random = new Random();

    public Puzzle() {
        parts = new String[NUMBER_PARTS];
        parts[0] = "I LOVE";
        parts[1] = "MOBILE";
        parts[2] = "PROGRAMMING"; // Sửa thành "PROGRAMMING" để đúng chính tả
        parts[3] = "USING";
        parts[4] = "JAVA";
    }
    public String getRandomWord() {
        int randomIndex = random.nextInt(NUMBER_PARTS);
        return parts[randomIndex];
    }

    public boolean solved(String[] solution) {
        if (solution != null && solution.length == parts.length) {
            for (int i = 0; i < parts.length; i++) {
                if (!solution[i].equals(parts[i])) // Sửa thành parts[i] để so sánh từng phần tử
                    return false;
            }
            return true;
        } else
            return false;
    }

    public String[] scramble() {
        String[] scrambled = new String[parts.length];
        for (int i = 0; i < scrambled.length; i++)
            scrambled[i] = parts[i];
        while (solved(scrambled)) {
            for (int i = 0; i < scrambled.length; i++) {
                int n = random.nextInt(scrambled.length - i) + i;
                String temp = scrambled[i];
                scrambled[i] = scrambled[n];
                scrambled[n] = temp;
            }
        }
        return scrambled;
    }

    public int getNumberParts() {
        return parts.length;
    }
    public String replacementWord() {
        // Thay thế từ này bằng logic của bạn để lấy từ mới
        return "NEW_WORD"; // Ví dụ: thay thế bằng từ mới là "NEW_WORD"
    }
}
