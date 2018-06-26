package com.job.jounalapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.job.jounalapp.R;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignIn";
    @BindView(R.id.signin_button)
    Button signinButton;

    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 123;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
      /*  this.requestWindowFeature(Window.FEATURE_NO_TITLE);*/
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        //init
        //firebase
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        shouldSignIn();
    }

    private void shouldSignIn() {
        if (mAuth.getCurrentUser() != null) {
            // already signed in
            Toast.makeText(this, "Signed in as " + mAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
            sendToJournal();
        } else {
            //googleAuthIntent();
        }
    }

    private void sendToJournal() {
        Intent intent = new Intent(this,JournalActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.signin_button)
    public void onViewClicked() {
        googleAuthIntent();
    }

    private void googleAuthIntent(){

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.mipmap.ic_launcher)
                        .setTheme(R.style.AppTheme)
                        .setAvailableProviders(Collections.singletonList(
                                new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {

                sendToJournal();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                showSnackbar(R.string.unknown_error);
                Log.e(TAG, "Sign-in error: ", response.getError());

                //googleAuthIntent();
            }
        }
    }

    private void showSnackbar(@StringRes int string){
        Snackbar.make(findViewById(android.R.id.content), string,Snackbar.LENGTH_LONG);
    }
}
