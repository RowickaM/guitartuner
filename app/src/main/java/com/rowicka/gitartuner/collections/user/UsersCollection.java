package com.rowicka.gitartuner.collections.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UsersCollection {

    private FirebaseFirestore db;
    private String UID;
    private static String TAG = "UsersCollection";
    private User user;


    /**
     * Konstruktor klasy UsersCollection ustalający aktualnie zalogowonego użytkownika
     * oraz ustanawiący połączenie z bazą
     *
     * @param uid indywidualny ID użytkownika
     */
    public UsersCollection(String uid) {
        this.db = FirebaseFirestore.getInstance();
        this.UID = uid;
    }

    /**
     * @return zwraca obiekt typu User
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Funkcja pobierająca informacje o użytkowniku
     *
     * @param dialog - okno ładowania
     */
    public void readUser(final ProgressDialog dialog) {
        this.db.collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                if (document.getId().equals(UID)) {
                                    String email = Objects.requireNonNull(document.getData().get("email")).toString();
                                    String nick = Objects.requireNonNull(document.getData().get("nick")).toString();
                                    String avatar = Objects.requireNonNull(document.getData().get("avatar")).toString();
                                    setUser(email, nick, avatar);
                                    if (dialog != null) {
                                        dialog.dismiss();
                                    }
                                    break;
                                }
                            }
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    /**
     * Funkcja aktualizująca informacje o użytkowniku w bazie danych
     *
     * @param activity okno aktualnie wyświetlane w aplikacji
     * @param nick     string zawierający nowy nick użytkownika
     * @param url      string zawierający nowy url avatara użytkownika
     */
    public void updateUser(final Activity activity, String nick, String url) {
        Map<String, Object> user = new HashMap<>();
        if (!url.equals(""))
            user.put("avatar", url);
        if (!nick.equals(""))
            user.put("nick", nick);

        db.collection("users").document(UID)
                .set(user, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity, "Dane zostały zmienione", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Nastąpił nieoczekiwany błąd. Spróbuj ponownie później.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Funkcja do tworzenia nowego użytkownika w bazie danych
     *
     * @param email string z emailem nowego użytkownika
     */
    public void addUser(String email) {

        Map<String, Object> user = new HashMap<>();

        user.put("avatar", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-5.png?alt=media&token=652814b1-2e80-426d-9341-b3dc6bc66194");

        if (!email.equals("")) {
            user.put("nick", email.split("@")[0]);

            user.put("email", email);

        }

        db.collection("users").document(UID)
                .set(user, SetOptions.merge());
    }

    /**
     * Funkcja ustawiająca email, nick oraz avatar użytkownika
     *
     * @param email  string z emailem użytkownika
     * @param nick   string z nickiem użytkownika
     * @param avatar string z url avatara użytkownika
     */
    private void setUser(String email, String nick, String avatar) {
        this.user = new User(email, nick, avatar);
    }


}
