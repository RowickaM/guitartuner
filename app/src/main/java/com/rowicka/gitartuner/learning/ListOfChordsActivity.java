package com.rowicka.gitartuner.learning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import com.rowicka.gitartuner.utility.CheckConnection;
import com.rowicka.gitartuner.utility.NavigationBottom;
import com.rowicka.gitartuner.utility.NavigationTop;
import java.util.ArrayList;
import java.util.Objects;

public class ListOfChordsActivity extends Activity {

    private String nameGroup;
    private String[] keys;

    /**
     *Funkcja potrzebna do stworzenia okna. Jest ona nadpisywana z klasy Activity.
     *Jest jedną z kliku dostępnych stanów z cyku życia aktywności.
     * @param savedInstanceState zawiera informacje o poprzednim stanie
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learning);

        new NavigationBottom(this);
        new NavigationTop(this);

        final AuthFirebase auth = new AuthFirebase(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nameGroup = bundle.getString("group");

            keys = Objects.requireNonNull(bundle.getString("keys")).split(";");
        }

        ListView listView = findViewById(R.id.chordsGroupList);

        ArrayList<Chords> list = new ArrayList<>();
        for (String key : keys) {
            list.add(new Chords(key));
        }

        ChordsAdapter chordsAdapter = new ChordsAdapter(ListOfChordsActivity.this, list);
        listView.setAdapter(chordsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckConnection network = new CheckConnection(ListOfChordsActivity.this);
                if (network.isConnected()) {
                    if (!auth.checkUserLogin()) {
                        new AlertDialog.Builder(ListOfChordsActivity.this)
                                .setTitle("Wymagane zalogowanie")
                                .setMessage("Do tej części aplikacji wymagane jest aktywne konto. Konto jest całkowicie darmowe.")
                                .setNeutralButton("Nie chce konta", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setPositiveButton("Przejdź do logowania", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                        overridePendingTransition(0, 0);
                                        startActivity(new Intent(ListOfChordsActivity.this, LoginActivity.class));
                                        overridePendingTransition(0, 0);
                                    }
                                })
                                .show();

                    } else {
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
                                        finish();
                                        overridePendingTransition(0, 0);
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
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
                                        finish();
                                        overridePendingTransition(0, 0);
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
                                    }
                                })
                                .show();
                    }
                } else {
                    network.notConnectedAlertDialog().show();
                }
            }
        });
    }

    /**
     *Funkcja potrzebna do nadpisania działania aplikacji po naciśnięciu przycisku cofnij
     */
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
