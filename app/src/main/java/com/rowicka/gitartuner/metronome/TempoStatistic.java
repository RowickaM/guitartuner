package com.rowicka.gitartuner.metronome;

public class TempoStatistic {
    private String name;
    private int min, max;

    public TempoStatistic(String name, int min, int max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMeanBpm() {
        return (min + max)/2;
    }
}
