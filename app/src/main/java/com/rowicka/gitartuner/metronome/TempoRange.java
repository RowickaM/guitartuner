package com.rowicka.gitartuner.metronome;

import java.util.ArrayList;

public class TempoRange {

    private ArrayList<TempoStatistic> array;

    public TempoRange() {
        array = new ArrayList<TempoStatistic>();
        array.add(new TempoStatistic("LARGO", 40, 60));
        array.add(new TempoStatistic("LARGHETTO", 60, 66));
        array.add(new TempoStatistic("ADAGIO", 66, 76));
        array.add(new TempoStatistic("ANDANTE", 76, 108));
        array.add(new TempoStatistic("MODERATO", 108, 120));
        array.add(new TempoStatistic("ALLEGRO", 120, 168));
        array.add(new TempoStatistic("PRESTO", 168, 200));
        array.add(new TempoStatistic("PRESTISSIMO", 200, 208));
    }

    public TempoStatistic getTempo(String name){
        if (array != null) {
            for (TempoStatistic ts : array) {
                if (ts.getName().equalsIgnoreCase(name)) {
                    return ts;
                }
            }
        }
        return null;
    }
}
