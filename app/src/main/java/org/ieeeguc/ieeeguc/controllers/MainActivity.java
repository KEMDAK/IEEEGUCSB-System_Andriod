package org.ieeeguc.ieeeguc.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.models.User;
import org.json.JSONObject;


public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    public static User loggedInUser;
    public static String token;
    public NavigationView navigationView;
    private SharedPreferences spToken;
    private SharedPreferences spLoggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* loading the logged in user and the token from the shared preferences */
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoibG9naW4tdG9rZW4iLCJ1c2VyQWdlbnQiOiJXZWIiLCJ1c2VySWQiOjEsImV4cCI6MTQ5Mzc0ODUyNjc3NCwiaWF0IjoxNDg1OTcyNTI2fQ.fIAn_gbyi_AUf6V54DOvahzwxSgtk3Hyr8UHXxx_aas";
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        loggedInUser = new User();

    }

    private void logout() {

        HTTPResponse logoutHTTPResponse = new HTTPResponse() {
            @Override
            public void onSuccess(int statusCode, JSONObject body) {

                // Fire an intent to the login activity.
                Intent logOutIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logOutIntent);

            }

            @Override
            public void onFailure(int statusCode, JSONObject body) {

                if (statusCode == -1) {
                    Toast.makeText(getApplicationContext(), R.string.failed_logout_internet_problem, Toast.LENGTH_LONG).show();
                } else if (statusCode == -500) {
                    Toast.makeText(getApplicationContext(), R.string.failed_logout_server_error, Toast.LENGTH_LONG).show();
                } else {

                    // Fire an intent to the login activity.
                    Intent logOutIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(logOutIntent);

                }

            }
        };

        loggedInUser.logout(token, logoutHTTPResponse);
        // Empty the sharedPreferences variable when the user is logged out.
        token = "";
        SharedPreferences.Editor editorToken = spToken.edit();
        SharedPreferences.Editor editorLoggedInUser = spLoggedInUser.edit();
        editorToken.putString("Token",token);
        editorLoggedInUser.putString("LoggedInUser","");
        editorToken.commit();
        editorLoggedInUser.commit();

        Log.i(token,spToken.getString("Token","000"));


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home_item) {
            // Handle the home action
        } else if (id == R.id.log_out_item) {
            // When the logout navigation item is clicked, log out .
            logout();
        }

        // Closes the drawer menu after the item is selected.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
