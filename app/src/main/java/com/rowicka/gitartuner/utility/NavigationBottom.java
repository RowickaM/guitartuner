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
import com.rowicka.gitartuner.learning.ListOfChordsActivity;
import com.rowicka.gitartuner.learning.ShowChordActivity;
import com.rowicka.gitartuner.metronome.MetronomeActivity;
import com.rowicka.gitartuner.profile.LoginActivity;
import com.rowicka.gitartuner.tuner.TunerActivity;


public class NavigationBottom {

    Activity act;

    public NavigationBottom(final Activity activity) {
        act = activity;
        LinearLayout toTuner = (LinearLayout) activity.findViewById(R.id.toTuner);
        LinearLayout toMetronome = (LinearLayout) activity.findViewById(R.id.toMetronome);
        LinearLayout toLearn = (LinearLayout) activity.findViewById(R.id.toLearn);

        ImageView imgTuner = (ImageView) activity.findViewById(R.id.tuner_ico);
        ImageView imgMetro = (ImageView) activity.findViewById(R.id.metronome_ico);
        ImageView imgLearn = (ImageView) activity.findViewById(R.id.learn_ico);

        TextView textTuner = (TextView) activity.findViewById(R.id.tuner_txt);
        TextView textMetro = (TextView) activity.findViewById(R.id.metronome_txt);
        TextView textLearn = (TextView) activity.findViewById(R.id.learn_txt);

        toTuner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!act.getClass().equals(TunerActivity.class)) {
                    act.startActivity(new Intent(act, TunerActivity.class));
                }
            }
        });

        toMetronome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!act.getClass().equals(MetronomeActivity.class)) {
                    act.startActivity(new Intent(act, MetronomeActivity.class));
                }
            }
        });


        toLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!act.getClass().equals(BasicLearningActivity.class)) {
                    act.startActivity(new Intent(act, LoginActivity.class));
                }
            }
        });

        if (act.getClass().equals(TunerActivity.class)) {
//            tuner
            setColor(activity, textTuner, imgTuner, R.drawable.ic_tuner_color);
//            metronome
            setUnColor(activity, textMetro, imgMetro, R.drawable.ic_metronome);
            //microphone
            setUnColor(activity, textLearn, imgLearn, R.drawable.ic_microphone);


        } else if (act.getClass().equals(MetronomeActivity.class)) {
//            tuner
            setUnColor(activity, textTuner, imgTuner, R.drawable.ic_tuner);
//            metronome
            setColor(activity, textMetro, imgMetro, R.drawable.ic_metronome_color);
            //microphone
            setUnColor(activity, textLearn, imgLearn, R.drawable.ic_microphone);
        } else if (act.getClass().equals(BasicLearningActivity.class)
                || act.getClass().equals(ListOfChordsActivity.class)
                || act.getClass().equals(LoginActivity.class)
                || act.getClass().equals(ShowChordActivity.class)) {
//            tuner
            setUnColor(activity, textTuner, imgTuner, R.drawable.ic_tuner);
//            metronome
            setUnColor(activity, textMetro, imgMetro, R.drawable.ic_metronome);
            //microphone
            setColor(activity, textLearn, imgLearn, R.drawable.ic_microphone_color);

        }

    }

    private void setColor(Activity activity, TextView textView, ImageView imgView, int drawable) {
        imgView.setImageDrawable(activity.getResources().getDrawable(drawable));
        textView.setTextColor(Color.WHITE);
    }

    private void setUnColor(Activity activity, TextView textView, ImageView imgView, int drawable) {
        imgView.setImageDrawable(activity.getResources().getDrawable(drawable));
        textView.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
    }
}