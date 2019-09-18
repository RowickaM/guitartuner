package com.rowicka.gitartuner.metronome;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.utility.BeatsPoints;
import com.rowicka.gitartuner.utility.NavigationBottom;

public class MetronomeActivity extends Activity {
    //todo 6 wyświetlenie kropek
    TempoRange tr;
    Metronome metronome;
    Thread metronomeThread;
    Button switchButton, upButton, downButton;
    Spinner tempoSpinner, beatSpinner;
    TextView bpmView;
    double measure, bpm;

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

        changeBpm(120);
        metronome = new Metronome(this, bpm, 4);
        tr = new TempoRange();
        metronomeThread = new Thread(metronome);

        new NavigationBottom(this);


        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchButton.getText().toString().equalsIgnoreCase("START")) {
                    metronome.setBpm(bpm);
                    metronomeThread.start();
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
                changeBpm(bpm + 1);
                metronome.setBpm(bpm);
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBpm(bpm - 1);
                metronome.setBpm(bpm);
            }
        });


        tempoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();

                changeBpm(tr.getTempo(name).getMeanBpm());
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

    private void changeBpm(double newBpm) {
        this.bpm = newBpm;
        bpmView.setText(String.valueOf(newBpm));

    }

    private void changeMeasure(double measure) {
        this.measure = measure;

    }
}
