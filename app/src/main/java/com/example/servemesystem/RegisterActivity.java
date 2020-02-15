package com.example.servemesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword, etContactNumber;
    private EditText etAddress, etState, etCountry, etZipCode;
    private Button buttonBack, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setAllEditTexts();
        this.setAllButtons();
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

            @Override
            public void onClick(View view) {
                this.getAllValuesFromEditTexts();
                if (this.isFormValuesValid()) {
                    //send request to Server
                    Toast.makeText(
                            RegisterActivity.this,
                            getString(R.string.message_registration_success),
                            Toast.LENGTH_LONG).show();
                    startLoginActivity();
                }
            }

            private boolean isFormValuesValid() {
                if (this.isAnyFieldEmpty() || this.isEmailExist() || this.isPasswordFormatWrong()
                        || this.isPasswordsNotMatching() || this.isContactNumberFormatNotValid() ||
                        this.isContactNumberExist() || this.isAddressValueNotValid()) {
                    return false;
                }
                return true;
            }

            private boolean isContactNumberExist() {
                //request server to check if contact number exist
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
                this.confirmPassword = etConfirmPassword.getText().toString();
                this.contactNumber = etContactNumber.getText().toString();
                this.address = etAddress.getText().toString();
                this.state = etState.getText().toString();
                this.country = etCountry.getText().toString();
                this.zipcode = etZipCode.getText().toString();

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
