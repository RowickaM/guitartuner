package com.rowicka.gitartuner.collections.leaderboard;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.rowicka.gitartuner.collections.user.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LeaderboardCollection {
    private static final String TAG = "LeaderboardCollection";
    private String UID;
    private FirebaseFirestore db;
    private Map<String, Object> collection;
    private ArrayList<Leaderboard> leaderCollection = new ArrayList<>();


    public LeaderboardCollection(String uid) {
        this.collection = new HashMap<>();
        this.UID = uid;
        this.db = FirebaseFirestore.getInstance();
    }

    public void createDocument() {
        Map<String, String> chordTab = new HashMap<>();
        chordTab.put("attempt", "0");
        chordTab.put("points", "0");

        Map<String, Object> leaderboardCollection = new HashMap<>();

        leaderboardCollection.put("all", 0);
        Map<String, Object> group1 = new HashMap<>();
        group1.put("all", 0);


        group1.put("E-moll", chordTab);

        group1.put("D-dur", chordTab);
        group1.put("G-dur", chordTab);

        leaderboardCollection.put("group1", group1);

        Map<String, Object> group2 = new HashMap<>();
        group2.put("all", 0);
        group2.put("E-dur", chordTab);
        group2.put("A-moll", chordTab);
        group2.put("A-dur", chordTab);
        group2.put("C-dur", chordTab);

        leaderboardCollection.put("group2", group2);

        Map<String, Object> group3 = new HashMap<>();
        group3.put("all", 0);
        group3.put("F-dur", chordTab);
        group3.put("D-moll", chordTab);
        group3.put("A7", chordTab);
        group3.put("E5", chordTab);
        group3.put("E7", chordTab);

        leaderboardCollection.put("group3", group3);

        Map<String, Object> group4 = new HashMap<>();
        group4.put("all", 0);
        group4.put("A5", chordTab);
        group4.put("C5", chordTab);
        group4.put("D5", chordTab);
        group4.put("G5", chordTab);

        leaderboardCollection.put("group4", group4);

        Map<String, Object> group5 = new HashMap<>();
        group5.put("all", 0);
        group5.put("B7", chordTab);
        group5.put("D7", chordTab);
        group5.put("G7", chordTab);
        group5.put("C-moll", chordTab);
        group5.put("G-moll", chordTab);

        leaderboardCollection.put("group5", group5);

        db.collection("leaderboard")
                .document(this.UID)
                .set(leaderboardCollection)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                });

    }

    public void updateCollectionForUser(final String group,
                                        final String chordName, final double points, final float pointsGroup,
                                        final float pointsUser, final String iteration) {

        collection.put("attempt", iteration);
        collection.put("points", points);

        Map<String, Object> temp = new HashMap<>();
        temp.put(chordName, collection);
        temp.put("all", pointsGroup);

        Map<String, Object> finall = new HashMap<>();
        finall.put(group, temp);
        finall.put("all", pointsUser);

        db.collection("leaderboard")
                .document(UID)
                .set(finall, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                });
    }
    public void getCollectionByUID(final ProgressDialog dialog){
        db.collection("leaderboard").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult())){

                                final String id = document.getId();
                                final Map<String, Object> statistic =  getStatistic(document.getData());
                                leaderCollection.add(new Leaderboard(id, statistic));
                               dialog.cancel();
                            }
                        }else {
                            Log.w(TAG, "Error getting documents.", task.getException());

                        }


                    }

                });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.setCancelable(false);
                dialog.show();
                db.collection("users").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for (QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult())){
                                        for (Leaderboard leaderboard: leaderCollection){
                                            if (document.getId().equals(leaderboard.getUid())){
                                                leaderboard.setUser(getUser(document.getData()));
                                                break;
                                            }
                                        }
                                    }
                                    dialog.dismiss();
                                }
                            }
                        });
            }
        });
    }

    private Map<String, Object> getStatistic(Map<String, Object> data) {

        Map<String, Object> statistic = new HashMap<>();
        statistic.put("all", data.get("all"));

        Map<String, Object> inGroup = new HashMap<>();
        inGroup = (Map<String, Object>) data.get("group1");
        statistic.put("group1", inGroup.get("all"));

        inGroup = (Map<String, Object>) data.get("group2");
        statistic.put("group2", inGroup.get("all"));

        inGroup = (Map<String, Object>) data.get("group3");
        statistic.put("group3", inGroup.get("all"));

        inGroup = (Map<String, Object>) data.get("group4");
        statistic.put("group4", inGroup.get("all"));

        inGroup = (Map<String, Object>) data.get("group5");
        statistic.put("group5", inGroup.get("all"));

        return statistic;
    }
    private User getUser(Map<String, Object> data){
        Log.d(TAG, "getUser: "+data.get("email"));
        return new User(Objects.requireNonNull(data.get("email")).toString(),
                Objects.requireNonNull(data.get("nick")).toString(),
                Objects.requireNonNull(data.get("avatar")).toString());
    }

    public ArrayList<Leaderboard> getLeaderCollection(){
        return this.leaderCollection;
    }

}
