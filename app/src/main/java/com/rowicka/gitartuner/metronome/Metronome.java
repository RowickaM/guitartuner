package com.rowicka.gitartuner.metronome;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.utility.BeatsPoints;

import java.io.FileDescriptor;
import java.io.IOException;

public class Metronome implements Runnable {
    private double bpm, measure;
    private BeatsPoints bp;
    private int counter;
    private boolean start = false;
    final MediaPlayer beat1, beat2;
    Activity activity;

    public Metronome(Activity activity, double bpm, int measure) {

        this.activity = activity;
        this.bpm = bpm;
        this.measure = measure;
        beat1 = MediaPlayer.create(activity, R.raw.beat1);
        beat2 = MediaPlayer.create(activity, R.raw.beat2);

    }
    public void stopMetronome(){
        Log.d("MetronomeSound","Stop");
        this.start = false;
        this.counter=0;

    }

    private void beep(int counter){
        if (this.counter%measure == 0){
//            changeMarked(this.counter%measure);
           beat1.start();
        }else {
//            changeMarked(this.counter%measure);
            beat2.start();
        }

    }

    public double getBpm() {
        return bpm;
    }

    public void setBpm(double bpm) {
        this.bpm = bpm;
    }

    public double getMeasure() {
        return measure;
    }

    public void setMeasure(double measure) {
        this.measure = measure;
    }

    public void changeMarked(double position){
        unmarkAll();
        if (position == 0){
            bp.getArray()[0].setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_dot_color));
        }else{
            bp.getArray()[0].setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_dot_color_small));
        }
    }

    public void unmarkAll(){
        bp.getArray()[0].setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_dot));
        for (int i = 1; i< bp.getArray().length; i++) {
            bp.getArray()[i].setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_dot_small));
        }
    }

    @Override
    public void run() {
        Log.d("MetronomeSound","Start");
        this.start = true;
        this.bp = new BeatsPoints(activity);
        while (start){
            counter++;
            beep(counter);
            try {
                Thread.sleep((long)(1000 * (60.0/bpm)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
