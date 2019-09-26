package com.rowicka.gitartuner.learning.chordsGroup;

import java.sql.DataTruncation;

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

    public void setName(String name) {
        this.name = name;
    }

    public String[] getInclude() {
        return include;
    }

    public void setInclude(String[] include) {
        this.include = include;
    }

}
