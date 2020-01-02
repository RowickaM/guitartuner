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
import java.util.Map;
import java.util.Objects;


public class ChordsCollection {
    /**
     * Zawiera metody wykorzystywane do połączeń z kolekcjami chords oraz leaderboard
     * chord obiekt typu Chord, który będzie zwracany
     * db obiekt bazy danych
     */
    private Chord chord;
    private FirebaseFirestore db;
    private static final String TAG = "ChordsCollection";

    /**
     * Utworzenie połączenia z bazą danych
     */
    public ChordsCollection() {
        this.db = FirebaseFirestore.getInstance();
    }

    /**
     * Pobranie akordu z bazy danych
     * oraz określenie liczby prób dla dengo akodu
     * i aktualnie zdobytych punktów dla akordu, grupy i wszystkich punktów użytkownika
     * @param dialog okno ładowania
     * @param group nazwa grupy akordu
     * @param key nazwa akordu
     */
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

    /**
     * Zwrócony zostaje utworzony obiekt chord
     * @return obiekt typu Chord
     */
    public Chord getChord() {
        return this.chord;
    }

    /**
     * Pobranie informacji o danym użytkowniku o ilości prób dla danego akordu,
     * punktów za dany akord, grupę akordów
     * oraz całkowitą ilość punktów dla danego użytkowika
     * wszystkie te dane zostają dospisane do oniektu chord
     * @param chord obiekt typu Chord
     * @param dialog okno ładowania
     * @param groupName nazwa grupy akordu
     * @param chordName nazwa akordu
     */
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
                                            document.getData(),
                                            (Map<String, Object>) Objects.requireNonNull(document.get(groupName)),
                                            chordName);
                                    if (result != null) {
                                        chord.setAttemptAndPoints(result[0], result[1]);
                                        chord.setAllInGroup(result[2]);
                                        chord.setAllForUser(result[3]);
                                        dialog.dismiss();
                                    }
                                }
                            }

                        }else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    /**
     * Określenie ilości prób
     * oraz zdobytych punktów w danym akordzie,
     * grupie akordów oraz wszystch punktów przypisanych do użytkowmika
     * @param all cały dokument pobrany z bazy
     * @param result lista z dokumentu dotycząca danej grupy akordu
     * @param name nazwa akordu
     * @return tablica z informacjami o numerze próby dla danego akordu,
     * zebranych punktach z danego akordu,
     * zebranych punktach w danej grupie akordu
     * oraz punktach przypisanych do danego użytkownika
     */
    private float[] getValueOfArray(Map<String, Object> all, Map<String, Object> result, String name) {
        float allForUser = Float.parseFloat(Objects.requireNonNull(all.get("all")).toString());
        float allForGroup = Float.parseFloat(Objects.requireNonNull(result.get("all")).toString());

        Map<String, Object> array;
        array = (Map<String, Object>) result.get(name);
        if (array != null)
            return new float[]{
                    Float.parseFloat(Objects.requireNonNull(array.get("attempt")).toString()),
                    Float.parseFloat(Objects.requireNonNull(array.get("points")).toString()),
                    allForGroup,
                    allForUser
            };
        else return null;
    }

    /**
     *Pobiera dokument z akordami dla danej grupy i pobiera wymagane częstotliwości dla danego akordu
     * określonego za pomocą zmiennej key
     * @param document dokument z listą akordów przypisanych do poszczególnej grupy akordów
     * @param key nazwa akordu
     * @return obiekt typu Chord z wartościami wymaganych częstotliwości oraz przypisanym schematem do akordu
     */
    private Chord getValuesOfKeyChord(QueryDocumentSnapshot document, String key) {
        Map<String, Object> objJSON;
        objJSON = document.getData();
        ArrayList array = (ArrayList) objJSON.get(key);
        HashMap schema;
        if (array != null) {
            schema = (HashMap) array.get(1);

            HashMap correctFreq = (HashMap) array.get(0);
            return new Chord(
                    key,
                    new String[]{ // wymagane częstotliwości
                            (String) correctFreq.get("StringE"),
                            (String) correctFreq.get("StringA"),
                            (String) correctFreq.get("StringD"),
                            (String) correctFreq.get("StringG"),
                            (String) correctFreq.get("StringH"),
                            (String) correctFreq.get("Stringe"),
                    },
                    schema.get("schema").toString()//schemat
            );
        }
        return null;
    }

}
