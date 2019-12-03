package com.rowicka.gitartuner.learning.chordsGroup;

public class ChordsGroup {
    private String name;
    private String[] include;

    public ChordsGroup(String name, String include) {

        this.name = name;
        this.include=include.split(";");
    }

    public String getName() {
        return name;
    }

    public String[] getInclude() {
        return include;
    }

}
