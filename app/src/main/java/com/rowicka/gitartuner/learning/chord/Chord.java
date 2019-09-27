package com.rowicka.gitartuner.learning.chord;

public class Chord {
    private String name;
    private String[] correctFrequency;
    private String urlSchema;
    private float attempt, points, allInGroup, allForUser;

    public Chord(String name, String[] correctFrequency, String url) {
        this.name = name;
        this.correctFrequency = correctFrequency;
        this.urlSchema = url;
    }

    public boolean isCorrect(String frequency, int position){

        float correct = Float.parseFloat(correctFrequency[position]);
        float actual = Float.parseFloat(frequency);
        float difference = (int) Math.round(correct - actual);

        if (correct == 0)
            return true;
        return Math.abs(difference) <= 2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAttempt() {
        return attempt;
    }

    public float getPoints() {
        return points;
    }

    public float getAllInGroup() {
        return allInGroup;
    }

    public float getAllForUser() {
        return allForUser;
    }

    public void setAllInGroup(float allInGroup) {
        this.allInGroup = allInGroup;
    }

    public void setAllForUser(float allForUser) {
        this.allForUser = allForUser;
    }

    public void setAttemptAndPoints(float attempt, float points) {
        this.attempt = attempt;
        this.points = points;
    }

    public String getUrlSchema() {
        return urlSchema;
    }
}
