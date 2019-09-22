package com.rowicka.gitartuner.learning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.learning.chords.Chords;
import com.rowicka.gitartuner.learning.chords.ChordsAdapter;
import com.rowicka.gitartuner.utility.NavigationBottom;
import com.rowicka.gitartuner.utility.NavigationTop;

import java.util.ArrayList;

public class ListOfChordsActivity extends Activity {

    private ListView listView;
    private ChordsAdapter chordsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learning);

        new NavigationBottom(this);
        new NavigationTop(this);


        listView = (ListView) findViewById(R.id.chordsGroupList);

        ArrayList<Chords> list = new ArrayList<>();
        list.add(new Chords("A", "11"));
        list.add(new Chords("A", "11"));
        list.add(new Chords("A", "11"));

        chordsAdapter = new ChordsAdapter(this, list);
        listView.setAdapter(chordsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(ListOfChordsActivity.this, ShowChordActivity.class));
            }
        });
    }
}
