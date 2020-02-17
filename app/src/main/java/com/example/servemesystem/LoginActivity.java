package com.example.servemesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private TextView textRegister;
    private TextView textForgotPassword;
    private Button buttonLogin;
    private EditText editEmail, editPassword;



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
            private String email, password;
            @Override
            public void onClick(View view) {
                this.getLogindetails();
                if(this.isLoginDetailsValid()) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(intent);
                    finish();
                }
            }

            private boolean isLoginDetailsValid() {
                if(email.isEmpty()){
                    editEmail.setError(getString(R.string.error_required_field_email));
                    editEmail.requestFocus();
                    return false;
                }
                if(password.isEmpty()){
                    editPassword.setError(getString(R.string.error_required_field_password));
                    editPassword.requestFocus();
                    return false;
                }
                return true;
            }

            private void getLogindetails() {
                email = editEmail.getText().toString();
                password = editPassword.getText().toString();
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
        this.textForgotPassword = findViewById(R.id.textForgotPassword);
        this.textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });
    }

    private void setAllEditTexts() {
        this.editEmail = findViewById(R.id.editEmail);
        this.editPassword = findViewById(R.id.editPassword);
    }
}
