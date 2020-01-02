package com.rowicka.gitartuner.tuner;

import java.util.ArrayList;

class Notes {
    /**
     * notes - lista tablic stringów mówiąca o nazwie struny i waganej częstotliwości
     */
    private static ArrayList<String[]> notes;

    /**
     * Funkcja uzupełnia tablice o nazwę strun (dźwięków) i wymaganej częstotliwości przy pustej strunie
     * strój EADGHe
     * @return zwraca tru jeśli długość listy jest równa 6, w innym przypadku zwróci false
     */
    static boolean fillNotesStandard() {
        notes = new ArrayList<>();

        String[] note;

        note = new String[]{"E", "82"};
        notes.add(note);

        note = new String[]{"A", "110"};
        notes.add(note);

        note = new String[]{"D", "147"};
        notes.add(note);

        note = new String[]{"G", "196"};
        notes.add(note);

        note = new String[]{"H", "247"};
        notes.add(note);

        note = new String[]{"e", "330"};
        notes.add(note);

        return notes.size() == 6;
    }

    /**
     * Funkcja uzupełnia tablice o nazwę strun (dźwięków) i wymaganej częstotliwości przy pustej strunie
     * strój DGCFAD
     * @return zwraca tru jeśli długość listy jest równa 6, w innym przypadku zwróci false
     */
    static boolean fillNotesDropD() {
        notes = new ArrayList<>();

        String[] note;
        note = new String[]{"D", "293"};
        notes.add(note);

        note = new String[]{"A", "220"};
        notes.add(note);

        note = new String[]{"F", "174"};
        notes.add(note);

        note = new String[]{"C", "130"};
        notes.add(note);

        note = new String[]{"G", "98"};
        notes.add(note);

        note = new String[]{"D", "73"};
        notes.add(note);

        return notes.size() == 6;
    }

    /**
     * Funkcja uzupełnia tablice o nazwę strun (dźwięków) i wymaganej częstotliwości przy pustej strunie
     * strój EbG#C#F#BbEb
     * @return zwraca tru jeśli długość listy jest równa 6, w innym przypadku zwróci false
     */
    static boolean fillNotesEFlat() {
        notes = new ArrayList<>();

        String[] note;

        note = new String[]{"Bb", "233"};
        notes.add(note);

        note = new String[]{"F#", "185"};
        notes.add(note);

        note = new String[]{"C#", "138"};
        notes.add(note);

        note = new String[]{"G#", "103"};
        notes.add(note);

        note = new String[]{"Eb", "77"};
        notes.add(note);

        note = new String[]{"Eb", "11"};
        notes.add(note);

        return notes.size() == 6;
    }

    /**
     * Funkcja zwracająca wykrytą strune w zależności od częstotliwości
     * @param frequency wykryta częstotliwość z mikrofonu
     * @return zwraca tablice gdzie
     * pierwsza wartość to nazwa struny
     * druga wartość to różnica pomiędzy odczytaną wartośćiąz mikrofonu a wymaganą częstotliwośćią
     * trzecia wartość to wymagana częstotliwość
     * jeśli żadana z dostępnych częstotliwości nie odpowiada przedziałowi błędu (+/-) 10 to zwraca null
     */
    static String[] getNote(String frequency) {
        if (!notes.isEmpty()) {
            for (String[] a : notes) {
                float noteFreq = Float.parseFloat(a[1]); //częstotliwość dla danej struny
                float freq = Float.parseFloat(frequency);
                float difference = Math.round(noteFreq - freq);
                if (Math.abs(difference) <= 10 ) {
                    return new String[]{a[0], String.valueOf(difference), a[1]};
                }
            }
        }
        return null;
    }

    /**
     *
     * @return zwraca listę nazwy strun i ich wymaganych częstotliwości
     */
    static ArrayList<String[]> getArray(){
        return notes;
    }

    /**
     *
     * @param note nazwa struny
     * @return zwraca pozycje struny w liście strun na podstawie nazwy struny
     */
    static int positionOfString(String note){
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
