package com.rowicka.gitartuner.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.learning.BasicLearningActivity;
import com.rowicka.gitartuner.utility.CheckConnection;
import com.rowicka.gitartuner.utility.NavigationBottom;

public class LoginActivity extends Activity {

    EditText login, password;
    Button loginButton;
    TextView toRegistration;

    /**
     * Funkcja potrzebna do stworzenia okna. Jest ona nadpisywana z klasy Activity.
     * Jest jedną z kliku dostępnych stanów z cyku życia aktywności.
     *
     * @param savedInstanceState zawiera informacje o poprzednim stanie
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new NavigationBottom(this);

        CheckConnection network = new CheckConnection(this);
        if (network.isConnected()) {

            final AuthFirebase auth = new AuthFirebase(this);
            if (auth.checkUserLogin()) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(new Intent(this, BasicLearningActivity.class));
                overridePendingTransition(0, 0);
            }

            login = findViewById(R.id.login);
            password = findViewById(R.id.password);
            loginButton = findViewById(R.id.loginButton);
            toRegistration = findViewById(R.id.toRegistration);

            toRegistration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                    overridePendingTransition(0, 0);
                }
            });

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!login.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {
                        auth.userLogin(login.getText().toString(), password.getText().toString());
                    }
                }
            });
        } else {
            network.notConnectedAlertDialog().show();
        }

    }
}
