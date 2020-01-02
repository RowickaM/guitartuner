package com.rowicka.gitartuner.metronome;

import java.util.ArrayList;

class TempoRange {

    private ArrayList<TempoStatistic> array;

    TempoRange() {
        array = new ArrayList<>();
        array.add(new TempoStatistic("LARGO", 40, 60));
        array.add(new TempoStatistic("LARGHETTO", 60, 66));
        array.add(new TempoStatistic("ADAGIO", 66, 76));
        array.add(new TempoStatistic("ANDANTE", 76, 108));
        array.add(new TempoStatistic("MODERATO", 108, 120));
        array.add(new TempoStatistic("ALLEGRO", 120, 168));
        array.add(new TempoStatistic("PRESTO", 168, 200));
        array.add(new TempoStatistic("PRESTISSIMO", 200, 208));
    }

    /**
     * Funkcja zwracająca obiekt typu TempoStatic
     * @param name nazwa tempa
     * @return zwraca obiekt TempoStatic jeśli znajdzie w liście nazwę pasującą do podanej
     * w innym przypadku zwróci null
     */
    TempoStatistic getTempo(String name){
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
