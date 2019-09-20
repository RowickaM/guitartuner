package com.rowicka.gitartuner.recognize;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.utility.NavigationBottom;
import com.rowicka.gitartuner.utility.Permission;

public class RecognizeActivity extends Activity {
    Button buttonRec;
    TextView title, author, album, timecode;
    Permission permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize);

        permission = new Permission(this);
        permission.permissions();

        new NavigationBottom(this);

        buttonRec = (Button) findViewById(R.id.recordAudio);
        title = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);
        album = (TextView) findViewById(R.id.album);
        timecode = (TextView) findViewById(R.id.timeCode);

        buttonRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecord();
            }
        });
    }

    private void startRecord(){

    }
}
