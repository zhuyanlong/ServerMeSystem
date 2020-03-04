package com.example.servemesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private TextView textRegister;
    private TextView textForgotPassword;
    private Button buttonLogin;
    private EditText editEmail, editPassword;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setAllEditTexts();
        this.setAllTextViews();
        this.setAllButtons();
        this.setEditPasswordListener();
    }

    private void setAllButtons() {
        this.defineButtons();
        this.setAllButtonListener();
    }

    private void setEditPasswordListener(){
        editPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN)||(keyCode == KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editPassword.getWindowToken(), 0);
                    buttonLogin.performClick();
                }
                return false;
            }
        });
    }

    private void setAllButtonListener() {
        mAuth = FirebaseAuth.getInstance();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            private String email, password;
            @Override
            public void onClick(View view) {
                this.getLogindetails();
                if(this.isLoginDetailsValid()) {
                    //firebase authentication
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                                startActivity(intent);
                                                finish();
                                                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                            } else {
                                                //login failed
                                                Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
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