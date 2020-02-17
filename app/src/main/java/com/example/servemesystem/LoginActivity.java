package com.example.servemesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private Button buttonBack;
    private Button buttonLogin;
    private EditText emailTextView, passwordTextView;
    private FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                get email,password
                final String email, password;
                email = emailTextView.getText().toString().trim();
                password = passwordTextView.getText().toString().trim();
                Log.d("InIt",email+":"+password);

//                 login existing user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                            //Intent intent = new Intent()
                                        } else {
                                            //login failed
                                            Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
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
        emailTextView=findViewById(R.id.logEmail);
        passwordTextView=findViewById(R.id.logPassword);

    }
}
