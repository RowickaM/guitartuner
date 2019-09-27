package com.rowicka.gitartuner.leaderboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.collections.leaderboard.Leaderboard;
import com.rowicka.gitartuner.collections.leaderboard.LeaderboardCollection;
import com.rowicka.gitartuner.utility.NavigationBottom;
import com.rowicka.gitartuner.utility.NavigationLeaderboard;
import com.rowicka.gitartuner.utility.NavigationTop;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LeaderboardActivity extends Activity {

    private ListView listView;
    private LeaderboardAdapter adapter;
    ArrayList<Leaderboard> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        new NavigationTop(this);
        new NavigationBottom(this);
        final NavigationLeaderboard nav = new NavigationLeaderboard(this);

        //do pobrania danych z bazy
        FirebaseAuth auth = FirebaseAuth.getInstance();

        final LeaderboardCollection leaderboardCollection = new LeaderboardCollection(Objects.requireNonNull(auth.getCurrentUser()).getUid());

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Ładowanie danych");
        dialog.show();

        leaderboardCollection.getCollectionByUID(dialog);
        listView = (ListView) findViewById(R.id.leaderboardList);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                list = leaderboardCollection.getLeaderCollection();
                list = getAll();
                adapter = new LeaderboardAdapter(LeaderboardActivity.this,
                        list, null);
                listView.setAdapter(adapter);


            }
        });

        //do nawigacji pomiędzy grupami
        TextView[] tab = nav.getTab();
        for (int i = 0; i < tab.length; i++) {
            final int finalI = i;
            tab[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nav.setCheck(finalI);
                    if (finalI == 0) {
                        list = getAll();
                        adapter = new LeaderboardAdapter(LeaderboardActivity.this,
                                list, null);
                    } else {
                        list = getGroup("group" + finalI);
                        adapter = new LeaderboardAdapter(LeaderboardActivity.this,
                                list, "group"+finalI);
                    }
                    listView.setAdapter(adapter);
                }
            });
        }

    }

    private ArrayList<Leaderboard> getAll() {
        Leaderboard[] leaderboards = list.toArray(new Leaderboard[list.size()]);
        Leaderboard temp;
        for (int i = 0; i < leaderboards.length; i++) {
            for (int j = 0; j < leaderboards.length - 1; j++)
                if (leaderboards[j].getAllPoints() < leaderboards[j + 1].getAllPoints()) {
                    temp = leaderboards[j];
                    leaderboards[j] = leaderboards[j + 1];
                    leaderboards[j + 1] = temp;
                }
        }

        return new ArrayList<Leaderboard>(Arrays.asList(leaderboards));
    }

    private ArrayList<Leaderboard> getGroup(String str) {
        Leaderboard[] leaderboards = list.toArray(new Leaderboard[list.size()]);
        Leaderboard temp;
        for (int i = 0; i < leaderboards.length; i++) {
            for (int j = 0; j < leaderboards.length - 1; j++)
                if (leaderboards[j].getPointsByGroup(str) < leaderboards[j + 1].getPointsByGroup(str)) {
                    temp = leaderboards[j];
                    leaderboards[j] = leaderboards[j + 1];
                    leaderboards[j + 1] = temp;
                }
        }

        return new ArrayList<Leaderboard>(Arrays.asList(leaderboards));
    }

}

