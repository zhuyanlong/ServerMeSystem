package com.example.servemesystem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPasswordEnterEmailFragment extends Fragment {
    private Button buttonBack, buttonSubmit;
    private EditText editEmail;
    private FirebaseAuth firebaseAuth;

    public ForgotPasswordEnterEmailFragment() {
        // Required empty public constructor
    }


    public static ForgotPasswordEnterEmailFragment newInstance(String param1, String param2) {
        ForgotPasswordEnterEmailFragment fragment = new ForgotPasswordEnterEmailFragment();
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
        View view =  inflater.inflate(R.layout.fragment_forgot_password_enter_email, container, false);
        this.setAllEditTexts(view);
        this.setAllButtons(view);
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

    private void setAllEditTexts(View view) {
        this.editEmail = view.findViewById(R.id.editEmail);
    }

    private void setAllButtons(View  view) {
        this.defineAllButtons(view);
        this.setAllButtonListeners();
    }

    private void defineAllButtons(View view) {
        this.buttonBack = view.findViewById(R.id.buttonBack);
        this.buttonSubmit = view.findViewById(R.id.buttonSubmit);
    }

    private void setAllButtonListeners() {
        this.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity();
            }
        });

        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {

            private String emailId;
            @Override
            public void onClick(View view) {
                emailId = editEmail.getText().toString();
                if(this.isEmailIdValid()){
                    this.sendOtpRequestToServer();
//                    this.startForgotPasswordEnterOtpFragment();

                }
            }

            private boolean isEmailIdValid() {
                if(emailId.isEmpty()){
                    editEmail.setError(getString(R.string.error_required_field_email));
                    editEmail.requestFocus();
                    return false;
                }
                if( !(Patterns.EMAIL_ADDRESS.matcher(emailId).matches())){
                    editEmail.setError(getString(R.string.error_format_email_id));
                    editEmail.requestFocus();
                    return false;
                }
                return true;
            }

            private void sendOtpRequestToServer() {
                // make server do this
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.sendPasswordResetEmail(emailId)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Sent to you email.", Toast.LENGTH_LONG).show();
                                    startLoginActivity();
                                } else {
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

            private void startForgotPasswordEnterOtpFragment() {
                Fragment fragment = new ForgotPasswordEnterOTPFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.layoutForgotPassword, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
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


}