package com.rowicka.gitartuner.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.utility.CheckConnection;

public class RegistrationActivity extends Activity {

    EditText login, password, passwordRepeat;
    Button loginButton;
    TextView toLogin;

    /**
     *Funkcja potrzebna do stworzenia okna. Jest ona nadpisywana z klasy Activity.
     * Jest jedną z kliku dostępnych stanów z cyku życia aktywności.
     * @param savedInstanceState zawiera informacje o poprzednim stanie
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        CheckConnection network = new CheckConnection(this);
        if (network.isConnected()) {

            final AuthFirebase auth = new AuthFirebase(this);

            login = findViewById(R.id.loginSignup);
            password = findViewById(R.id.passwordSignup);
            passwordRepeat = findViewById(R.id.passwordRepeatSignup);
            loginButton = findViewById(R.id.registerButton);
            toLogin = findViewById(R.id.toLogin);

            toLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    overridePendingTransition(0, 0);
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
        }else {
            network.notConnectedAlertDialog().show();
        }

    }
}
