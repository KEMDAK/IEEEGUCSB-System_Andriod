package org.ieeeguc.ieeeguc.controllers;

import android.app.Activity;
import android.os.Bundle;

import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.models.User;

public class MainActivity extends Activity {

    public static User loggedInUser;
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* loading the logged in user and the token from the shared preferences */
    }
}
