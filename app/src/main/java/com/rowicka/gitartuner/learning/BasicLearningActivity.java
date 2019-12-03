package com.rowicka.gitartuner.learning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.learning.chordsGroup.ChordsGroup;
import com.rowicka.gitartuner.learning.chordsGroup.ChordsGroupAdapter;
import com.rowicka.gitartuner.profile.AuthFirebase;
import com.rowicka.gitartuner.profile.LoginActivity;
import com.rowicka.gitartuner.utility.NavigationBottom;
import com.rowicka.gitartuner.utility.NavigationTop;

import java.util.ArrayList;

public class BasicLearningActivity extends Activity {

    private ArrayList<ChordsGroup> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learning);

        new NavigationBottom(this);
        new NavigationTop(this);

        AuthFirebase auth = new AuthFirebase(this);
        if(!auth.checkUserLogin()){
            finish();
            overridePendingTransition(0, 0);
            startActivity(new Intent(BasicLearningActivity.this, LoginActivity.class));
            overridePendingTransition(0, 0);
        }

        ListView listView = (ListView) findViewById(R.id.chordsGroupList);
        list = new ArrayList<>();

        list.add(new ChordsGroup("Grupa 1", "D-dur;E-moll;G-dur"));
        list.add(new ChordsGroup("Grupa 2", "A-dur;A-moll;C-dur;E-dur"));
        list.add(new ChordsGroup("Grupa 3", "D-moll;F-dur;A7;E5;E7"));
        list.add(new ChordsGroup("Grupa 4", "A5;C5;D5;G5"));
        list.add(new ChordsGroup("Grupa 5", "G-moll;C-moll;B7;D7;G7"));

        ChordsGroupAdapter chordsGroupAdapter = new ChordsGroupAdapter(BasicLearningActivity.this, list);
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
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
