package org.ieeeguc.ieeeguc.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* loading the logged in user and the token from the shared preferences */

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

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
        // TODO: Empty the sharedPreferences variable when it's tag is known
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
