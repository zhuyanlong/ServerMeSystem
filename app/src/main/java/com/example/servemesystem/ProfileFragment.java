package com.example.servemesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private LinearLayout firstLayout, lastLayout, emailLayout, passwordLayout, contactLayout, addressLayout;
    public ProfileFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.drawer_fragment_profile, container, false);
        user = SaveSharedPreference.getUserObject(getContext());
        this.setFirstNameField();
        this.setLastNameField();
        this.setEmailIDField();
//        this.setPasswordChangeField();
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
                Intent intentLogin = new Intent(getContext(), LoginActivity.class);
                startActivity(intentLogin);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                getActivity().finish();            }
        });

    }

    private void setFirstNameField() {
        TextView  textChange;
        textChange = view.findViewById(R.id.textFirstName);
        textChange.setText(String.format("%s %s",getString(R.string.settings_first_name),user.getFirstName()));
        firstLayout =  view.findViewById(R.id.layoutFirstName);
        firstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startFragment(new ProfileChangeFirstNameFragment());            }
        });
    }

    private void setLastNameField() {
        TextView textChange = view.findViewById(R.id.textLastName);
        textChange.setText(String.format("%s %s",getString(R.string.settings_last_name),user.getLastName()));
        view.findViewById(R.id.layoutLastName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new ProfileChangeLastNameFragment());
            }
        });

    }
//
    private void setEmailIDField() {
        TextView textChange = view.findViewById(R.id.textEmail);
        textChange.setText(String.format("%s %s",getString(R.string.settings_email),user.getEmail()));
//        view.findViewById(R.id.textChangeEmail).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startFragment(new ProfileChangeEmailFragment());
//            }
//        });
    }
//
//    private void setPasswordChangeField() {
//
//        view.findViewById(R.id.textChangePassword).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startFragment(new ProfileChangePasswordFragment());
//            }
//        });
//    }
//
    private void setContactField() {
        TextView textChange = view.findViewById(R.id.textContactNumber);
        textChange.setText(String.format("%s %s",getString(R.string.settings_contact),user.getContactNumber()));
        contactLayout = view.findViewById(R.id.layoutContact);
        contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new ProfileChangeContactNumberFragment());
            }
        });
    }
//
    private void setAddressField() {
        TextView textChange = view.findViewById(R.id.textAddress);
        textChange.setText(String.format("%s %s %s %s %s",getString(R.string.settings_address),user.getAddress(), user.getState(), user.getCountry(), user.getZipcode()));
        addressLayout = view.findViewById(R.id.layoutAddress);
        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new ProfileChangeAddressFragment());
            }
        });
    }

    private void startFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
