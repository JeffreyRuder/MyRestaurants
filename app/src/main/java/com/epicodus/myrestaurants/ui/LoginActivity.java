package com.epicodus.myrestaurants.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epicodus.myrestaurants.MyRestaurantsApplication;
import com.epicodus.myrestaurants.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.passwordLoginButton) Button mPasswordLoginButton;
    @Bind(R.id.emailEditText) EditText mEmailEditText;
    @Bind(R.id.passwordEditText) EditText mPasswordEditText;
    @Bind(R.id.registerButton) Button mRegisterButton;

    private Firebase mFirebaseRef;
    private Firebase.AuthResultHandler mAuthResultHandler;

    private ProgressDialog mAuthProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mFirebaseRef = MyRestaurantsApplication.getAppInstance().getFirebaseRef();

        initializeAuthProgressDialog();
        initializeAuthResultHandler();

        mPasswordLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mPasswordLoginButton) {
            loginWithPassword();
        }
        if (v == mRegisterButton) {
            registerNewUser();
        }
    }

    public void loginWithPassword() {
        mAuthProgressDialog.show();
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        mFirebaseRef.authWithPassword(email, password, mAuthResultHandler);
    }

    public void registerNewUser() {
        mAuthProgressDialog.show();
        final String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        mFirebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {

            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                mFirebaseRef.authWithPassword(email, password, mAuthResultHandler);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                mAuthProgressDialog.hide();
                showErrorDialog(firebaseError.toString());
            }
        });
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void initializeAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
    }


    public void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
            | Intent.FLAG_ACTIVITY_CLEAR_TASK
            | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    private void initializeAuthResultHandler() {
        mAuthResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                goToMainActivity();
                mAuthProgressDialog.hide();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                mAuthProgressDialog.hide();
                showErrorDialog(firebaseError.toString());
            }
        };
    }
}
