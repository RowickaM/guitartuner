package com.rowicka.gitartuner.tuner;

import java.util.ArrayList;

public class Notes {
    private static ArrayList<String[]> notes;

    public static boolean fillNotesStandard() {
        notes = new ArrayList<>();

        String[] note;

        note = new String[]{"e", "330"};
        notes.add(note);

        note = new String[]{"H", "247"};
        notes.add(note);

        note = new String[]{"G", "196"};
        notes.add(note);

        note = new String[]{"D", "147"};
        notes.add(note);

        note = new String[]{"A", "110"};
        notes.add(note);

        note = new String[]{"E", "82"};
        notes.add(note);

        return notes.size() == 6;
    }

    public static boolean fillNotesDropD() {
        notes = new ArrayList<>();

        String[] note;

        note = new String[]{"D", "73"};
        notes.add(note);

        note = new String[]{"G", "98"};
        notes.add(note);

        note = new String[]{"C", "130"};
        notes.add(note);

        note = new String[]{"F", "174"};
        notes.add(note);

        note = new String[]{"A", "220"};
        notes.add(note);

        note = new String[]{"D", "293"};
        notes.add(note);

        return notes.size() == 6;
    }

    public static boolean fillNotesEFlat() {
        notes = new ArrayList<>();

        String[] note;

        note = new String[]{"Eb", "11"};
        notes.add(note);

        note = new String[]{"Eb", "77"};
        notes.add(note);

        note = new String[]{"G#", "103"};
        notes.add(note);

        note = new String[]{"C#", "138"};
        notes.add(note);

        note = new String[]{"F#", "185"};
        notes.add(note);

        note = new String[]{"Bb", "233"};
        notes.add(note);

        return notes.size() == 6;
    }

    public static String[] getNote(String frequencies) {
        if (!notes.isEmpty()) {
            for (String[] a : notes) {

                float noteFreq = Float.parseFloat(a[1]); //częstotliwość dla danej struny
                float freq = Float.parseFloat(frequencies);
                float difference = (int) Math.round(noteFreq - freq);



                if (Math.abs(difference) <= 20 ) { //|| Arrays.equals(a, notes.get(0)) || Arrays.equals(a, notes.get(6))
                    return new String[]{a[0], String.valueOf(difference)};
                }
            }
        }
        return null;
    }

    public static ArrayList<String[]> getArray(){
        return notes;
    }

    public static int positionOfString(String note){
        if (!notes.isEmpty()){
            for ( int i = 0; i< notes.size(); i++){
                if (notes.get(i)[0].equals(note)){
                    return i;
                }
            }
            return -1;
        }
        return -1;
    }
}
