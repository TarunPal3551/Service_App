package com.example.serviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class SignUpActivity extends AppCompatActivity {
    TextView textViewLogin;
    MaterialButton buttonSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        textViewLogin=findViewById(R.id.tvLogin);
        buttonSignup=findViewById(R.id.signupButton);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SignUpActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SignUpActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
