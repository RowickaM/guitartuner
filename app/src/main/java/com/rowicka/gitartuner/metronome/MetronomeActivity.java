package com.rowicka.gitartuner.metronome;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.utility.NavigationBottom;

public class MetronomeActivity extends Activity {
    TempoRange tr;
    Metronome metronome;
    Thread metronomeThread;
    Button switchButton, upButton, downButton;
    Spinner tempoSpinner, beatSpinner;
    TextView bpmView;
    SeekBar seekBar;
    double measure, bpm;
    private static final String TAG = "MetronomeActivity";

    private void changeBpm(double newBpm, int switcher) {
        if (newBpm >= 40 && newBpm <= 208) {
            this.bpm = newBpm;
            bpmView.setText(String.valueOf(newBpm));
            if (switcher == 1){
                seekBar.setProgress((int) newBpm);
            }
        }

    }

    private void changeMeasure(double measure) {
        this.measure = measure;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);

        switchButton = (Button) findViewById(R.id.switchButton);
        upButton = (Button) findViewById(R.id.btnHighSpeed);
        downButton = (Button) findViewById(R.id.btnLowSpeed);
        tempoSpinner = (Spinner) findViewById(R.id.tapSpinner);
        beatSpinner = (Spinner) findViewById(R.id.beatSpinner);
        bpmView = (TextView) findViewById(R.id.tapText);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        metronome = new Metronome(this, bpm, 4);
        tr = new TempoRange();
        metronomeThread = new Thread(metronome);

        new NavigationBottom(this);

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchButton.getText().toString().equalsIgnoreCase("START")) {
                    metronome.setBpm(bpm);
                    if (metronomeThread.getState() == Thread.State.NEW){
                        metronomeThread.start();
                    }else{
                        metronomeThread = new Thread(metronome);
                        metronomeThread.start();
                    }
                    switchButton.setText("STOP");
                } else if (switchButton.getText().toString().equalsIgnoreCase("STOP")) {
                    metronomeThread.interrupt();
                    metronome.stopMetronome();
                    switchButton.setText("START");
                }
            }
        });


        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBpm(bpm + 1,1);
                metronome.setBpm(bpm);
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBpm(bpm - 1,1);
                metronome.setBpm(bpm);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i >= 40){
                    changeBpm((double) i , 0);
                    metronome.setBpm(i);
                }else{
                    seekBar.setProgress(40);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        tempoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();

                changeBpm(tr.getTempo(name).getMin(),1);
                metronome.setBpm(bpm);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        beatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String beat = adapterView.getItemAtPosition(i).toString();
                changeMeasure(Double.parseDouble(beat.split("/")[0]));
                metronome.setMeasure(measure);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    protected void onStop() {
        metronomeThread.interrupt();
        metronome.stopMetronome();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
