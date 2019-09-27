package com.rowicka.gitartuner.utility;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.rowicka.gitartuner.R;

public class BeatsPoints {

    private ImageView[] txArray;
    private Activity activity;

   public BeatsPoints(Activity activity) {
        this.activity = activity;
        txArray = new ImageView[]{
                (ImageView) activity.findViewById(R.id.dot1),
                (ImageView) activity.findViewById(R.id.dot2),
                (ImageView) activity.findViewById(R.id.dot3),
                (ImageView) activity.findViewById(R.id.dot4),
                (ImageView) activity.findViewById(R.id.dot5),
                (ImageView) activity.findViewById(R.id.dot6),
                (ImageView) activity.findViewById(R.id.dot7),
                (ImageView) activity.findViewById(R.id.dot8),
                (ImageView) activity.findViewById(R.id.dot9),
                (ImageView) activity.findViewById(R.id.dot10),
                (ImageView) activity.findViewById(R.id.dot11),
                (ImageView) activity.findViewById(R.id.dot12),
        };

    }

    public void setVisible(double beats){
       for(int i=0; i < beats; i++){
            txArray[i].setVisibility(View.VISIBLE);
       }
    }

    public void setInvisible(){
        for (ImageView imageView : txArray) {
            imageView.setVisibility(View.GONE);
        }
    }

    public ImageView[] getArray(){
       return txArray;
    }

}
