package com.rowicka.gitartuner.metronome;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

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

    /**
     * Funkcja zmienia wartość tempa
     * @param newBpm nowa wartość tempa
     * @param switcher 1-zmiana wartości na pasku (seekbar)
     */
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

    /**
     *Funkcja potrzebna do stworzenia okna. Jest ona nadpisywana z klasy Activity.
     * Jest jedną z kliku dostępnych stanów z cyku życia aktywności.
     * @param savedInstanceState zawiera informacje o poprzednim stanie
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);

        switchButton = findViewById(R.id.switchButton);
        upButton = findViewById(R.id.btnHighSpeed);
        downButton = findViewById(R.id.btnLowSpeed);
        tempoSpinner = findViewById(R.id.tapSpinner);
        beatSpinner = findViewById(R.id.beatSpinner);
        bpmView = findViewById(R.id.tapText);
        seekBar = findViewById(R.id.seekBar);

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
                    switchButton.setText(getResources().getString(R.string.stop));
                } else if (switchButton.getText().toString().equalsIgnoreCase("STOP")) {
                    metronomeThread.interrupt();
                    metronome.stopMetronome();
                    switchButton.setText(getResources().getString(R.string.start));
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
            public void onNothingSelected(AdapterView<?> adapterView){}
        });

    }

    /**
     *Funkcja potrzebna do nadpisania działania aplikacji w czasie wyłączania aktywności.
     * Jest ona nadpisywana z klasy Activity.
     * Jest jedną z kliku dostępnych stanów z cyku życia aktywności.
     */
    @Override
    protected void onStop() {
        metronomeThread.interrupt();
        metronome.stopMetronome();
        super.onStop();
    }

    /**
     *Funkcja potrzebna do nadpisania działania aplikacji po naciśnięciu przycisku cofnij
     */
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
