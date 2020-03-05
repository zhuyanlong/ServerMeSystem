package com.example.servemesystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Patterns;
import android.util.Printer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.servemesystem.helper.MD5;
import com.example.servemesystem.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.PrivateKey;


public class RegisterCollectDetailsFragment extends Fragment {
    private EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword, etContactNumber;
    private EditText etAddress, etState, etCountry, etZipCode;
    private Button buttonBack, buttonRegister;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String collectionPath;
    private View view;
    private User user;

    public RegisterCollectDetailsFragment() {
        // Required empty public constructor
    }


    public static RegisterCollectDetailsFragment newInstance(String param1, String param2) {
        RegisterCollectDetailsFragment fragment = new RegisterCollectDetailsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register_collect_details, container, false);
        this.setAndroidBackKeyButton();
        this.setAllEditTexts();
        this.setAllButtons();
        this.configDB();
        return view;
    }

    private void setAndroidBackKeyButton() {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    startLoginActivity();
                    return true;
                }
                return false;
            }
        } );
    }

    private void configDB(){
        collectionPath="users";
        mAuth= FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
    }

    private void setAllButtons() {
        this.defineAllButtons();
        this.setAllButtonListeners();
    }

    private void defineAllButtons() {
        this.buttonBack = view.findViewById(R.id.buttonBack);
        this.buttonRegister = view.findViewById(R.id.buttonRegister);
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
            @Override
            public void onClick(View view) {
                this.getAllValuesFromEditTexts();
                this.setAllValues();
                if (this.isFormValuesValid()) {
                    startNextFragment();
                    //send request to Server
//                    createUserWithEmail(email,password);
//                    mAuth.createUserWithEmailAndPassword(email, MD5.getMd5(password))
//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    //Register successful
//                                    if (task.isSuccessful()) {
//                                        Log.d("onSuccess", "createUserWithEmail:success");
//                                        Toast.makeText(
//                                                getActivity(),
//                                                getString(R.string.message_registration_success),
//                                                Toast.LENGTH_LONG).show();
//                                        FirebaseUser user = mAuth.getCurrentUser();
//                                        String uid=user.getUid();
//                                        // insert data to firestore
//                                        insertData(uid);
//                                        startNextFragment();
//                                        //Register failed
//                                    } else {
//                                        Log.w("onFailure", "createUserWithEmail:failure", task.getException());
//                                        Toast.makeText(getActivity(), "Registration Failed",
//                                                Toast.LENGTH_SHORT).show();
////                                        etEmail.setError(getString(R.string.error_email_already_exist));
////                                        etEmail.requestFocus();
//                                    }
//                                }
//                            });
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
//                final Boolean[] judge=new Boolean[1];
//                db.collection(collectionPath)
//                        .whereEqualTo("contact", contactNumber)
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                //find ContactNumber
//                                if (task.isSuccessful()) {
//                                    Log.d("contactExist","contactExist");
//                                    etContactNumber.setError(getString(R.string.error_contact_already_exist));
//                                    etContactNumber.requestFocus();
//                                    //not find ContactNumber
//                                } else {
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
                final boolean[] emailExist = {false};
                //request server to verify
                db.collection(collectionPath)
                        .whereEqualTo("email", email)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                //find email
                                if (task.isSuccessful()) {
                                    Log.d("emailExist","emailExist");
                                    emailExist[0] = true;
                                    etEmail.setError(getString(R.string.error_email_already_exist));
                                    etEmail.requestFocus();
                                    //not find email
                                } else {
                                    Log.d("emailNotExist", "Error getting documents: ", task.getException());
                                }
                            }
                        });


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
                    etEmail.setError(getString(R.string.error_format_email_id));
                    etEmail.requestFocus();
                    return true;
                }
                if(emailExist[0])
                    return true;

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
            }

            private void setAllValues(){
                user = new User();
                user.setFirstName(this.firstName);
                user.setLastName(this.lastName);
                user.setEmail(this.email);
                user.setContactNumber(this.contactNumber);
                user.setAddress(this.address);
                user.setState(this.state);
                user.setCountry(this.country);
                user.setZipcode(this.zipcode);
                user.setPassword(MD5.getMd5(this.password));
            }

            private void insertData(String uid){
                Log.w("InIt","I'm here");
                db.collection(collectionPath).document(uid).set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("onSuccess", "DocumentSnapshot successfully written!");
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
        this.etFirstName = view.findViewById(R.id.editFirstName);
        this.etLastName = view.findViewById(R.id.editLastName);
        this.etEmail = view.findViewById(R.id.editEmail);
        this.etPassword = view.findViewById(R.id.editPassword);
        this.etConfirmPassword = view.findViewById(R.id.editConfirmPassword);
        this.etContactNumber = view.findViewById(R.id.editContactNumber);
        this.etAddress = view.findViewById(R.id.editAddress);
        this.etState = view.findViewById(R.id.editState);
        this.etCountry = view.findViewById(R.id.editCountry);
        this.etZipCode = view.findViewById(R.id.editZipcode);
    }

    private void startNextFragment(){
        Fragment fragment = new ForgotPasswordEnterOTPFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.formLayout, fragment);
        transaction.commit();
    }

    private void startLoginActivity(){
        Intent intentLogin = new Intent(getContext(), LoginActivity.class);
        intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentLogin);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        getActivity().finish();
    }




}
