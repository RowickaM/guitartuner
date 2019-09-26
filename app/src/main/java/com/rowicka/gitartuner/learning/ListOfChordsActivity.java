package com.rowicka.gitartuner.learning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.learning.chords.Chords;
import com.rowicka.gitartuner.learning.chords.ChordsAdapter;
import com.rowicka.gitartuner.profile.AuthFirebase;
import com.rowicka.gitartuner.profile.LoginActivity;
import com.rowicka.gitartuner.utility.NavigationBottom;
import com.rowicka.gitartuner.utility.NavigationTop;

import java.util.ArrayList;

public class ListOfChordsActivity extends Activity {

    private static final String TAG = "ListOfChordsActivity";

    private ListView listView;
    private ChordsAdapter chordsAdapter;
    private String nameGroup;

    private ArrayList<Chords> list;
    private String[] keys;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learning);

        new NavigationBottom(this);
        new NavigationTop(this);

        AuthFirebase auth = new AuthFirebase(this);
        if(!auth.checkUserLogin()){
            startActivity(new Intent(ListOfChordsActivity.this, LoginActivity.class));
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nameGroup = bundle.getString("group");

            keys = bundle.getString("keys").split(";");
        }

        listView = (ListView) findViewById(R.id.chordsGroupList);

        list = new ArrayList<>();
        for (String key : keys) {
            list.add(new Chords(key));
        }

        chordsAdapter = new ChordsAdapter(ListOfChordsActivity.this, list);
        listView.setAdapter(chordsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Intent intent = new Intent(ListOfChordsActivity.this, ShowChordActivity.class);
                intent.putExtra("group", nameGroup);
                intent.putExtra("key", keys[i]);


                Dialog dialog = new AlertDialog.Builder(ListOfChordsActivity.this)
                        .setTitle("Wybierz tryb")
                        .setMessage("W trybie ranking masz cztery próby. " +
                                "Za każdą będziesz dostawał punkty, które będą liczone do rankingów. " +
                                "\nW trybie nauka punkty nie są naliczane.")
                        .setPositiveButton("Ranking", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                intent.putExtra("isLearning", 0);
                                intent.putExtra("progress", 1);
                                StringBuilder sb = new StringBuilder();
                                for (String str : keys) {
                                    sb.append(str);
                                    sb.append(";");
                                }
                                intent.putExtra("keys", sb.toString());
                                startActivity(intent);
                            }
                        })
                        .setNeutralButton("Anuluj", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Nauka", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                intent.putExtra("isLearning", 1);
                                startActivity(intent);
                            }
                        })
                        .show();


            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
