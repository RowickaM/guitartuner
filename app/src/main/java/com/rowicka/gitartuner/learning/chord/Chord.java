package com.rowicka.gitartuner.learning.chord;

import android.util.Log;

public class Chord {
    private String name;
    private String[] correctFrequency;

    public Chord(String name, String[] correctFrequency) {
        this.name = name;
        this.correctFrequency = correctFrequency;
    }

    public boolean isCorrect(String frequency, int position){

        float correct = Float.parseFloat(correctFrequency[position]);
        float actual = Float.parseFloat(frequency);
        float difference = (int) Math.round(correct - actual);

        return Math.abs(difference) <= 2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCorrectFrequency() {
        return correctFrequency;
    }

    public void setCorrectFrequency(String[] correctFrequency) {
        this.correctFrequency = correctFrequency;
    }
}
