package com.example.servemesystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button buttonBack;
    private Button buttonSubmit;
    EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        this.setEditText();
        this.setAllButtons();
    }

    private void setEditText() {
        this.editEmail = findViewById(R.id.editEmail);
    }

    private void setAllButtons() {
        this.defineAllButtons();
        this.setAllButtonListeners();


    }

    private void defineAllButtons() {
        this.buttonBack = findViewById(R.id.buttonBack);
        this.buttonSubmit = findViewById(R.id.buttonSubmit);
    }

    private void setAllButtonListeners() {
        this.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity();
            }
        });

        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {

            private String isEmailId;
            @Override
            public void onClick(View view) {
                isEmailId = editEmail.getText().toString();
                if(this.isEmailIdValid()){
                    this.sendOtpRequestToServer();
                    this.startForgotPasswordEnterOtpFragment();

                }
            }

            private boolean isEmailIdValid() {
                return true;
            }

            private void sendOtpRequestToServer() {

                // make server do this
            }

            private void startForgotPasswordEnterOtpFragment() {
                Fragment fragment = new ForgotPasswordEnterOTPFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layoutForgotPassword, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

            });
    }

    private void startLoginActivity(){
        Intent intentLogin = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(intentLogin);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            // do something on back

            startLoginActivity();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
