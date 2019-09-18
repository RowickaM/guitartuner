package com.rowicka.gitartuner.metronome;

import java.util.ArrayList;

public class TempoRange {

    ArrayList<TempoStatistic> array;

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

    public String getNameTempo(int bpm) {
        TempoStatistic temp = null;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getMin() <= bpm && array.get(i).getMax() > bpm) {
                temp = array.get(i);
                break;
            }
        }
        if (temp != null) {
            return temp.getName();
        } else {
            return "";
        }
    }

    public int getMeanBpm(String name) {
        if (array != null) {
            for (TempoStatistic ts : array) {
                if (ts.getName().equalsIgnoreCase(name)) {
                    return ts.getMeanBpm();
                }
            }
        }
        return -1;
    }

    public ArrayList<TempoStatistic> getArray() {
        return array;
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

//<item>LARGO</item>
//        <item>LARGHETTO</item>
//        <item>ADAGIO</item>
//        <item>ANDANTE</item>
//        <item>MODERATO</item>
//        <item>ALLEGRO</item>
//        <item>PRESTO</item>
//        <item>PRESTISSIMO</item>
