package com.base7.jd.birdapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.base7.jd.birdapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText txtEmail, txtPassword;

    private TextView btnSignUp;

    private FirebaseAuth fbAuth;

    private Button btnLogin;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fbAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnSignUp = (TextView) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
                finish();
            }
        });

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("Logging In...");
                dialog.show();
                fbAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveLogin();
                            dialog.hide();
                            Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, UserInfo.class));
                            finish();
                        } else {
                            dialog.hide();
                            Toast.makeText(Login.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void saveLogin() {
        SharedPreferences preferences = this.getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("username", txtEmail.getText().toString());
        editor.putString("password", txtPassword.getText().toString());
        editor.apply();
    }
}
