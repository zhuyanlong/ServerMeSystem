package com.example.servemesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private TextView textRegister;
    private TextView textForgotPassword;
    private Button buttonBack;
    private Button buttonLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setAllEditTexts();
        this.setAllTextViews();
        this.setAllButtons();
    }

    private void setAllButtons() {
        this.defineButtons();
        this.setAllButtonListener();
    }

    private void setAllButtonListener() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent()
            }
        });
    }

    private void defineButtons() {
        buttonLogin = findViewById(R.id.buttonLogin);


    }

    private void setAllTextViews() {
        this.setRegisterTextView();
        this.setForgotPasswordTextView();

    }

    private void setRegisterTextView() {
        this.textRegister = findViewById(R.id.textRegister);
        this.textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }

    private void setForgotPasswordTextView() {

    }

    private void setAllEditTexts() {
    }
}
