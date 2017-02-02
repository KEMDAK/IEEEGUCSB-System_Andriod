package org.ieeeguc.ieeeguc.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        // Sets the class to be a listener.
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void logout() {



        HTTPResponse logoutHTTPResponse = new HTTPResponse() {
            @Override
            public void onSuccess(int statusCode, JSONObject body) {

            }

            @Override
            public void onFailure(int statusCode, JSONObject body) {

                if (statusCode == -1) {

                    Toast.makeText(getApplicationContext(),
                            R.string.failed_logout_internet_problem
                            , Toast.LENGTH_LONG).show();

                } else if (statusCode == 500) {

                    Toast.makeText(getApplicationContext(),
                            R.string.failed_logout_server_error,
                            Toast.LENGTH_LONG).show();
                }

            }
        };

        // Logs out the logged in user.
        loggedInUser.logout(token, logoutHTTPResponse);


        // Getting a reference to the SharedPreferences.
        SharedPreferences sp = getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);

        // Clearing the SharedPreferences file.
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

        // Nullifying the token and the loggedInUser,after clearing the SharedPreferences.
        token = null;
        loggedInUser = null;

        // Navigating to the login screen.
        Intent logOutIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(logOutIntent);


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home_item) {

            // Handle the home action

        } else if (id == R.id.log_out_item) {

            // When the logout navigation item is clicked logs out .
            logout();
        }

        // Closes the drawer menu after the item is selected.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
