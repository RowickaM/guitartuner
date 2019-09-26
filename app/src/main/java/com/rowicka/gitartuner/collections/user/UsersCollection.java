package com.rowicka.gitartuner.collections.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.rowicka.gitartuner.profile.ProfileActivity;

import java.util.HashMap;
import java.util.Map;

public class UsersCollection {

    private FirebaseFirestore db;
    private String UID;
    private static String TAG = "UsersCollection";
    User user;

    public String email;
    private boolean isExist = false;

    public UsersCollection(String uid) {
        this.db = FirebaseFirestore.getInstance();
        this.UID = uid;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(String email, String nick, String avatar) {
        this.user = new User(email, nick, avatar);
    }


    public boolean userIsExist(){
        return this.isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public void readUser(final ProgressDialog dialog) {
        this.db.collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equals(UID)) {
                                    String email = document.getData().get("email").toString();
                                    String nick = document.getData().get("nick").toString();
                                    String avatar = document.getData().get("avatar").toString();
                                    setUser(email, nick, avatar);
                                    setExist(true);
                                    dialog.dismiss();
                                    break;
                                }
                            }
                            dialog.dismiss();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void updateUser(Activity activity, String uid, String nick, String url) {
        Map<String, Object> user = new HashMap<>();
        if (!url.equals(""))
            user.put("avatar", url);
        if (!nick.equals(""))
            user.put("nick", nick);

        db.collection("users").document(uid)
                .set(user, SetOptions.merge());

        Toast.makeText(activity, "Dane zostały zmienione", Toast.LENGTH_SHORT).show();

    }

    public void addUser(String uid, String email) {
        Map<String, Object> user = new HashMap<>();
            user.put("avatar", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-5.png?alt=media&token=652814b1-2e80-426d-9341-b3dc6bc66194");
        if (!email.equals("")){
            user.put("nick", email);
            user.put("email", email);

        }

        db.collection("users").document(uid)
                .set(user, SetOptions.merge());
    }

}
