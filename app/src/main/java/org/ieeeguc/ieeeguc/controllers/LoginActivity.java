package org.ieeeguc.ieeeguc.controllers;

import android.os.Bundle;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void logout(){

        HTTPResponse logoutHTTPResponse = new HTTPResponse() {
            @Override
            public void onSuccess(int statusCode, JSONObject body) {

                    // TODO: Fire an intent to the login activity,and finishes the current activity.)
            }

            @Override
            public void onFailure(int statusCode, JSONObject body) {

                if(statusCode == -1){
                    Toast.makeText(getApplicationContext(), R.string.failed_logout_internet_problem, Toast.LENGTH_LONG).show();
                }

                else if(statusCode == -500){
                    Toast.makeText(getApplicationContext(), R.string.failed_logout_server_error, Toast.LENGTH_LONG).show();
                }

                else if (statusCode >= 300){

                    // TODO: Unknown error until now , should be handled gracefully.
                }

                else if(statusCode >= 400){

                    // TODO: Ask kareem if this will be for debugging purpose only or not.
                }

                else{

                    Toast.makeText(getApplicationContext(), R.string.failed_logout_server_error, Toast.LENGTH_LONG).show();
                }

            }
        };

        currentUser.logout(token,logoutHTTPResponse);

        // TODO: Empty the sharedPreferences variable when it's tag is known.
    }

    /**
     * Attempts to sign in to the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

    }
}

