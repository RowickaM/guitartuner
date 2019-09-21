package com.rowicka.gitartuner.learning.chords;

public class Chords {
    private String name, progress;

    public Chords(String name, String progress) {
        this.name = name;
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
