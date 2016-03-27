package com.epicodus.myrestaurants.ui;

import android.content.Intent;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.passwordLoginButton) Button mPasswordLoginButton;
    @Bind(R.id.emailEditText) EditText mEmailEditText;
    @Bind(R.id.passwordEditText) EditText mPasswordEditText;
    @Bind(R.id.registerButton) Button mRegisterButton;

    private Firebase mFirebaseRef;
    private Firebase.AuthResultHandler mAuthResultHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mFirebaseRef = MyRestaurantsApplication.getAppInstance().getFirebaseRef();

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
            //register user
        }
    }

    public void loginWithPassword() {
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        mFirebaseRef.authWithPassword(email, password, mAuthResultHandler);
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
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.d("Authentication error", firebaseError.toString());
            }
        };
    }
}
