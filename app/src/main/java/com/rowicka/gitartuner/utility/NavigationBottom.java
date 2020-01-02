package com.rowicka.gitartuner.utility;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.learning.BasicLearningActivity;
import com.rowicka.gitartuner.metronome.MetronomeActivity;
import com.rowicka.gitartuner.tuner.TunerActivity;


public class NavigationBottom {

    private Activity activity;
    private ImageView imgTuner, imgMetro, imgLearn, imgInfo;
    private TextView textTuner, textMetro, textLearn, textInfo;

    public NavigationBottom(final Activity activity) {
        this.activity = activity;

        LinearLayout toTuner = activity.findViewById(R.id.toTuner);
        LinearLayout toMetronome = activity.findViewById(R.id.toMetronome);
        LinearLayout toLearn = activity.findViewById(R.id.toLearn);
        LinearLayout toInfo = activity.findViewById(R.id.toInfo);

        imgTuner = activity.findViewById(R.id.tuner_ico);
        imgMetro = activity.findViewById(R.id.metronome_ico);
        imgLearn = activity.findViewById(R.id.learn_ico);
        imgInfo = activity.findViewById(R.id.info_ico);

        textTuner = activity.findViewById(R.id.tuner_txt);
        textMetro = activity.findViewById(R.id.metronome_txt);
        textLearn = activity.findViewById(R.id.learn_txt);
        textInfo = activity.findViewById(R.id.info_txt);

        setMark(activity.getClass());

        addListener(toTuner, TunerActivity.class);
        addListener(toMetronome, MetronomeActivity.class);
        addListener(toLearn, BasicLearningActivity.class);
        addListener(toInfo, InfoActivity.class);
    }

    private void addListener(final LinearLayout btn, final Class to){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!activity.getClass().equals(to)){
                    changeTo(to);
                }
            }
        });
    }

    private void changeTo(Class cls){
        activity.finish();
        activity.overridePendingTransition(0, 0);
        activity.startActivity(new Intent(activity, cls));
        activity.overridePendingTransition(0, 0);
    }

    private void setColor(TextView text, ImageView img, int drawable) {
        img.setImageDrawable(this.activity.getResources().getDrawable(drawable));
        text.setTextColor(Color.WHITE);
    }

    private void setMark(Class cls){
        setUnColor(textTuner, imgTuner, R.drawable.ic_tuner);
        setUnColor(textMetro, imgMetro, R.drawable.ic_metronome);
        setUnColor(textLearn, imgLearn, R.drawable.ic_chords);
        setUnColor(textInfo, imgInfo, R.drawable.ic_info);
        switch (cls.getSimpleName()){
            case "TunerActivity":
                setColor(textTuner, imgTuner, R.drawable.ic_tuner_color);
                break;
            case "MetronomeActivity":
                setColor(textMetro, imgMetro, R.drawable.ic_metronome_color);
                break;
            case "BasicLearningActivity":
            case "ListOfChordsActivity":
            case "ShowChordActivity":
            case "LoginActivity":
            case "LeaderboardActivity":
                setColor(textLearn, imgLearn, R.drawable.ic_chords_color);
                break;
            case "InfoActivity":
                setColor(textInfo, imgInfo, R.drawable.ic_info_color);
                break;
        }

    }

    private void setUnColor(TextView text, ImageView img, int drawable) {
        img.setImageDrawable(this.activity.getResources().getDrawable(drawable));
        text.setTextColor(this.activity.getResources().getColor(R.color.colorPrimaryDark));
    }
}