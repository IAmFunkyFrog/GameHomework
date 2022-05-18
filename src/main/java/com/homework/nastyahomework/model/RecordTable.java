package com.homework.nastyahomework.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecordTable {

    private final ObservableList<RecordPair> records = FXCollections.observableArrayList();

    public void addResult(int score, String word) {
        records.add(new RecordPair(score, word));
        records.sort((a, b) -> {
            int diff = b.getScore() - a.getScore();
            if(diff != 0) return diff;
            else return b.getWord().length() - a.getWord().length();
        });
        if(records.size() > 3) {
            records.remove(3);
        }
    }

    public ObservableList<RecordPair> getRecords() {
        return records;
    }

    public static class RecordPair {

        private final int score;
        private final String word;

        public RecordPair(int score, String word) {
            this.score = score;
            this.word = word;
        }

        public int getScore() {
            return score;
        }

        public String getWord() {
            return word;
        }

        @Override
        public String toString() {
            return word + ": " + String.valueOf(score);
        }

    }
}
