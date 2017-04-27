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

public class SignUp extends AppCompatActivity {

    private Button btnSignUp;

    private FirebaseAuth fbAuth;

    private EditText txtEmail, txtPassword;

    private TextView btnLogin;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fbAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);

        checkLogin();

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnLogin = (TextView) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
                finish();
            }
        });

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("Signing Up...");
                dialog.show();
                fbAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.hide();
                            Toast.makeText(SignUp.this, "Successfully SignUp", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUp.this, Login.class));
                            finish();
                        } else {
                            dialog.hide();
                            Toast.makeText(SignUp.this, "Error", Toast.LENGTH_LONG).show();
                            txtEmail.setText(task.toString());
                        }
                    }
                });
            }
        });
    }

    private void checkLogin() {
        dialog.setMessage("Checking User Login...");
        dialog.show();
        SharedPreferences preferences = this.getSharedPreferences("Login", Context.MODE_PRIVATE);

        String username = preferences.getString("username", "no");
        String password = preferences.getString("password", "no");

        if (username.equals("no") && password.equals("no")) {
            dialog.hide();
            Toast.makeText(SignUp.this, "No Login", Toast.LENGTH_LONG).show();
        } else {
            Login(username, password);
        }
    }

    private void Login(String username, String password) {

        fbAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    dialog.hide();
                    Toast.makeText(SignUp.this, "Successfully Login", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignUp.this, UserInfo.class));
                    finish();
                } else {
                    dialog.hide();
                    Toast.makeText(SignUp.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
