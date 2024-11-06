package com.example.braintraining_hw.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestUtils {
    private static final Random RAND = new Random();
    private static final int MIN_INT = 0;
    private static final int MAX_INT = 100_000_000;

    public static final String[][] QUESTIONS_AND_ANSWERS = {
            { "Сколько будет 7 + 5? Ответ: 12", "true" },
            { "Что получится, если из 15 вычесть 8? Ответ: 6", "false" },
            { "Умножьте 6 на 4. Ответ: 24", "true" },
            { "Разделите 36 на 6. Ответ: 6", "true" },
            { "Сколько будет 9 + 8 - 4? Ответ: 12", "false" },
            { "У Маши было 12 яблок, она дала 5 подруге. Сколько яблок осталось у Маши? Ответ: 7", "true" },
            { "Какое число получится, если умножить 5 на 3, а затем прибавить 2? Ответ: 16", "false" },
            { "Найдите среднее арифметическое чисел 10, 20 и 30. Ответ: 25", "false" },
            { "Если у Пети было 24 конфеты, и он раздал их поровну между тремя друзьями, то сколько конфет получил каждый друг? Ответ: 12", "false" },
            { "На сколько нужно умножить 7, чтобы получить 35? Ответ: 5", "true" },
            { "Сколько будет 18 - 9 + 3? Ответ: 12", "true" },
            { "Что больше: 25 или 5 × 5? Ответ: 25", "false" },
            { "Сколько будет 2 + 2 * 2? Ответ: 8", "false" },
            { "Сколько будет 2 + 2 * 2? Ответ: 6", "true" },
            { "Сколько получится, если умножить 8 на 2 и затем вычесть 4? Ответ: 12", "true" },
            { "У Ксюши было 30 карандашей, она потеряла 10. Сколько карандашей у неё осталось? Ответ: 10", "false" },
            { "Какое число получается, если к 12 прибавить 15 и затем разделить на 3? Ответ: 9", "false" },
            { "Найдите произведение чисел 9 и 3, а затем разделите его на 3. Какой результат? Ответ: 9", "true" },
            { "Сколько будет 14 + 7 - 5? Ответ: 22", "false" },
            { "На сколько нужно разделить 24, чтобы получить 6? Ответ: 4", "true" },
    };


    public static List<String> getListQuestionsAndAnswer() {
        List<String> questions = new ArrayList<>();

        for (int i = 0; i < QUESTIONS_AND_ANSWERS.length; i++) {
            questions.add(QUESTIONS_AND_ANSWERS[i][0] + AppConstant.SPLIT_QUESTIONS + QUESTIONS_AND_ANSWERS[i][1] + "\n");
        }

        return questions;
    }


    public static int getRandomInteger() {
        return getRandomInteger(MIN_INT, MAX_INT);
    }

    public static int getRandomInteger(int max) {
        return getRandomInteger(MIN_INT, max);
    }

    public static int getRandomInteger(final int min, final int max) {
        return RAND.nextInt((max - min) + 1) + min;
    }
}
