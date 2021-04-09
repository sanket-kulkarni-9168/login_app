package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText emailEditText;
    TextInputEditText passwordEditText;
    Button loginBtn,cancelBtn;
    String emailValue,passwordValue;
    boolean emailError,passwordError;

    private static Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_REGEX = "^(([^<>()\\[\\]\\\\!?.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

    }

    private void findViews() {
        emailEditText = findViewById(R.id.login_email_edittext);
        passwordEditText = findViewById(R.id.login_password_edittext);
        cancelBtn = findViewById(R.id.cancel_btn);
        loginBtn = findViewById(R.id.login_btn);

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    emailValue = s.toString();
                    if (emailValidator(emailValue)) {
                        emailEditText.setError(null);
                        emailError = false;
                    } else {
                        emailEditText.setError(getString(R.string.invalid_email));
                        emailError = true;
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    passwordValue = s.toString();
                    if (passwordValidator(passwordValue)) {
                        passwordEditText.setError(null);
                        passwordError = false;
                    } else {
                        passwordEditText.setError(getString(R.string.invalid_password));
                        passwordError = true;
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTappingCancelBtn();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTappingLoginButton();
            }
        });

    }

    private void onTappingCancelBtn() {
        finish();
    }

    private void onTappingLoginButton() {
        if(emailEditText.getText().toString().equals(""))
            emailEditText.setError("Please enter email address");
        else if(passwordEditText.getText().toString().equals(""))
            passwordEditText.setError("Please enter password");
        else if(emailError&&passwordError)
            showAlert("Validation Failed",getString(R.string.invalid_email)+" and "+getString(R.string.invalid_password));
        else if(emailError)
            showAlert("Validation Failed",getString(R.string.invalid_email));
        else if(passwordError)
            showAlert("Validation Failed",getString(R.string.invalid_password));
        else
            showAlert("Validation Success","");
    }

    public void showAlert(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialogBuilder.create().show();
    }

    public boolean emailValidator(String email) {
        pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean passwordValidator(String password) {
        pattern = Pattern.compile(PASSWORD_REGEX);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}