package com.rowicka.gitartuner.utility;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rowicka.gitartuner.R;

import java.util.ArrayList;
import java.util.Arrays;

public class InfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        new NavigationBottom(this);

        ListView list = (ListView) findViewById(R.id.listAbout);
        String[] about = getResources().getStringArray(R.array.about);
        ArrayList<String> aboutL = new ArrayList<String>(Arrays.asList(about));
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, aboutL);

        list.setAdapter(adapter);
    }
}
