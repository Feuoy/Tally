package com.example.oneapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private final String TAG = "MainActivity";

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    Button btnCancel;
    CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "---------------------------------------------MainActivity---------"
                + "onCreate @" + this.hashCode() + "---------");

        txtUsername = (EditText) findViewById(R.id.etUsername);
        txtPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        cbRemember.setOnCheckedChangeListener(this);

        String username = (String) MySharedPreferences.getUsername(MainActivity.this);
        txtUsername.setText(username);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);

                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                intent.putExtra("USERNAME", username);
                intent.putExtra("PASSWORD", password);

                int requestCode = 2000;
                startActivityForResult(intent, requestCode);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtUsername.setText("");
                tx