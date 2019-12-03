package com.rowicka.gitartuner.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rowicka.gitartuner.collections.leaderboard.LeaderboardCollection;
import com.rowicka.gitartuner.collections.user.UsersCollection;

public class AuthFirebase {

    private FirebaseAuth firebaseAuth;
    private Context context;

    public AuthFirebase(Context context) {
        this.context = context;
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean checkUserLogin() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        return currentUser != null;
    }

    public void logout() {
        firebaseAuth.signOut();
    }

    public void userLogin(String email, String password) {
        password = password.trim();
        email = email.trim();
        final ProgressDialog dialog = showLoadingDialog();
        dialog.show();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                dialog.dismiss();
                                updateUI(user);
                                context.startActivity(new Intent(context, ProfileActivity.class));
                            }
                            else {
                                dialog.dismiss();
                                updateUI(null);
                                Toast.makeText(context, "Nie poprawne dane", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

    public void registerUser(String email, String password, String repeatPassword) {

        email = email.trim();
        password = password.trim();
        repeatPassword = repeatPassword.trim();

        final ProgressDialog dialog = showLoadingDialog();
        dialog.show();

        if (!TextUtils.isEmpty(email) && checkPassword(password, repeatPassword)) {
            final String finalEmail = email;
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Registration", "createUserWithEmail:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();

                                UsersCollection uc = new UsersCollection(user.getUid());
                                uc.addUser(
                                        finalEmail);

                                LeaderboardCollection lc = new LeaderboardCollection(user.getUid());
                                lc.createDocument();

                                dialog.dismiss();
                                updateUI(user);
                            } else {
                                Log.w("Registration", "createUserWithEmail:failure", task.getException());
                                dialog.dismiss();
                                Toast.makeText(context, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });

        } else {
            dialog.dismiss();
            Toast.makeText(context, "Wymagane pola są puste", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPassword(String password, String repeatPassword) {
        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(repeatPassword)) {
            if (password.equals(repeatPassword)) {
                return true;
            } else {
                Toast.makeText(context, "Wprowadzone hasła są różne", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(context, "Wprowadzone hasła są różne", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            context.startActivity(new Intent(context, ProfileActivity.class));
        }
    }

    private ProgressDialog showLoadingDialog(){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Ładowanie");
        dialog.setCancelable(false);
        return dialog;
    }
}
