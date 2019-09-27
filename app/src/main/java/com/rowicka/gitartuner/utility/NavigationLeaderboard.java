package com.rowicka.gitartuner.utility;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;
import com.rowicka.gitartuner.R;

public class NavigationLeaderboard {

    private TextView[] tab;
    private Activity activity;

    public NavigationLeaderboard(Activity activity){
        this.activity = activity;
        tab = new TextView[]{
                (TextView) activity.findViewById(R.id.all_leader),
                (TextView) activity.findViewById(R.id.gr1_leader),
                (TextView) activity.findViewById(R.id.gr2_leader),
                (TextView) activity.findViewById(R.id.gr3_leader),
                (TextView) activity.findViewById(R.id.gr4_leader),
                (TextView) activity.findViewById(R.id.gr5_leader)
        };
    }

    public TextView[] getTab(){
        return this.tab;
    }

    public void setCheck(int position){
        for (int i= 0; i<tab.length; i++){
            setUncheck(i);
        }
        tab[position].setTextColor(activity.getResources().getColor(R.color.text));
    }

    private void setUncheck(int position){
        tab[position].setTextColor(Color.WHITE);
    }
}
