package com.aghabasit.finalprojectei;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    private EditText mEmail;
    private EditText mPassword;
    private Button loginButton;
    private TextView mForgotPassword;
    private TextView mSignup;

    private ProgressDialog mDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}