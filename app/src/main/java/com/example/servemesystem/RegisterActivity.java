package com.example.servemesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword, etContactNumber;
    private EditText etAddress, etState, etCountry, etZipCode;
    private Button buttonBack, buttonRegister;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String collectionPath;
    private boolean contactjudge;
    private int signal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setAllEditTexts();
        this.setAllButtons();
        this.configDB();

    }

    private void configDB(){
        collectionPath="test";
        mAuth=FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        contactjudge=false;
        signal=1;
    }

    private void setAllButtons() {
        this.defineAllButtons();
        this.setAllButtonListeners();
    }

    private void defineAllButtons() {
        this.buttonBack = findViewById(R.id.buttonBack);
        this.buttonRegister = findViewById(R.id.buttonRegister);
    }

    private void setAllButtonListeners() {
        this.setButtonBackListener();
        this.setButtonRegisterListener();
    }

    private void setButtonBackListener() {
        this.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity();
            }
        });
    }

    private void setButtonRegisterListener() {
        this.buttonRegister.setOnClickListener(new View.OnClickListener() {
            String firstName, lastName, email, password, confirmPassword, contactNumber, address;
            String state, country, zipcode;
            //user information
            Map<String, Object> user=new HashMap<>();

            @Override
            public void onClick(View view) {
                this.getAllValuesFromEditTexts();
                if (this.isFormValuesValid()) {
                    //send request to Server
//                    createUserWithEmail(email,password);
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //Register successful
                                    if (task.isSuccessful()) {
                                        // insert data to firestore
                                        insertData();
                                        Log.d("onSuccess", "createUserWithEmail:success");
                                        Toast.makeText(
                                                RegisterActivity.this,
                                                getString(R.string.message_registration_success),
                                                Toast.LENGTH_LONG).show();
                                        startLoginActivity();
                                        //Register failed
                                    } else {
                                        Log.w("onFailure", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Registration Failed",
                                                Toast.LENGTH_SHORT).show();
                                        etEmail.setError(getString(R.string.error_email_already_exist));
                                        etEmail.requestFocus();
                                    }
                                }
                            });
                }
            }

            private boolean isFormValuesValid() {
                if (this.isAnyFieldEmpty() || this.isEmailExist() || this.isPasswordFormatWrong()
                        || this.isPasswordsNotMatching() || this.isContactNumberFormatNotValid() ||
                        this.isContactNumberExist() || this.isAddressValueNotValid()) {

                    return false;
                }
                Log.w("pass", "pass formatcheck");
                return true;
            }

            private boolean isContactNumberExist() {
                //request server to check if contact number exist
////                final Boolean[] judge=new Boolean[1];
//                db.collection(collectionPath)
//                        .whereEqualTo("contact", contactNumber)
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                //find ContactNumber
//                                if (task.isSuccessful()) {
//                                    Log.d("contactExist","contactExist");
//                                    contactjudge=true;
////                                    etContactNumber.setError(getString(R.string.error_contact_already_exist));
////                                    etContactNumber.requestFocus();
//                                    //not find ContactNumber
//                                } else {
//                                    contactjudge=false;
//                                    Log.d("contactNotExist", "Error getting documents: ", task.getException());
//                                }
//                            }
//                        });
                return false;
            }

            private boolean isAnyFieldEmpty() {
                if(firstName.isEmpty()){
                    etFirstName.setError(getString(R.string.error_required_field_first_name));
                    etFirstName.requestFocus();
                    return true;
                }
                if(email.isEmpty()){
                    etEmail.setError(getString(R.string.error_required_field_email));
                    etEmail.requestFocus();
                    return true;
                }
                if(password.isEmpty())
                {
                    etPassword.setError(getString(R.string.error_required_field_password));
                    etPassword.requestFocus();
                    return true;
                }
                if(confirmPassword.isEmpty())
                {
                    etConfirmPassword.setError(getString(R.string.error_required_field_confirm_password));
                    etConfirmPassword.requestFocus();
                    return true;
                }
                if(contactNumber.isEmpty())
                {
                    etContactNumber.setError(getString(R.string.error_required_field_contact_number));
                    etConfirmPassword.requestFocus();
                    return true;
                }
                if(address.isEmpty())
                {
                    etAddress.setError(getString(R.string.error_required_field_address));
                    etAddress.requestFocus();
                    return true;
                }
                if(state.isEmpty())
                {
                    etState.setError(getString(R.string.error_required_field_state));
                    etState.requestFocus();
                    return true;
                }
                if(country.isEmpty())
                {
                    etCountry.setError(getString(R.string.error_required_field_country));
                    etCountry.requestFocus();
                    return true;
                }
                if(zipcode.isEmpty())
                {
                    etZipCode.setError(getString(R.string.error_required_field_zip_code));
                    etZipCode.requestFocus();
                    return true;
                }
                return false;
            }

            private boolean isEmailExist() {
                //request server to verify
                final Boolean[] judge=new Boolean[1];
                judge[0]=false;
                db.collection(collectionPath)
                        .whereEqualTo("email", email)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                //find email
                                if (task.isSuccessful()) {
                                        Log.d("emailExist","emailExist");
                                    judge[0]=true;
                                    //not find email
                                } else {
                                    judge[0]=false;
                                    Log.d("emailNotExist", "Error getting documents: ", task.getException());
                                }
                            }
                        });
                if(judge[0]==true){
                    etEmail.setError(getString(R.string.error_email_already_exist));
                    etEmail.requestFocus();
                    return true;
                }


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmail.setError(getString(R.string.error_format_email_id));
                    etEmail.requestFocus();
                    return true;
                }

                return false;

            }

            private boolean isPasswordFormatWrong() {
                if(!(password.length() >= 8 && password.matches("[a-zA-Z0-9!@$%^&*()]+"))) {
                    etPassword.setError(getString(R.string.error_format_password));
                    etPassword.requestFocus();
                    return true;
                }
                return  false;
            }

            private boolean isPasswordsNotMatching() {
                if(!password.equals(confirmPassword)) {
                    etConfirmPassword.setError(getString(R.string.error_passwords_unmatch));
                    etConfirmPassword.requestFocus();
                    return true;
                }
                return false;
            }

            private boolean isContactNumberFormatNotValid() {
                if(!(contactNumber.length() == 10 && contactNumber.matches("[0-9]+"))) {
                    etContactNumber.setError(getString(R.string.error_format_contact_number));
                    etContactNumber.requestFocus();
                    return true;
                }
                return false;
            }

            private boolean isAddressValueNotValid() {
                //check if gmap says  its valid
                return false;
            }

            private void getAllValuesFromEditTexts() {
                this.firstName = etFirstName.getText().toString();
                this.lastName = etLastName.getText().toString();
                this.email = etEmail.getText().toString();
                this.password = etPassword.getText().toString();
                this.confirmPassword=etConfirmPassword.getText().toString();
                this.contactNumber = etContactNumber.getText().toString();
                this.address = etAddress.getText().toString();
                this.state = etState.getText().toString();
                this.country = etCountry.getText().toString();
                this.zipcode = etZipCode.getText().toString();
                this.user.put("firstname",firstName);
                this.user.put("lastname",lastName);
                this.user.put("email",email);
                this.user.put("password",password);
                this.user.put("contact",contactNumber);
                this.user.put("address",address);
                this.user.put("state",state);
                this.user.put("country",country);
                this.user.put("zip",zipcode);

            }
            private void insertData(){
                Log.w("InIt","I'm here");
                db.collection(collectionPath)
                        .add(this.user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.w("onSuccess","DocumentSnapshot added with ID: "+documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("onFailure","Error adding document", e);
                            }
                        });
            }
        });
    }

    private void setAllEditTexts() {
        this.etFirstName = findViewById(R.id.editFirstName);
        this.etLastName = findViewById(R.id.editLastName);
        this.etEmail = findViewById(R.id.editEmail);
        this.etPassword = findViewById(R.id.editPassword);
        this.etConfirmPassword = findViewById(R.id.editConfirmPassword);
        this.etContactNumber = findViewById(R.id.editContactNumber);
        this.etAddress = findViewById(R.id.editAddress);
        this.etState = findViewById(R.id.editState);
        this.etCountry = findViewById(R.id.editCountry);
        this.etZipCode = findViewById(R.id.editZipcode);
    }

    private void startLoginActivity(){
        Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intentLogin);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            // do something on back.


            startLoginActivity();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}