package com.example.servemesystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.servemesystem.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;


public class RegisterEnterOTPFragment extends Fragment {
    public static String TAG = "ServeMeSystem :: RegisterEnterOTPFrag";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String collectionPath="users";

    private EditText editOtp;
    private Button buttonBack, buttonSubmit;
    private String mVerificationId = null;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private User user;

    public RegisterEnterOTPFragment() {
        // Required empty public constructor
    }


    public static RegisterEnterOTPFragment newInstance(String param1, String param2) {
        RegisterEnterOTPFragment fragment = new RegisterEnterOTPFragment();
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
        View view = inflater.inflate(R.layout.fragment_register_enter_o_t_p, container, false);
        this.getUserDetails();
        this.setEditText(view);
        this.setAllButtons(view);
        this.sendVerificationCode(user.getContactNumber());
        this.setAndroidBackKeyButton(view);
        return view;
    }

    private void setAndroidBackKeyButton(View view) {
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

    private void getUserDetails() {
        user = (User)getArguments().getSerializable("user");
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+1" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private void setEditText(View view) {
        this.editOtp = view.findViewById(R.id.editOtp);
    }

    private void setAllButtons(View view) {
        this.defineAllButtons(view);
        this.setAllButtonListener(view);
    }

    private void defineAllButtons(View view) {
        this.buttonBack = view.findViewById(R.id.buttonBack);
        this.buttonSubmit = view.findViewById(R.id.buttonSubmit);
    }

    private void setAllButtonListener(View view) {
        this.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            private String otp;
            @Override
            public void onClick(View view) {
                otp = editOtp.getText().toString();
                if(!otp.isEmpty()) {
                    verifyVerificationCode(otp);                }
                else{
                    editOtp.setError(getString(R.string.error_required_field_otp));
                    editOtp.requestFocus();
                }
            }


        });
    }

    private void startLoginActivity(){
        Intent intentLogin = new Intent(getContext(), LoginActivity.class);
        intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentLogin);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        getActivity().finish();
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                editOtp.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            mResendToken = forceResendingToken;
        }
    };

    private void verifyVerificationCode(String otp) {
        //creating the credential
        if(mVerificationId != null) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);

            //signing the user
            signInWithPhoneAuthCredential(credential);
        }
        else
            editOtp.setError(getString(R.string.error_invalid_otp));
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            registerUserWithFirebase();
                            startLoginActivity();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                editOtp.setError(getString(R.string.error_invalid_otp));
                            }
                            else
                                editOtp.setError(getString(R.string.error_generic));
                            Log.e(TAG, task.getException().toString());
                        }
                    }
                });
    }

    private void registerUserWithFirebase(){
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            insertUserDetailsToFirebase();
                        }
                        else{
                            Toast.makeText(getContext(), getString(R.string.error_generic), Toast.LENGTH_LONG).show();
                            Log.e(TAG, task.getException().toString());
                        }
                    }
                });
    }

    private void insertUserDetailsToFirebase(){
        db.collection(collectionPath).document(mAuth.getCurrentUser().getUid()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG,"Error adding document", e);
                    }
                });
    }
}

