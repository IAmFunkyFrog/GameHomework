package com.homework.nastyahomework.model;

import java.util.*;

public class Game {

    public static final HashMap<String, ArrayList<String>> words = new HashMap<String, ArrayList<String>>();

    static {
        words.put("Математика", new ArrayList<String>(List.of(new String[]{"Сложение"})));
        words.put("Цветы", new ArrayList<String>(List.of(new String[]{"Василек"})));
    }

    private final String word;
    private int score = 15;

    private int lettersKnown = 0;

    public Game(String type) {
        String word = null;
        for(Map.Entry<String, ArrayList<String>> p: words.entrySet()) {
            if(Objects.equals(p.getKey(), type)) {
                word = (String) getRandomObjectFromArray(p.getValue());
            }
        }
        if(word == null) {
            Map.Entry<String, ArrayList<String>> randomWords = (Map.Entry<String, ArrayList<String>>) getRandomObjectFromArray(words.entrySet().stream().toList());
            word = (String) getRandomObjectFromArray(randomWords.getValue());
        }
        this.word = word.toUpperCase();
    }

    private <T> Object getRandomObjectFromArray(List<T> array) {
        return array.get((int) (Math.random() * array.size()));
    }

    // Функция возвращает позиции буквы в текущем слове, если она там есть, и обновляет количество очков
    // Если возвращается -1, значит буквы нет в слове или игра уже закончена
    public Integer[] tryCharacter(String character) {
        if(getLoseStatus() || getWonStatus()) return new Integer[]{-1};

        var indices = new ArrayList<Integer>();
        int index = word.indexOf(character);
        do {
            indices.add(index);
            index = word.indexOf(character, index + 1);
        } while (index != -1);
        if(indices.get(0) == -1) score--;
        else lettersKnown += indices.size();
        return indices.toArray(new Integer[0]);
    }

    public String getWord() {
        return word;
    }

    public int getScore() {
        return score;
    }

    public boolean getWonStatus() {
        return lettersKnown == word.length();
    }

    public boolean getLoseStatus() {
        return score == 0;
    }
}
