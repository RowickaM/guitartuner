package com.rowicka.gitartuner.learning.chordsGroup;

public class ChordsGroup {
        String name, include, progress;

    public ChordsGroup(String name, String include, String progress) {
        this.name = name;
        this.include = include;
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
