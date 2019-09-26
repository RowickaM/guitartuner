package com.rowicka.gitartuner.learning;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.collections.chords.ChordsCollection;
import com.rowicka.gitartuner.learning.chords.Chords;
import com.rowicka.gitartuner.learning.chordsGroup.ChordsGroup;
import com.rowicka.gitartuner.learning.chordsGroup.ChordsGroupAdapter;
import com.rowicka.gitartuner.profile.AuthFirebase;
import com.rowicka.gitartuner.profile.LoginActivity;
import com.rowicka.gitartuner.utility.NavigationBottom;
import com.rowicka.gitartuner.utility.NavigationTop;

import java.util.ArrayList;

public class BasicLearningActivity extends Activity {

    private ListView listView;
    private ChordsGroupAdapter chordsGroupAdapter;
    private ArrayList<ChordsGroup> list;

    private static final String TAG = "BasicLearningActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learning);

        new NavigationBottom(this);
        new NavigationTop(this);

        AuthFirebase auth = new AuthFirebase(this);
        if(!auth.checkUserLogin()){
            startActivity(new Intent(BasicLearningActivity.this, LoginActivity.class));
        }

        listView = (ListView) findViewById(R.id.chordsGroupList);
        list = new ArrayList<>();

        list.add(new ChordsGroup("Grupa 1", "D-dur;E-moll;G-dur"));
        list.add(new ChordsGroup("grupa2", "A-dur;A-moll;C-dur;E-dur"));
        list.add(new ChordsGroup("grupa3", "D-moll;F-dur;A7;E5;E7"));
        list.add(new ChordsGroup("grupa4", "A5;C5;D5;G5"));
        list.add(new ChordsGroup("grupa5", "G-moll;C-moll;B7;D7;G7"));

        chordsGroupAdapter = new ChordsGroupAdapter(BasicLearningActivity.this, list);
        listView.setAdapter(chordsGroupAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BasicLearningActivity.this, ListOfChordsActivity.class);
                StringBuilder sb = new StringBuilder();
                for (String str : list.get(i).getInclude()) {
                    sb.append(str);
                    sb.append(";");
                }
                intent.putExtra("keys", sb.toString());
                switch (i) {
                    case 0:
                        intent.putExtra("group", "group1");
                        break;
                    case 1:
                        intent.putExtra("group", "group2");
                        break;
                    case 2:
                        intent.putExtra("group", "group3");
                        break;
                    case 3:
                        intent.putExtra("group", "group4");
                        break;
                    case 4:
                        intent.putExtra("group", "group5");
                        break;
                }

                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
