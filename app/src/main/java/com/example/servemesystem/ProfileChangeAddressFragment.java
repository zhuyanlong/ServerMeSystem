package com.example.servemesystem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.servemesystem.helper.SaveSharedPreference;
import com.example.servemesystem.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class ProfileChangeAddressFragment extends Fragment {
    private EditText editAddress, editCountry, editState, editZip;
    private View view;
    private User user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String collectionPath="users";

    public ProfileChangeAddressFragment() {
        // Required empty public constructor
    }


    public static ProfileChangeAddressFragment newInstance(String param1, String param2) {
        ProfileChangeAddressFragment fragment = new ProfileChangeAddressFragment();

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
        view = inflater.inflate(R.layout.fragment_profile_change_address, container, false);
        user = SaveSharedPreference.getUserObject(getContext());
        this.setEditTexts();
        this.setAllButton();
        return view;
    }

    private void setEditTexts() {
        this.editAddress = view.findViewById(R.id.editAddress);
        this.editState = view.findViewById(R.id.editState);
        this.editCountry = view.findViewById(R.id.editCountry);
        this.editZip = view.findViewById(R.id.editZipcode);

    }

    private void setAllButton() {
        view.findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();

            }
        });

        view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            private String address, state, country, zip;

            @Override
            public void onClick(View view) {
                this.getAddress();
                if(this.isFirstNameValid()){
                    user.setAddress(address);
                    user.setState(state);
                    user.setCountry(country);
                    user.setZipcode(zip);
                    this.submitValueToFirebase();
                }
            }

            private void submitValueToFirebase() {
//                    mAuth.getCurrentUser()
//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if(task.isSuccessful()){
//                                        insertUserDetailsToFirebase();
//                                    }
//                                    else{
//                                        Toast.makeText(getContext(), getString(R.string.error_generic), Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                            });
                insertUserDetailsToFirebase();

            }

            private boolean isFirstNameValid() {

                if(address.isEmpty()){
                    ProfileChangeAddressFragment.this.editAddress.setError(getString(R.string.error_required_field_address));
                    ProfileChangeAddressFragment.this.editAddress.requestFocus();
                    return false;
                }
                if(state.isEmpty()){
                    ProfileChangeAddressFragment.this.editState.setError(getString(R.string.error_required_field_state));
                    ProfileChangeAddressFragment.this.editState.requestFocus();
                    return false;
                }
                if(country.isEmpty()){
                    ProfileChangeAddressFragment.this.editCountry.setError(getString(R.string.error_required_field_country));
                    ProfileChangeAddressFragment.this.editCountry.requestFocus();
                    return false;
                }
                if(zip.isEmpty()){
                    ProfileChangeAddressFragment.this.editZip.setError(getString(R.string.error_required_field_zip_code));
                    ProfileChangeAddressFragment.this.editZip.requestFocus();
                    return false;
                }
                return true;
            }

            private void getAddress() {
                address = ProfileChangeAddressFragment.this.editAddress.getText().toString();
                state = ProfileChangeAddressFragment.this.editState.getText().toString();
                country = ProfileChangeAddressFragment.this.editCountry.getText().toString();
                zip = ProfileChangeAddressFragment.this.editZip.getText().toString();
            }
        });
    }

    private void insertUserDetailsToFirebase(){
        db.collection(collectionPath).document(mAuth.getCurrentUser().getUid()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getUserInfoFromServer(user.getEmail());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), getString(R.string.error_generic), Toast.LENGTH_LONG).show();

                    }
                });
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
                            Toast.makeText(getContext(), getString(R.string.error_generic), Toast.LENGTH_LONG).show();
                        }

                        if (users != null && users.size() == 1)
                        {

                            User temp = users.get(0);
                            Log.i("ServemeSystem User Info", temp.toString());
                            user.setAddress(temp.getAddress());
                            user.setState(temp.getState());
                            user.setCountry(temp.getCountry());
                            user.setZipcode(temp.getZipcode());
                            SaveSharedPreference.removeUserObject(getContext());
                            SaveSharedPreference.setUserObject(getContext(), user);
                            getFragmentManager().popBackStack();

                        }
                    }
                });
    }
}
