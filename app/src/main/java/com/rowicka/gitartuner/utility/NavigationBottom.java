package com.rowicka.gitartuner.utility;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.leaderboard.LeaderboardActivity;
import com.rowicka.gitartuner.learning.BasicLearningActivity;
import com.rowicka.gitartuner.learning.ListOfChordsActivity;
import com.rowicka.gitartuner.learning.ShowChordActivity;
import com.rowicka.gitartuner.metronome.MetronomeActivity;
import com.rowicka.gitartuner.profile.LoginActivity;
import com.rowicka.gitartuner.tuner.TunerActivity;


public class NavigationBottom {

    private Activity activity;

    public NavigationBottom(final Activity activity) {
        this.activity = activity;
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
                if (!NavigationBottom.this.activity.getClass().equals(TunerActivity.class)) {
                    NavigationBottom.this.activity.finish();
                    NavigationBottom.this.activity.overridePendingTransition(0, 0);
                    NavigationBottom.this.activity.startActivity(new Intent(NavigationBottom.this.activity, TunerActivity.class));
                    NavigationBottom.this.activity.overridePendingTransition(0, 0);
                }
            }
        });

        toMetronome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NavigationBottom.this.activity.getClass().equals(MetronomeActivity.class)) {
                    NavigationBottom.this.activity.finish();
                    NavigationBottom.this.activity.overridePendingTransition(0, 0);
                    NavigationBottom.this.activity.startActivity(new Intent(NavigationBottom.this.activity, MetronomeActivity.class));
                    NavigationBottom.this.activity.overridePendingTransition(0, 0);
                }
            }
        });


        toLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NavigationBottom.this.activity.getClass().equals(BasicLearningActivity.class)) {
                    NavigationBottom.this.activity.finish();
                    NavigationBottom.this.activity.overridePendingTransition(0, 0);
                    NavigationBottom.this.activity.startActivity(new Intent(NavigationBottom.this.activity, LoginActivity.class));
                    NavigationBottom.this.activity.overridePendingTransition(0, 0);
                }
            }
        });

        if (this.activity.getClass().equals(TunerActivity.class)) {
//            tuner
            setColor(textTuner, imgTuner, R.drawable.ic_tuner_color);
//            metronome
            setUnColor(textMetro, imgMetro, R.drawable.ic_metronome);
            //microphone
            setUnColor(textLearn, imgLearn, R.drawable.ic_chords);


        } else if (this.activity.getClass().equals(MetronomeActivity.class)) {
//            tuner
            setUnColor(textTuner, imgTuner, R.drawable.ic_tuner);
//            metronome
            setColor(textMetro, imgMetro, R.drawable.ic_metronome_color);
            //microphone
            setUnColor(textLearn, imgLearn, R.drawable.ic_chords);
        } else if (this.activity.getClass().equals(BasicLearningActivity.class)
                || this.activity.getClass().equals(ListOfChordsActivity.class)
                || this.activity.getClass().equals(LoginActivity.class)
                || this.activity.getClass().equals(LeaderboardActivity.class)
                || this.activity.getClass().equals(ShowChordActivity.class)) {
//            tuner
            setUnColor(textTuner, imgTuner, R.drawable.ic_tuner);
//            metronome
            setUnColor(textMetro, imgMetro, R.drawable.ic_metronome);
            //microphone
            setColor(textLearn, imgLearn, R.drawable.ic_chords_color);

        }

    }

    private void setColor(TextView text, ImageView img, int drawable) {
        img.setImageDrawable(this.activity.getResources().getDrawable(drawable));
        text.setTextColor(Color.WHITE);
    }

    private void setUnColor(TextView text, ImageView img, int drawable) {
        img.setImageDrawable(this.activity.getResources().getDrawable(drawable));
        text.setTextColor(this.activity.getResources().getColor(R.color.colorPrimaryDark));
    }
}