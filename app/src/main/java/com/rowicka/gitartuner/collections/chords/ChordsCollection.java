package com.rowicka.gitartuner.collections.chords;

import android.app.ProgressDialog;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rowicka.gitartuner.learning.chord.Chord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChordsCollection {

    private Chord chord;
    private FirebaseFirestore db;
    private static final String TAG = "ChordsCollection";

    public ChordsCollection() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void fetchChord(final ProgressDialog dialog, final String group, final String key) {

        db.collection("chords")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                if (document.getId().equals(group)) {
                                        chord = getValuesOfKeyChord(document, key);
                                        queryAttempt(chord, dialog, group, key);
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public Chord getChord() {
        return this.chord;
    }

    private void queryAttempt(final Chord chord, final ProgressDialog dialog, final String groupName, final String chordName){
        final FirebaseAuth auth = FirebaseAuth.getInstance();


        db.collection("leaderboard").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                if (document.getId().equals(Objects.requireNonNull(auth.getCurrentUser()).getUid())) {
                                    float[] result = getValueOfArray(
                                            (Map<String, Object>) document.getData(),
                                            (Map<String, Object>) Objects.requireNonNull(document.get(groupName)),
                                            chordName);
                                    chord.setAttemptAndPoints(result[0], result[1]);
                                    chord.setAllInGroup(result[2]);
                                    chord.setAllForUser(result[3]);
                                    dialog.dismiss();
                                }
                            }

                        }else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private float[] getValueOfArray(Map<String, Object> all, Map<String, Object> result, String name) {
        float allForUser = Float.parseFloat(Objects.requireNonNull(all.get("all")).toString());
        float allForGroup = Float.parseFloat(Objects.requireNonNull(result.get("all")).toString());

        Map<String, Object> array = new HashMap<>();
        array = (Map<String, Object>)result.get(name);
        if (array != null)
            return new float[]{
                    Float.parseFloat(Objects.requireNonNull(array.get("attempt")).toString()),
                    Float.parseFloat(Objects.requireNonNull(array.get("points")).toString()),
                    allForGroup,
                    allForUser
            };
        else return null;
    }

    private Chord getValuesOfKeyChord(QueryDocumentSnapshot document, String key) {

        Map<String, Object> objJSON = new HashMap<>();
        objJSON = (Map<String, Object>) document.getData();
        ArrayList array = (ArrayList) objJSON.get(key);
        HashMap schema = (HashMap) array.get(1);
        HashMap correctFreq = (HashMap) array.get(0);


        return new Chord(
                key,
                new String[]{ // wymagane częstotliwości
                        (String) correctFreq.get("StringE"),
                        (String) correctFreq.get("StringA"),
                        (String) correctFreq.get("StringD"),
                        (String) correctFreq.get("StringG"),
                        (String) correctFreq.get("StringH"),
                        (String) correctFreq.get("StringE"),
                },
                schema.get("schema").toString()//schemat
        );
    }

}
