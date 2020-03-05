package com.example.servemesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servemesystem.helper.MD5;
import com.example.servemesystem.helper.SaveSharedPreference;
import com.example.servemesystem.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class LoginActivity extends AppCompatActivity {
    public static String TAG = "ServeMeSystem :: LoginActivity";

    private TextView textRegister;
    private TextView textForgotPassword;
    private Button buttonLogin;
    private EditText editEmail, editPassword;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(this.isUserLoggedIn())
            startHomeActivity();
        this.setAllEditTexts();
        this.setAllTextViews();
        this.setAllButtons();
    }

    private boolean isUserLoggedIn() {
        user = SaveSharedPreference.getUserObject(LoginActivity.this);
        if(user != null)
            return true;
        return false;
    }

    private void setAllButtons() {
        this.defineButtons();
        this.setAllButtonListener();
    }

    private void setAllButtonListener() {
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            private String email, password;
            @Override
            public void onClick(View view) {
                this.getLogindetails();
                if(this.isLoginDetailsValid()) {
                    //firebase authentication
                    mAuth.signInWithEmailAndPassword(email, MD5.getMd5(password))
                            .addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                getUserInfoFromServer(email);
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

    private void startHomeActivity(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("user", user);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
        finish();
    }

    private void getUserInfoFromServer(String emailId){
        firestore.collection("users")
                .whereEqualTo("email", emailId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<User> users = null;
                        if (task.isSuccessful())
                        {
                            users = task.getResult().toObjects(User.class);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, getString(R.string.error_generic), Toast.LENGTH_LONG).show();
                        }

                        if (users != null && users.size() == 1)
                        {
                            user = users.get(0);
                            Log.i(TAG, user.toString());
                            SaveSharedPreference.setUserObject(LoginActivity.this, user);
                            startHomeActivity();

                        }
                    }
                });
    }
    }
