package com.rowicka.gitartuner.learning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.learning.chord.Chord;
import com.rowicka.gitartuner.utility.NavigationBottom;
import com.rowicka.gitartuner.utility.Permission;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class ShowChordActivity extends Activity {

    Permission permission;
    private Thread audioThread;
    private AudioDispatcher dispatcher;
//    private int[] correctSeq = {0, 0, 0, 0, 0, 0};
    private int[] correctSeq = {1,1,1,1,1,1};
    private int progress;
    private int isLearning = 1;

    TextView nameChord, progressTV;
    TextView[] string;
    ImageView schemaChord;
    Chord chord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chord);

        permission = new Permission(this);
        permission.permissions();
        new NavigationBottom(this);

        nameChord = (TextView) findViewById(R.id.chordName);
        schemaChord = (ImageView) findViewById(R.id.chordSchema);
        progressTV = (TextView) findViewById(R.id.progress);
        progress = Integer.parseInt(progressTV.getText().toString().substring(6,7));



        string = new TextView[]{
                findViewById(R.id.stringChord1),
                findViewById(R.id.stringChord2),
                findViewById(R.id.stringChord3),
                findViewById(R.id.stringChord4),
                findViewById(R.id.stringChord5),
                findViewById(R.id.stringChord6),
        };

        chord = new Chord("G - dur", new String[]{"98", "123", "147", "196", "294", "392"});
//        Chord chord = new Chord("D - dur",new String[]{"0","0","147","220","294","370"});
//        Chord chord = new Chord("e - mol",new String[]{"82","123","165","196","247","330"});

        nameChord.setText(chord.getName());


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            progress = bundle.getInt("progress");
            progressTV.setText("próba "+progress+"/4");
            isLearning = bundle.getInt("isLearning");

        }
        if (progress < 5 && isLearning == 1){
            getPitch();
            isLearning = 1;
        }else{
            Log.d("cos", "koniec nauki");
            isLearning = 0;
        }

    }


    public void closeThread() {
        if (dispatcher != null) {
            audioThread.interrupt();
            dispatcher.stop();
            dispatcher = null;

            for (int i = 0; i<correctSeq.length; i++){
                setDefault(i);
            }
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

    public void processPitch(float pitchInHz) {
        for (int i = 0; i < correctSeq.length; i++) {
            if (correctSeq[i] == 1) {
                setCorrect(i);
            }
        }
        if (chord != null && pitchInHz > 0) {
            if (chord.isCorrect(String.valueOf(pitchInHz), 5)) {
                setCorrect(5);
            } else if (chord.isCorrect(String.valueOf(pitchInHz), 4)) {
                setCorrect(4);
            }else if (chord.isCorrect(String.valueOf(pitchInHz), 3)) {
                setCorrect(3);
            }else if (chord.isCorrect(String.valueOf(pitchInHz), 2)) {
                setCorrect(2);
            }else if (chord.isCorrect(String.valueOf(pitchInHz), 1)) {
                setCorrect(1);
            }else if (chord.isCorrect(String.valueOf(pitchInHz), 0)) {
                setCorrect(0);
            }
        }

        if (allIsCorrect()){
            assert chord != null;
            dialog("Gratulacje","Właśnie poprawnie zagrałeś akord " + chord.getName()
                    +"! Zostało ci jeszcze "+(4 - progress)+" razy i będziesz miał dostęp do rankingu :D");
        }
    }

    private boolean allIsCorrect(){
        for (int i = 0; i< correctSeq.length; i++){
            if (correctSeq[i] == 0){
                return false;
            }
        }
        return true;
    }

    private AlertDialog dialog(String title, String message){
        closeThread();
         final AlertDialog dialog = new AlertDialog.Builder(ShowChordActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_info).show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLearning == 1) {
                    refreshActivity(progress + 1);
                }else{
                    refreshActivity(1);
                }
            }
        });
        return dialog;
    }

    private void refreshActivity(int progress){
        finish();
        overridePendingTransition(0, 0);
        Intent intent = getIntent();

        Bundle dataBundle = new Bundle();
        dataBundle.putInt("progress", progress);
        dataBundle.putInt("isLearning", isLearning);
        intent.putExtras(dataBundle);

        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    private void setCorrect(int position) {
        string[position].setBackgroundColor(getResources().getColor(R.color.green));
        correctSeq[position] = 1;
    }

    private void setUncorrect(int position) {
        string[position].setBackgroundColor(getResources().getColor(R.color.red));
        correctSeq[position] = 0;
    }

    private void setDefault(int position) {
        string[position].setBackgroundColor(Color.WHITE);
        correctSeq[position] = 0;
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        // Not calling **super**, disables back button in current screen.
    }
}
