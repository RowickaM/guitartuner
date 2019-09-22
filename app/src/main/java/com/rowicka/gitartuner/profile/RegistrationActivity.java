package com.rowicka.gitartuner.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.rowicka.gitartuner.R;

public class RegistrationActivity extends Activity {

    EditText login, password, passwordRepeat;
    Button loginButton;
    TextView toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final AuthFirebase auth = new AuthFirebase(this);

        login = (EditText) findViewById(R.id.loginSignup);
        password = (EditText) findViewById(R.id.passwordSignup);
        passwordRepeat = (EditText) findViewById(R.id.passwordRepeatSignup);
        loginButton = (Button) findViewById(R.id.registerButton);
        toLogin = (TextView) findViewById(R.id.toLogin);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.registerUser(
                        login.getText().toString(),
                        password.getText().toString(),
                        passwordRepeat.getText().toString()
                );
            }
        });


    }
}
