package com.rowicka.gitartuner.utility;

import android.app.Activity;
import android.widget.ImageView;

public class IcoUser {
    private String url;
    private ImageView display;

    public IcoUser(Activity activity, String url, int resourceDisplay){
        this.url = "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2F" + url;
        this.display = (ImageView) activity.findViewById(resourceDisplay);
    }

    public String getImgUrl() {
        return this.url;
    }

    public ImageView getDisplay() {
        return display;
    }
}
