package com.rowicka.gitartuner.utility;

import android.app.Activity;
import android.widget.ImageView;

public class IcoUser {
    private String url;
    private ImageView display;

    public IcoUser(Activity activity, String url, int resourceDisplay){
        this.url = url;
        this.display = (ImageView) activity.findViewById(resourceDisplay);
    }

    public String getImgUrl() {
        return this.url;
    }

    public ImageView getDisplay() {
        return display;
    }
}
