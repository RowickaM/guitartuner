package com.rowicka.gitartuner.tuner;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.utility.NavigationBottom;
import com.rowicka.gitartuner.utility.Permission;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class TunerActivity extends Activity {

    /**
     * strings - tablica nazw strun
     * stringsBack - tablica okręgów strun
     */
    Permission permission;
    TextView[] strings;
    ConstraintLayout[] stringsBack;
    int frequencyReq = 0;
    private int[] correctSeq = {0,0,0,0,0,0};
    private Thread audioThread;
    private AudioDispatcher dispatcher;

    /**
     * Funkcja potrzebna do włączenia wątku sprawdzenia częstotliwości
     * odebranego przez mikrofon dźwięku - wszystkie struny na raz.
     *
     */
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

    /**
     * Funkcja potrzebna do włączenia wątku sprawdzenia częstotliwości
     * odebranego przez mikrofon dźwięku - po jednej strunie na raz.
     *
     */
    public void checkPitch(final int position) {
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);
        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
                final float pitchInHz = res.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < correctSeq.length; i++) {
                            if (correctSeq[i] == 1) {
                                setChosenAndCorrect(i);
                            }
                        }
                        TextView correctText = findViewById(R.id.correctPitch);
                        correctText.setText(String.valueOf(frequencyReq));
                        process(pitchInHz, position);
                    }
                });
            }
        };
        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);
        audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();
    }

    /**
     * Ustawienie wszystch strun jako nie zagrane
     */
    public void clearStringsColor() {
        for (int i = 0; i < stringsBack.length; i++) {
            setNotChosenAndNotCorrect(i);
            correctSeq[i] = 0;
        }
    }

    /**
     * Funkcja zaznacza w oknie że struna jest wybrana i jest zagraqna prawidłowo
     * @param position nr struny określonej jako zagranej
     */
    public void setChosenAndCorrect(int position) {
        stringsBack[position].setBackground(getResources().getDrawable(R.drawable.ic_circle_yellow_green));
        strings[position].setTextColor(getResources().getColor(R.color.text));
        correctSeq[position] = 1;
    }

    /**
     * Funkcja zaznacza w oknie że struna nie jest wybrana i jest zagraqna prawidłowo
     * @param position nr struny określonej jako zagranej
     */
    public void setNotChosenAndCorrect(int position) {
        stringsBack[position].setBackground(getResources().getDrawable(R.drawable.ic_circle_green));
        strings[position].setTextColor(getResources().getColor(R.color.green));
    }

    /**
     * Funkcja zaznacza w oknie że struna jest wybrana i nie jest zagraqna prawidłowo
     * @param position nr struny określonej jako zagranej
     */
    public void setChosenAndNotCorrect(int position) {
        stringsBack[position].setBackground(getResources().getDrawable(R.drawable.ic_circle_accent));
        strings[position].setTextColor(getResources().getColor(R.color.colorAccent));
        correctSeq[position] = 0;
    }

    /**
     * Funkcja zaznacza w oknie że struna nie jest wybrana i nie jest zagraqna prawidłowo
     * @param position nr struny określonej jako zagranej
     */
    public void setNotChosenAndNotCorrect(int position) {
        stringsBack[position].setBackground(getResources().getDrawable(R.drawable.ic_circle_yellow));
        strings[position].setTextColor(getResources().getColor(R.color.text));
    }

    /**
     * Funkcja sprawdzająca która ze strun ma taką samą lub zbliżoną (+/- 2) częstotliwość
     * do którejś z wymaganych częstotliwości
     * @param pitchInHz pole przyjmujące informacje o odczytanej częstotliwości dźwięku
     */
    public void processPitch(float pitchInHz) {
        TextView pitchText =  findViewById(R.id.pitchText);
        String[] array = Notes.getNote(String.valueOf(pitchInHz));
        for (int i = 0; i < correctSeq.length; i++) {
            if (correctSeq[i] == 1) {
                setNotChosenAndCorrect(i);
            }else{
                setNotChosenAndNotCorrect(i);
            }
        }
        if (array != null) {
            TextView correctText = findViewById(R.id.correctPitch);
            correctText.setText(array[2]);
            if (Float.parseFloat(array[1]) == 0) {
                pitchText.setText(array[1]);
                setChosenAndCorrect(Notes.positionOfString(array[0]));
            } else {
                setChosenAndNotCorrect(Notes.positionOfString(array[0]));
                String s = String.valueOf(array[1].charAt(0));
                if (s.equals("-")) {
                    pitchText.setText(array[1]);
                } else {
                    pitchText.setText("+"+array[1]);
                }
            }
        }

    }

    /**
     * Funkcja sprawdzająca czy struna ma taką samą lub zbliżoną (+/- 2) częstotliwość do wymaganej
     * @param pitchInHz pole przyjmujące informacje o odczytanej częstotliwości dźwięku
     */
    public void process(float pitchInHz, int position) {
        TextView pitchText = findViewById(R.id.pitchText);
        for (int i = 0; i < correctSeq.length; i++) {
            if (correctSeq[i] == 1) {
                setNotChosenAndCorrect(i);
            }else{
                setNotChosenAndNotCorrect(i);
            }
        }
        if ((pitchInHz > -1.0)) {
            int diff = (int) (pitchInHz - frequencyReq);

            if (diff == 0) {
                pitchText.setText(String.valueOf(diff));
                setChosenAndCorrect(position);
            } else if (correctSeq[position] == 0){
                setChosenAndNotCorrect(position);
                if (pitchInHz - frequencyReq > 0) {
                    pitchText.setText("+"+diff);
                } else {
                    pitchText.setText(String.valueOf(diff));
                }
            }
        }

    }


    /**
     *Funkcja potrzebna do stworzenia okna. Jest ona nadpisywana z klasy Activity.
     * Jest jedną z kliku dostępnych stanów z cyku życia aktywności.
     * @param savedInstanceState zawiera informacje o poprzednim stanie
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuner);

        permission = new Permission(this);
        permission.permissions();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        new NavigationBottom(this);

        final Switch autoTuneSwitch = findViewById(R.id.autoTune);
        final Spinner spinner = findViewById(R.id.spinnerTuner);

        strings = new TextView[]{
                findViewById(R.id.stringText1),
                findViewById(R.id.stringText2),
                findViewById(R.id.stringText3),
                findViewById(R.id.stringText4),
                findViewById(R.id.stringText5),
                findViewById(R.id.stringText6)
        };

        stringsBack = new ConstraintLayout[]{
                findViewById(R.id.string1),
                findViewById(R.id.string2),
                findViewById(R.id.string3),
                findViewById(R.id.string4),
                findViewById(R.id.string5),
                findViewById(R.id.string6)
        };




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                clearStringsColor();
                String[] notes = adapterView.getItemAtPosition(position).toString().split(" ");

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
                                setChosenAndNotCorrect(finalI);
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
                    clearStringsColor();
                }
            }
        });
    }

    /**
     * Funkcja odpowiedzialna za poprawne zakończenie wątku
     */
    public void closeThread() {
        clearStringsColor();
        if (dispatcher != null) {
            audioThread.interrupt();
            dispatcher.stop();
            dispatcher = null;
        }
    }

    /**
     *Funkcja potrzebna do nadpisania działania aplikacji w czasie wyłączania aktywności.
     * Jest ona nadpisywana z klasy Activity.
     * Jest jedną z kliku dostępnych stanów z cyku życia aktywności.
     */
    @Override
    protected void onStop() {
//        clearStringsColor();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        closeThread();
        super.onStop();
    }

    /*todo sprawdzić w dokumentacji*/
    /**
     * Funkcja odpowiedzialna za sprawdzednie nadania pozwolenia
     * @param requestCode kod pozwolenia
     * @param permissions pozwolenia
     * @param grantResults wynik nadania pozwoleń
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(permissions[i])) {
                        new AlertDialog.Builder(this)
                                .setMessage("Nie nadano wszystkich potrzebnych pozwoleń")
                                .setPositiveButton("Ponów", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        permission.requestMultiplePermissions();
                                    }
                                })
                                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();
                    }
                    return;
                }
            }
        }
    }

    /**
     *Funkcja potrzebna do nadpisania działania aplikacji po naciśnięciu przycisku cofnij
     */
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
