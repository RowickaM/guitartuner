package com.rowicka.gitartuner.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.collections.user.User;
import com.rowicka.gitartuner.collections.user.UsersCollection;
import com.rowicka.gitartuner.learning.BasicLearningActivity;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends Activity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    AuthFirebase auth;
    User user;
    ProgressDialog dialog;

    private void updateUI() {
        TextView email = (TextView) findViewById(R.id.email);
        EditText nick = (EditText) findViewById(R.id.nick);
        ImageView avatar = (ImageView) findViewById(R.id.avatar);
        Button updateData = (Button) findViewById(R.id.change);

        email.setText(user.getEmail());
        nick.setText(user.getNick());

        //image
        Picasso.with(this)
                .load(user.getImgUrl())
                .placeholder(R.mipmap.ic_guitar_round)
                .into(avatar);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(new Intent(ProfileActivity.this, SelectAvatarActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final UsersCollection uc = new UsersCollection(firebaseAuth.getCurrentUser().getUid());
                EditText nick = (EditText) findViewById(R.id.nick);
                if (!TextUtils.isEmpty(nick.getText().toString().trim())) {
                    uc.updateUser(
                            ProfileActivity.this,
                            nick.getText().toString().trim(),
                            "");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dialog = new ProgressDialog(this);
        if (firebaseAuth.getCurrentUser().getUid() != null) {
            dialog.setMessage("Ładowanie danych");
            dialog.setCancelable(false);
            dialog.show();

            final UsersCollection uc = new UsersCollection(firebaseAuth.getCurrentUser().getUid());
            uc.readUser(dialog);

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if(uc.getUser() == null){
                        auth = new AuthFirebase(ProfileActivity.this);
                        auth.logout();
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                        overridePendingTransition(0, 0);
                    }else {
                        user = uc.getUser();
                        updateUI();
                    }
                }
            });


        } else {
            finish();
            overridePendingTransition(0, 0);
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(0, 0);
        }


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
        startActivity(new Intent(ProfileActivity.this, BasicLearningActivity.class));
        overridePendingTransition(0, 0);
    }
}
