package com.rowicka.gitartuner.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.learning.BasicLearningActivity;
import com.rowicka.gitartuner.utility.NavigationBottom;

public class LoginActivity extends Activity {

    EditText login, password;
    Button loginButton;
    TextView toRegistration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new NavigationBottom(this);
        final AuthFirebase auth = new AuthFirebase(this);
        if (auth.checkUserLogin()){
            startActivity(new Intent(this, BasicLearningActivity.class));
        }

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        toRegistration = (TextView) findViewById(R.id.toRegistration);

        toRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.userLogin(login.getText().toString(), password.getText().toString());
            }
        });

    }
}
