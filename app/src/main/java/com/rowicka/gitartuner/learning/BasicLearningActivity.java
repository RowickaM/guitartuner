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
import com.rowicka.gitartuner.utility.NavigationBottom;
import com.rowicka.gitartuner.utility.NavigationTop;

import java.util.ArrayList;

public class BasicLearningActivity extends Activity {

    private ListView listView;
    private ChordsGroupAdapter chordsGroupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learning);

        new NavigationBottom(this);
        new NavigationTop(this);

        listView = (ListView) findViewById(R.id.chordsGroupList);
        ArrayList<ChordsGroup> list = new ArrayList<>();
        list.add(new ChordsGroup("Grupa A", "e G C", "100.0"));
        list.add(new ChordsGroup("Grupa B", "d D a", "1.0"));
        list.add(new ChordsGroup("Grupa C", "A F E", "16.0"));
        list.add(new ChordsGroup("Grupa D", "e G C", "72.0"));
        list.add(new ChordsGroup("Grupa E", "d D a", "8.0"));
        list.add(new ChordsGroup("Grupa F", "A F E", "0.0"));
        list.add(new ChordsGroup("Grupa F", "A F E", "0.0"));
        list.add(new ChordsGroup("Grupa F", "A F E", "0.0"));
        list.add(new ChordsGroup("Grupa F", "A F E", "0.0"));
        list.add(new ChordsGroup("Grupa F", "A F E", "0.0"));
        list.add(new ChordsGroup("Grupa F", "A F E", "0.0"));
        list.add(new ChordsGroup("Grupa F", "A F E", "0.0"));
        list.add(new ChordsGroup("Grupa F", "A F E", "0.0"));
        list.add(new ChordsGroup("Grupa F", "A F E", "0.0"));

        chordsGroupAdapter = new ChordsGroupAdapter(this, list);
        listView.setAdapter(chordsGroupAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(BasicLearningActivity.this, ListOfChordsActivity.class));
            }
        });
    }
}
