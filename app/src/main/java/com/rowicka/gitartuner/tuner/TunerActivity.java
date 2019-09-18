package com.rowicka.gitartuner.tuner;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.utility.NavigationBottom;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class TunerActivity extends Activity {


    private Thread audioThread;
    private AudioDispatcher dispatcher;

    TextView[] strings;
    ConstraintLayout[] stringsBack;

    int frequencyReq = 0;


    private int[] correctSeq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuner);

        new NavigationBottom(this);

        final Switch autoTuneSwitch = (Switch) findViewById(R.id.autoTune);
        final Spinner spinner = (Spinner) findViewById(R.id.spinnerTuner);

        //////////
        spinner.setSelection(2);

        strings = new TextView[]{
                (TextView) findViewById(R.id.stringText1),
                (TextView) findViewById(R.id.stringText2),
                (TextView) findViewById(R.id.stringText3),
                (TextView) findViewById(R.id.stringText4),
                (TextView) findViewById(R.id.stringText5),
                (TextView) findViewById(R.id.stringText6)
        };

        stringsBack = new ConstraintLayout[]{
                (ConstraintLayout) findViewById(R.id.string1),
                (ConstraintLayout) findViewById(R.id.string2),
                (ConstraintLayout) findViewById(R.id.string3),
                (ConstraintLayout) findViewById(R.id.string4),
                (ConstraintLayout) findViewById(R.id.string5),
                (ConstraintLayout) findViewById(R.id.string6)
        };

        correctSeq = new int[]{0,1,0,0,0,1};


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                clearStringsColor();
                String[] notes = adapterView.getItemAtPosition(position).toString().split(" ");

                for (int i = 0; i < notes.length / 2; i++) {
                    String temp = notes[i];
                    notes[i] = notes[notes.length - 1 - i];
                    notes[notes.length - 1 - i] = temp;
                }

                switch ((int) id) {
                    case 0: {
                        Notes.fillNotesStandard();
                        for (int i = 0; i < notes.length; i++) {
                            strings[i].setText(notes[i]);
                        }
                        break;
                    }
                    case 1: {
                        Notes.fillNotesDropD();
                        for (int i = 0; i < notes.length; i++) {
                            strings[i].setText(notes[i]);
                        }
                        break;
                    }
                    case 2: {
                        Notes.fillNotesEFlat();
                        for (int i = 0; i < notes.length; i++) {
                            strings[i].setText(notes[i]);
                        }
                        break;
                    }
                }

                if (Notes.getArray() != null) {
                    for (int i = 0; i < stringsBack.length; i++) {
                        final int finalI = i;
                        stringsBack[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                autoTuneSwitch.setChecked(false);
                                frequencyReq = Integer.parseInt(Notes.getArray().get(finalI)[1]);

                                closeThread();
                                setChoosenAndNotCorect(finalI);
                                checkPitch(finalI);

                            }
                        });
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        autoTuneSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    closeThread();
                    clearStringsColor();
                    getPitch();
                } else {
                    closeThread();
                }
            }
        });

    }


    public void closeThread() {
        clearStringsColor();
        if (dispatcher != null) {
            audioThread.interrupt();
            dispatcher.stop();
            dispatcher = null;
        }
    }

    @Override
    protected void onStop() {
        closeThread();
        super.onStop();
    }

    public void getPitch() {

        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
                final float pitchInHz = res.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processPitch(pitchInHz);
                    }
                });
            }
        };
        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);

        audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();
    }


    public void checkPitch(final int position) {

        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
                final float pitchInHz = res.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        process(pitchInHz, position);
//                        if (Math.abs(frequencyReq - pitchInHz) == 0)
//                            stringsBack[position].setBackgroundColor(Color.GREEN);
                    }
                });
            }
        };

        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);

        audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();
    }

    public void clearStringsColor() {
        for (int i = 0; i < stringsBack.length; i++) {
            setNotChoosenAndNotCorect(i);
            correctSeq[i] = 0;
        }
    }

    public void setChoosenAndCorect(int position){
        stringsBack[position].setBackground(getResources().getDrawable(R.drawable.ic_circle_yellow_green));
        strings[position].setTextColor(getResources().getColor(R.color.text));
        correctSeq[position] = 1;
    }

    public void setNotChoosenAndCorect(int position){ //todo zostawić te które są pooprawne po zaznaczeniu lub znacznej zmianie
        stringsBack[position].setBackground(getResources().getDrawable(R.drawable.ic_circle_green));
        strings[position].setTextColor(getResources().getColor(R.color.green));
    }

    public void setChoosenAndNotCorect(int position){
        stringsBack[position].setBackground(getResources().getDrawable(R.drawable.ic_circle_accent));
        strings[position].setTextColor(getResources().getColor(R.color.colorAccent));
        correctSeq[position] = 0;
    }

    public void setNotChoosenAndNotCorect(int position){
        stringsBack[position].setBackground(getResources().getDrawable(R.drawable.ic_circle_yellow));
        strings[position].setTextColor(getResources().getColor(R.color.text));
    }

    public void processPitch(float pitchInHz) {
        TextView pitchText = (TextView) findViewById(R.id.pitchText);
        String[] array = Notes.getNote(String.valueOf(pitchInHz));
        if (array != null) {

            if (Float.parseFloat(array[1]) == 0){
                pitchText.setText(array[1]);
                setChoosenAndCorect(Notes.positionOfString(array[0]));
            }else{
                setChoosenAndNotCorect(Notes.positionOfString(array[0]));
                if (pitchInHz - frequencyReq > 0) {
                    pitchText.setText(array[1]);
                }
                else {
                    pitchText.setText("+"+array[1]);
                }
            }

        }else{
            pitchText.setText(" ");
        }
    }

    public void process(float pitchInHz, int position){
        TextView pitchText = (TextView) findViewById(R.id.pitchText);

        if ((pitchInHz > -1.0)) {
            int diff = (int) (pitchInHz - frequencyReq);

            if (diff == 0){
                pitchText.setText(diff);
                setChoosenAndCorect(position);
            }else{
                setChoosenAndNotCorect(position);
                if (pitchInHz - frequencyReq > 0) {
                    pitchText.setText(diff);
                }
                else {
                    pitchText.setText("+"+diff);
                }
            }

        } else {
            pitchText.setText(" ");
        }

    }
}
