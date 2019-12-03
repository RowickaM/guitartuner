package com.rowicka.gitartuner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.rpc.Help;
import com.rowicka.gitartuner.learning.ShowChordActivity;
import com.rowicka.gitartuner.utility.NavigationBottom;
import com.rowicka.gitartuner.utility.NavigationTop;
import com.squareup.picasso.Picasso;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ImageView img1 = (ImageView)findViewById(R.id.imageView4);
        ImageView img2 = (ImageView)findViewById(R.id.imageView5);
        ImageView img3 = (ImageView)findViewById(R.id.imageView);

        new NavigationTop(this);
        new NavigationBottom(this);

        Picasso.with(HelpActivity.this)
                .load("https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FC-dur.png?alt=media&token=07f648be-0303-49e6-be89-55336556dff0")
                .resize(400,300)
                .placeholder(R.mipmap.ic_guitar_round)
                .into(img1);
        Picasso.with(HelpActivity.this)
                .load("https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FD5.png?alt=media&token=fef2a0ab-2f9b-4a15-ae7d-a9ca53cb1047")
                .resize(400,300)
                .placeholder(R.mipmap.ic_guitar_round)
                .into(img2);
        Picasso.with(HelpActivity.this)
                .load("https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2Flewa_reka.png?alt=media&token=144d9fa1-50d6-47a7-b9b8-d7b923b435e1")
                .placeholder(R.mipmap.ic_guitar_round)
                .into(img3);

    }
}
