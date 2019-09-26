package com.rowicka.gitartuner.learning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.collections.chords.ChordsCollection;
import com.rowicka.gitartuner.collections.leaderboard.LeaderboardCollection;
import com.rowicka.gitartuner.learning.chord.Chord;
import com.rowicka.gitartuner.profile.LoginActivity;
import com.rowicka.gitartuner.utility.NavigationBottom;
import com.rowicka.gitartuner.utility.NavigationTop;
import com.rowicka.gitartuner.utility.Permission;
import com.squareup.picasso.Picasso;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class ShowChordActivity extends Activity {

    private static final String TAG = "ShowChordActivity";
    Permission permission;
    private Thread audioThread;
    private AudioDispatcher dispatcher;
//    private int[] correctSeq = {0, 0, 0, 0, 0, 0};
    private int[] correctSeq = {1,1,1,1,1,1};
    private int isLearning = 1, iteration = 1;
    private String nameGroup, key, keys;
    private float attempt, allInChord, allForGroup, allForUser;

    private float[] allPoints = {0, 0};

    TextView nameChord;
    Button showSchema;
    TextView[] string;
    ImageView schemaChord;
    LinearLayout layoutButton;
    Chord chord;
    ChordsCollection cc;

    ProgressDialog dialog;

    //zmienne do odmierzania czasu
    Chronometer chronometr;
    Handler handler;
    long tMilliSec, tStart, tBuff, tUpdate = 0L;
    int sec, min, millisec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chord);

        permission = new Permission(this);
        permission.permissions();

        new NavigationBottom(this);
        new NavigationTop(this);

        nameChord = (TextView) findViewById(R.id.chordName);
        schemaChord = (ImageView) findViewById(R.id.chordSchema);
        chronometr = findViewById(R.id.stopWatchView);
        showSchema = (Button) findViewById(R.id.showSchemaBtn);
        layoutButton = (LinearLayout) findViewById(R.id.layoutButton);

        handler = new Handler();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nameGroup = bundle.getString("group");
            key = bundle.getString("key");
            keys = bundle.getString("keys");
            iteration = bundle.getInt("progress");
            setProgressText(iteration);
            isLearning = bundle.getInt("isLearning");
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("Ładowanie danych");
        dialog.setCancelable(false);
        dialog.show();


        string = new TextView[]{
                findViewById(R.id.stringChord1),
                findViewById(R.id.stringChord2),
                findViewById(R.id.stringChord3),
                findViewById(R.id.stringChord4),
                findViewById(R.id.stringChord5),
                findViewById(R.id.stringChord6),
        };

        cc = new ChordsCollection();
        cc.fetchChord(dialog, nameGroup, key);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {//działanie aplikacji po pobraniu akordu
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                chord = cc.getChord();
                if (cc != null) {
                    Picasso.with(ShowChordActivity.this)
                            .load(chord.getUrlSchema())
                            .placeholder(R.mipmap.ic_guitar_round)
                            .into(schemaChord);


                    nameChord.setText(chord.getName());
                    attempt = chord.getAttempt();
                    allInChord = chord.getPoints();
                    allForGroup = chord.getAllInGroup();
                    allForUser = chord.getAllForUser();

                    if (isLearning == 1) {//tryb nauki
                        getPitch();
                    } else if (isLearning == 0) {//tryb rankingu
                        schemaChord.setVisibility(View.GONE);
                        showSchema.setVisibility(View.VISIBLE);
                        layoutButton.setVisibility(View.VISIBLE);
                        //stoper
                        startCountUp();
                        getPitch();
                    }
                } else {
                    startActivity(new Intent(ShowChordActivity.this, LoginActivity.class));
                }
            }

        });

        showSchema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSchema.setVisibility(View.GONE);
                layoutButton.setVisibility(View.GONE);
                schemaChord.setVisibility(View.VISIBLE);
            }
        });
    }


    private void setProgressText(int iteration) {
//        progressTV.setText("próba " + iteration + "/4");
    }

    public void closeThread() {
        if (dispatcher != null) {
            audioThread.interrupt();
            dispatcher.stop();
            dispatcher = null;

            for (int i = 0; i < correctSeq.length; i++) {
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
            }
            if (chord.isCorrect(String.valueOf(pitchInHz), 4)) {

                setCorrect(4);
            }
            if (chord.isCorrect(String.valueOf(pitchInHz), 3)) {

                setCorrect(3);
            }
            if (chord.isCorrect(String.valueOf(pitchInHz), 2)) {

                setCorrect(2);
            }
            if (chord.isCorrect(String.valueOf(pitchInHz), 1)) {

                setCorrect(1);
            }
            if (chord.isCorrect(String.valueOf(pitchInHz), 0)) {
                setCorrect(0);
            }
        }

        if (allIsCorrect()) {
            if (isLearning == 1 && chord != null) {
                dialog("Gratulacje", "Właśnie poprawnie zagrałeś akord " + chord.getName()
                        + "!");
            } else if (isLearning == 0) {

                setPoints();
                dialog("Próba nr " + iteration,
                        "Punkty w próbie: " + allPoints[1] + ".\n Punkty w rundzie: " + allPoints[0]);
            }
        }
    }

    private boolean allIsCorrect() {
        for (int i = 0; i < correctSeq.length; i++) {
            if (correctSeq[i] == 0) {
                return false;
            }
        }
        return true;
    }

    private AlertDialog dialog(String title, String message) {
        closeThread();
        stopCountUp();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ShowChordActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setIcon(R.drawable.ic_tuner_color);


        if (isLearning == 0 && iteration == 4) {
            dialogBuilder.setNeutralButton("Zagraj jeszcze raz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    LeaderboardCollection lc = new LeaderboardCollection(auth.getCurrentUser().getUid());
                    lc.updateCollectionForUser(nameGroup,
                            chord.getName(), allPoints[0] + allInChord,
                            allForGroup+allPoints[0],
                            allForUser + allPoints[0],
                            String.valueOf(attempt + 1));

                    finish();
                    overridePendingTransition(0, 0);
                    Intent intent = getIntent();
                    intent.putExtra("group", nameGroup);
                    intent.putExtra("key", key);
                    intent.putExtra("progress", 1);
                    intent.putExtra("isLearning", 0);
                    stopCountUp();
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        }

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                closeThread();

                if (isLearning == 1) {
                    getPitch();
                    dialogInterface.dismiss();
                } else if (isLearning == 0 && iteration < 4) {
                    //zwiększenie iteracji
                    iteration++;
                    setProgressText(iteration);
                    getPitch();
                    dialogInterface.dismiss();
                    startCountUp();
                } else if (isLearning == 0 && iteration == 4) {
                    stopCountUp();
                    dialogInterface.dismiss();
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    LeaderboardCollection lc = new LeaderboardCollection(auth.getCurrentUser().getUid());
                    lc.updateCollectionForUser(nameGroup, chord.getName(),
                            allPoints[0] + allInChord, allForGroup+allPoints[0],
                            allForUser + allPoints[0],
                            String.valueOf(attempt + 1));
                    Intent intent = new Intent(ShowChordActivity.this, ListOfChordsActivity.class);
                    if (keys != null) {
                        intent.putExtra("keys", keys);
                        intent.putExtra("group", nameGroup);
                        startActivity(intent);
                    }
                }
            }
        });


        return dialogBuilder.show();
    }

    private void setCorrect(int position) {
        string[position].setBackgroundColor(getResources().getColor(R.color.green));
        correctSeq[position] = 1;
    }

    private void setDefault(int position) {
        string[position].setBackgroundColor(Color.WHITE);
        correctSeq[position] = 1;
    }

    private void setPoints() {
        float score = 0;

        //za czas
        if (min == 0) {
            if (sec < 8) {
                score += 200;
            } else if (sec >= 8 && sec < 10) {
                score += 150;
            } else if (sec >= 10 && sec < 20) {
                score += 140;
            } else if (sec >= 20 && sec < 30) {
                score += 130;
            } else if (sec >= 30 && sec < 40) {
                score += 120;
            } else if (sec >= 40 && sec < 50) {
                score += 110;
            } else if (sec >= 50 && sec < 60) {
                score += 100;
            }
        } else if (min < 1) {
            score += 100;
        } else if (min >= 1 && min < 2) {
            score += 50;
        }

        //za schemat
        if (!(schemaChord.getVisibility() == View.VISIBLE)) {
            score += 100;
        }

        //za ilość prób
        switch ((int) attempt) {
            case 0:
                score *= 4;
                break;
            case 1:
                score *= 3;
                break;
            case 2:
                score *= 5;
                break;
            default:
                score *= 1;
                break;
        }

        this.allPoints[0] = this.allPoints[0] + score;
        this.allPoints[1] = score;
    }

    private void startCountUp() {
        Log.d(TAG, "startCountUp: ");
        tStart = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
        chronometr.start();
    }

    private void stopCountUp() {
        tMilliSec = 0L;
        tStart = 0L;
        tBuff = 0L;
        tUpdate = 0L;
        sec = 0;
        millisec = 0;
        min = 0;
        chronometr.stop();
        handler.removeCallbacks(runnable);

    }

    //do odmierzania czasu
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate / 1000);
            min = sec / 60;
            sec = sec % 60;
            millisec = (int) (tUpdate % 100);
            chronometr.setText(String.format("%02d", min) + ":" +
                    String.format("%02d", sec) + ":" + String.format("%02d", millisec));
            handler.postDelayed(this, 60);
        }
    };

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}
