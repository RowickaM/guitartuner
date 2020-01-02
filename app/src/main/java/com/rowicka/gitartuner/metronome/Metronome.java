package com.rowicka.gitartuner.metronome;

import android.app.Activity;
import android.media.MediaPlayer;

import com.rowicka.gitartuner.R;

public class Metronome implements Runnable {
    private double bpm, measure;
    private int counter;
    private boolean start = false;
    private final MediaPlayer beat1, beat2;

    Metronome(Activity activity, double bpm, int measure) {
        this.bpm = bpm;
        this.measure = measure;
        beat1 = MediaPlayer.create(activity, R.raw.beat1);
        beat2 = MediaPlayer.create(activity, R.raw.beat2);

    }

    void setBpm(double bpm) {
        this.bpm = bpm;
    }

    void setMeasure(double measure) {
        this.measure = measure;
    }

    /**
     * Funkcja uruchamiająca dźwięk
     * @param counter licznik oddanych dźwięków
     */
    private void beep(int counter){
        if (counter%measure == 0){
           beat2.start();
        }else {
            beat1.start();
        }
    }

    /**
     * Funkcja odpowiedzialna za poprawne wyzerowania stanu zmienneych po wyłączeniu wątku
     */
    void stopMetronome(){
        this.start = false;
        this.counter=0;
    }

    /**
     * Nadpisana funkcja z interfejsu Runnable, która zawiera informacje o działaniu wątku
     */
    @Override
    public void run() {
        this.start = true;
        while (start){
            beep(counter);
            counter++;
            try {
                /*wyliczenie odległości pomiędzy dźwiękami*/
                Thread.sleep((long)(1000 * (60.0/bpm)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
