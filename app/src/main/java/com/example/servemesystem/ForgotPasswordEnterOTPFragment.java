package com.example.servemesystem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.servemesystem.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;



public class ForgotPasswordEnterOTPFragment extends Fragment {
    public static String TAG = "ServeMeSystem :: ForgotPasswordEnterOTPFrag";

    private EditText editOtp;
    private Button buttonBack, buttonSubmit;
    private String mVerificationId = null;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private User user;



    public ForgotPasswordEnterOTPFragment() {
        // Required empty public constructor
    }


    public static ForgotPasswordEnterOTPFragment newInstance(String param1, String param2) {
        ForgotPasswordEnterOTPFragment fragment = new ForgotPasswordEnterOTPFragment();
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
        View view = inflater.inflate(R.layout.fragment_forgot_password_enter_ot, container, false);
//        this.getUserDetails();
//        this.setEditText(view);
//        this.setAllButtons(view);
//        this.sendVerificationCode(user.getContactNumber());
        return view;
    }

//    private void getUserDetails() {
//        user = (User)getArguments().getSerializable("user");
//    }
//
//    private void sendVerificationCode(String mobile) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                "+1" + mobile,
//                60,
//                TimeUnit.SECONDS,
//                TaskExecutors.MAIN_THREAD,
//                mCallbacks);
//    }
//
//    private void setEditText(View view) {
//        this.editOtp = view.findViewById(R.id.editOtp);
//    }
//
//    private void setAllButtons(View view) {
//        this.defineAllButtons(view);
//        this.setAllButtonListener(view);
//    }
//
//    private void defineAllButtons(View view) {
//        this.buttonBack = view.findViewById(R.id.buttonBack);
//        this.buttonSubmit = view.findViewById(R.id.buttonSubmit);
//    }
//
//    private void setAllButtonListener(View view) {
//        this.buttonBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().popBackStack();
//            }
//        });
//
//        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
//            private String otp;
//            @Override
//            public void onClick(View view) {
//                otp = editOtp.getText().toString();
//                if(!otp.isEmpty()) {
//                    verifyVerificationCode(otp);                }
//                else{
//                    editOtp.setError(getString(R.string.error_required_field_otp));
//                    editOtp.requestFocus();
//                }
//            }
//
//
//        });
//    }
//
//    private void startFragment(Fragment fragment){
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("user", user);
//        fragment.setArguments(bundle);
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.layoutForgotPassword, fragment);
//        transaction.commit();
//    }
//
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//            //Getting the code sent by SMS
//            String code = phoneAuthCredential.getSmsCode();
//
//            //sometime the code is not detected automatically
//            //in this case the code will be null
//            //so user has to manually enter the code
//            if (code != null) {
//                editOtp.setText(code);
//                //verifying the code
//                verifyVerificationCode(code);
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            mVerificationId = s;
//            mResendToken = forceResendingToken;
//        }
//    };
//
//    private void verifyVerificationCode(String otp) {
//        //creating the credential
//        if(mVerificationId != null) {
//            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
//
//            //signing the user
//            signInWithPhoneAuthCredential(credential);
//        }
//        else
//            editOtp.setError(getString(R.string.error_invalid_otp));
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            //verification successful we will start the profile activity
//                            startFragment(new ForgotPasswordNewPasswordFragment());
//
//                        } else {
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                editOtp.setError(getString(R.string.error_invalid_otp));
//                            }
//                            else
//                                editOtp.setError(getString(R.string.error_generic));
//                                Log.e(TAG, task.getException().toString());                        }
//
//                }
//                });
//    }

}