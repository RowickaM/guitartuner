package com.rowicka.gitartuner.utility;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.metronome.MetronomeActivity;
import com.rowicka.gitartuner.tuner.TunerActivity;


public class NavigationBottom {

    Activity act;

    public NavigationBottom(final Activity activity) {
        act = activity;
        LinearLayout toTuner = (LinearLayout) activity.findViewById(R.id.toTuner);
        LinearLayout toMetronome = (LinearLayout) activity.findViewById(R.id.toMetronome);
        ImageView imgTuner = (ImageView) activity.findViewById(R.id.tuner_ico);
        ImageView imgMetro = (ImageView) activity.findViewById(R.id.metronome_ico);
        TextView textTuner = (TextView) activity.findViewById(R.id.tuner_txt);
        TextView textMetro = (TextView) activity.findViewById(R.id.metronome_txt);

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

        if (act.getClass().equals(TunerActivity.class)){
            imgTuner.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_tuner_color));
            textTuner.setTextColor(Color.WHITE);
            imgMetro.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_metronome));
            textMetro.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        }else if (act.getClass().equals(MetronomeActivity.class)){
            imgTuner.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_tuner));
            textTuner.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            imgMetro.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_metronome_color));
            textMetro.setTextColor(Color.WHITE);
        }

    }
}