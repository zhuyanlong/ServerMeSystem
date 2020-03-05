package com.example.servemesystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.servemesystem.helper.SaveSharedPreference;
import com.example.servemesystem.pojo.User;

public class ProfileFragment extends Fragment {
    private View view;
    private User user;

    public ProfileFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.drawer_fragment_profile, container, false);
        user = SaveSharedPreference.getUserObject(getContext());
        this.setFirstNameField();
        this.setLastNameField();
        this.setEmailIDField();
        this.setPasswordChangeField();
        this.setContactField();
        this.setAddressField();
        this.setBackButton();
        return view;
    }
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setBackButton() {
        Button buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

    }

    private void setFirstNameField() {
        TextView  textChange;
        textChange = view.findViewById(R.id.textChangeFirstName);
        textChange.setText(user.getFirstName());
        textChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new ProfileChangeFirstNameFragment());
            }
        });
    }

    private void setLastNameField() {
        TextView textChange = view.findViewById(R.id.textChangeLastName);
        textChange.setText(user.getLastName());
        textChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new ProfileChangeLastNameFragment());
            }
        });

    }

    private void setEmailIDField() {
        TextView textChange = view.findViewById(R.id.textChangeEmail);
        textChange.setText(user.getEmail());
        textChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new ProfileChangeEmailFragment());
            }
        });
    }

    private void setPasswordChangeField() {
        TextView textChange = view.findViewById(R.id.textChangePassword);
        textChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new ProfileChangePasswordFragment());
            }
        });
    }

    private void setContactField() {
        TextView textChange = view.findViewById(R.id.textChangeContactNumber);
        textChange.setText(user.getContactNumber());
        textChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new ProfileChangeContactNumberFragment());
            }
        });
    }

    private void setAddressField() {
        TextView textChange = view.findViewById(R.id.textChangeAddress);
        textChange.setText(user.getAddress());
        textChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new ProfileChangeAddressFragment());
            }
        });
    }

    private void startFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutForm, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
