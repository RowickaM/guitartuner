package com.rowicka.gitartuner.collections.chords;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rowicka.gitartuner.learning.chord.Chord;
import com.rowicka.gitartuner.learning.chords.Chords;
import com.rowicka.gitartuner.learning.chordsGroup.ChordsGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChordsCollection {

    private ArrayList<Chords> listOfChords;
    private ArrayList<ChordsGroup> listOfChordsGroup;
    private Chord chord;
    private FirebaseFirestore db;
    private static final String TAG = "ChordsCollection";

    public ChordsCollection() {
        listOfChords = new ArrayList<>();
        listOfChordsGroup = new ArrayList<>();
        this.db = FirebaseFirestore.getInstance();
    }

    public void fetchChord(final ProgressDialog dialog, final String group, final String key) {

        db.collection("chords")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equals(group)) {
                                    if (document.getData().containsKey(key)) {
                                        chord = getValuesOfKeyChord(document, key);
                                        //tu zapytanie o liczbę prób

                                        queryAttempt(chord, dialog, group, key);
                                    }
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void queryAttempt(final Chord chord, final ProgressDialog dialog, final String groupName, final String chordName){
        final FirebaseAuth auth = FirebaseAuth.getInstance();


        db.collection("leaderboard").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equals(auth.getCurrentUser().getUid())) {
                                    Log.d(TAG, "onComplete: "+document.getData().toString());
                                    float[] result = getValueOfArray(
                                            (Map<String, Object>) document.getData(),
                                            (Map<String, Object>) document.get(groupName),
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
        float allForUser = Float.parseFloat(all.get("all").toString());

        Log.d(TAG, "getValueOfArray: "+result.get("all"));
        float allForGroup = Float.parseFloat(result.get("all").toString());

        Map<String, Object> array = new HashMap<>();
        array = (Map<String, Object>)result.get(name);
        return new float[]{
                Float.parseFloat(array.get("attempt").toString()),
                Float.parseFloat(array.get("points").toString()),
                allForGroup,
                allForUser
        };
    }

    private Chord getValuesOfKeyChord(QueryDocumentSnapshot document, String key) {
        String[] values = document.get(key).toString().split(",");
        values[0] = values[0].substring(2, values[0].length());
        values[5] = values[5].substring(0, values[5].length() - 1);
        values[6] = values[6].trim().substring(1, values[6].length() - 2);

        return new Chord(
                key,
                new String[]{
                        values[0].split("=")[1],
                        values[5].split("=")[1],
                        values[3].split("=")[1],
                        values[2].split("=")[1],
                        values[4].split("=")[1],
                        values[1].split("=")[1]
                },
                values[6].substring(7, values[6].length() - 1));
    }

    public Chord getChord() {
        return this.chord;
    }

    public ArrayList<Chords> getChords() {
        return this.listOfChords;
    }

    public ArrayList<ChordsGroup> getChordsGroup() {
        return this.listOfChordsGroup;
    }


    private Map<String, Object> grupa2() {
        Map<String, Object> group = new HashMap<>();

        //C-dur
        Map<String, Object> frequency = new HashMap<>();
        frequency.put("StringE", "0");
        frequency.put("StringA", "131");
        frequency.put("StringD", "165");
        frequency.put("StringG", "196");
        frequency.put("StringH", "262");
        frequency.put("Stringe", "330");
        Map<String, Object> schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FC-dur.png?alt=media&token=07f648be-0303-49e6-be89-55336556dff0");
        group.put("C-dur", Arrays.asList(frequency, schema));

        //A-dur
        frequency = new HashMap<>();
        frequency.put("StringE", "0");
        frequency.put("StringA", "110");
        frequency.put("StringD", "165");
        frequency.put("StringG", "220");
        frequency.put("StringH", "277");
        frequency.put("Stringe", "330");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FA-dur.png?alt=media&token=8598341f-ce19-4ea0-8666-ab0546a67774");
        group.put("A-dur", Arrays.asList(frequency, schema));

        //E-dur
        frequency = new HashMap<>();
        frequency.put("StringE", "82");
        frequency.put("StringA", "123");
        frequency.put("StringD", "165");
        frequency.put("StringG", "208");
        frequency.put("StringH", "247");
        frequency.put("Stringe", "330");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FE-dur.png?alt=media&token=941d4102-aeca-4969-be9e-a257822c5f97");
        group.put("E-dur", Arrays.asList(frequency, schema));

        //A-mol
        frequency = new HashMap<>();
        frequency.put("StringE", "0");
        frequency.put("StringA", "110");
        frequency.put("StringD", "165");
        frequency.put("StringG", "220");
        frequency.put("StringH", "262");
        frequency.put("Stringe", "330");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2Fa-moll.png?alt=media&token=92fc2569-05f9-4f38-8cf5-42eeb689f5f4");
        group.put("A-moll", Arrays.asList(frequency, schema));

        return group;
    }

    private Map<String, Object> grupa3() {
        Map<String, Object> group = new HashMap<>();

//        F-dur
        Map<String, Object> frequency = new HashMap<>();
        frequency.put("StringE", "87");
        frequency.put("StringA", "131");
        frequency.put("StringD", "175");
        frequency.put("StringG", "220");
        frequency.put("StringH", "262");
        frequency.put("Stringe", "349");
        Map<String, Object> schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FF-dur.png?alt=media&token=dec47365-5e03-4e79-b507-60685edd7edf");
        group.put("F-dur", Arrays.asList(frequency, schema));

        //d-moll
        frequency = new HashMap<>();
        frequency.put("StringE", "0");
        frequency.put("StringA", "0");
        frequency.put("StringD", "147");
        frequency.put("StringG", "220");
        frequency.put("StringH", "262");
        frequency.put("Stringe", "370");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2Fd-moll.png?alt=media&token=0f6e063f-cd04-4b08-9742-971be86ec8b0");
        group.put("D-moll", Arrays.asList(frequency, schema));

        //A7
        frequency = new HashMap<>();
        frequency.put("StringE", "0");
        frequency.put("StringA", "110");
        frequency.put("StringD", "156");
        frequency.put("StringG", "196");
        frequency.put("StringH", "262");
        frequency.put("Stringe", "330");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FA7.png?alt=media&token=9f5e320b-794d-4db1-8a7a-25f36bd811ab");
        group.put("A7", Arrays.asList(frequency, schema));

        //E7
        frequency = new HashMap<>();
        frequency.put("StringE", "82");
        frequency.put("StringA", "123");
        frequency.put("StringD", "147");
        frequency.put("StringG", "208");
        frequency.put("StringH", "247");
        frequency.put("Stringe", "330");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FE7.png?alt=media&token=e5157907-090e-4ce3-8ddc-aee98387398e");
        group.put("E7", Arrays.asList(frequency, schema));

        //E5
        frequency = new HashMap<>();
        frequency.put("StringE", "82");
        frequency.put("StringA", "123");
        frequency.put("StringD", "165");
        frequency.put("StringG", "0");
        frequency.put("StringH", "0");
        frequency.put("Stringe", "0");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FE5.png?alt=media&token=12b60212-8bed-4adc-8952-0f0770ac5a3b");
        group.put("E5", Arrays.asList(frequency, schema));

        return group;
    }

    private Map<String, Object> grupa4() {
        Map<String, Object> group = new HashMap<>();

//        G5
        Map<String, Object> frequency = new HashMap<>();
        frequency.put("StringE", "98");
        frequency.put("StringA", "147");
        frequency.put("StringD", "196");
        frequency.put("StringG", "0");
        frequency.put("StringH", "0");
        frequency.put("Stringe", "0");
        Map<String, Object> schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FG5.png?alt=media&token=39822055-dedb-46b7-aa72-3520915e0adb");
        group.put("G5", Arrays.asList(frequency, schema));

//D5
        frequency = new HashMap<>();
        frequency.put("StringE", "0");
        frequency.put("StringA", "147");
        frequency.put("StringD", "220");
        frequency.put("StringG", "294");
        frequency.put("StringH", "0");
        frequency.put("Stringe", "0");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FD5.png?alt=media&token=fef2a0ab-2f9b-4a15-ae7d-a9ca53cb1047");
        group.put("D5", Arrays.asList(frequency, schema));

        //C5
        frequency = new HashMap<>();
        frequency.put("StringE", "0");
        frequency.put("StringA", "131");
        frequency.put("StringD", "196");
        frequency.put("StringG", "262");
        frequency.put("StringH", "0");
        frequency.put("Stringe", "0");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FC5.png?alt=media&token=fed2c6fc-6a39-41b2-bf9a-7a370343829f");
        group.put("C5", Arrays.asList(frequency, schema));

        //A5
        frequency = new HashMap<>();
        frequency.put("StringE", "110");
        frequency.put("StringA", "165");
        frequency.put("StringD", "220");
        frequency.put("StringG", "0");
        frequency.put("StringH", "0");
        frequency.put("Stringe", "0");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FA5.png?alt=media&token=28b69242-5bbe-4ce1-bc78-fdd6314487d4");
        group.put("A5", Arrays.asList(frequency, schema));

        return group;
    }

    private Map<String, Object> grupa5() {
        Map<String, Object> group = new HashMap<>();

//        c-moll
        Map<String, Object> frequency = new HashMap<>();
        frequency.put("StringE", "98");
        frequency.put("StringA", "131");
        frequency.put("StringD", "196");
        frequency.put("StringG", "262");
        frequency.put("StringH", "311");
        frequency.put("Stringe", "392");
        Map<String, Object> schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2Fc-moll.png?alt=media&token=2e4fb5e3-66d2-413e-8afd-70bc05ecdd5e");
        group.put("C-moll", Arrays.asList(frequency, schema));

//g-moll
        frequency = new HashMap<>();
        frequency.put("StringE", "98");
        frequency.put("StringA", "147");
        frequency.put("StringD", "196");
        frequency.put("StringG", "233");
        frequency.put("StringH", "294");
        frequency.put("Stringe", "392");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2Fg-moll.png?alt=media&token=67e4b433-0b4d-4fc6-8a9a-f4eb0c91e214");
        group.put("G-moll", Arrays.asList(frequency, schema));

        //G7
        frequency = new HashMap<>();
        frequency.put("StringE", "98");
        frequency.put("StringA", "123");
        frequency.put("StringD", "147");
        frequency.put("StringG", "196");
        frequency.put("StringH", "247");
        frequency.put("Stringe", "349");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FG7.png?alt=media&token=e0a37bfc-373b-4f19-837b-f9754d86f22d");
        group.put("G7", Arrays.asList(frequency, schema));

        //D7
        frequency = new HashMap<>();
        frequency.put("StringE", "0");
        frequency.put("StringA", "0");
        frequency.put("StringD", "147");
        frequency.put("StringG", "220");
        frequency.put("StringH", "262");
        frequency.put("Stringe", "370");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2Fd7.png?alt=media&token=71134039-5af9-474f-aaf2-91125889841b");
        group.put("D7", Arrays.asList(frequency, schema));

        //B7
        frequency = new HashMap<>();
        frequency.put("StringE", "0");
        frequency.put("StringA", "123");
        frequency.put("StringD", "156");
        frequency.put("StringG", "220");
        frequency.put("StringH", "247");
        frequency.put("Stringe", "370");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FB7.png?alt=media&token=81b1ad21-96e1-41bb-8394-0d44519dbedf");
        group.put("B7", Arrays.asList(frequency, schema));

        return group;
    }

    private Map<String, Object> grupa1() {
        Map<String, Object> group = new HashMap<>();

//        G-dur
        Map<String, Object> frequency = new HashMap<>();
        frequency.put("StringE", "98");
        frequency.put("StringA", "123");
        frequency.put("StringD", "147");
        frequency.put("StringG", "196");
        frequency.put("StringH", "294");
        frequency.put("Stringe", "392");
        Map<String, Object> schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FG-dur.png?alt=media&token=d06e755b-d555-479e-af39-70c0fbfa48c4");
        group.put("G-dur", Arrays.asList(frequency, schema));

        //D-dur
        frequency = new HashMap<>();
        frequency.put("StringE", "0");
        frequency.put("StringA", "0");
        frequency.put("StringD", "147");
        frequency.put("StringG", "220");
        frequency.put("StringH", "294");
        frequency.put("Stringe", "370");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2FD-dur.png?alt=media&token=882fe534-df0e-481f-9292-8be03e44d52b");
        group.put("D-dur", Arrays.asList(frequency, schema));

        //e-moll
        frequency = new HashMap<>();
        frequency.put("StringE", "82");
        frequency.put("StringA", "123");
        frequency.put("StringD", "165");
        frequency.put("StringG", "196");
        frequency.put("StringH", "247");
        frequency.put("Stringe", "330");
        schema = new HashMap<>();
        schema.put("schema", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/schema%2Fe-moll.png?alt=media&token=aa56a6d4-9ec4-4d84-96e6-8e222ae36d91");
        group.put("E-moll", Arrays.asList(frequency, schema));

        return group;
    }

    public void fillChordsCollection() {
        db.collection("chords")
                .document("group1")
                .set(grupa1())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");

                    }
                });

        db.collection("chords")
                .document("group2")
                .set(grupa2())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");

                    }
                });

        db.collection("chords")
                .document("group3")
                .set(grupa3())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");

                    }
                });
        db.collection("chords")
                .document("group4")
                .set(grupa4())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");

                    }
                });

        db.collection("chords")
                .document("group5")
                .set(grupa5())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");

                    }
                });


    }
}
