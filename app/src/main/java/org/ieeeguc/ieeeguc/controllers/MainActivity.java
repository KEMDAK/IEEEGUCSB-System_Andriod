package org.ieeeguc.ieeeguc.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.fragments.user.UserIndex;
import org.ieeeguc.ieeeguc.fragments.user.UserShow;
import org.ieeeguc.ieeeguc.fragments.user.UserUpdate;
import org.ieeeguc.ieeeguc.models.User;
import org.json.JSONObject;

/**
 * The main screen that offers the different sections of the application.
 */
public class MainActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static User loggedInUser;
    public static String token;
    private static Context context;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting the context
        context = this;

        // Sets the class to be a listener to the navigation menu.
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        /** testing starts here **/
        /*
        // Fragment usage illustration
        // creating the fragment instance
        UserIndex userIndex = new UserIndex();

        // adding the needed variables to it
        User[] users = new User[1];
        users[0] = loggedInUser;
        Bundle bundle = new Bundle();
        bundle.putString("users", new Gson().toJson(users));
        userIndex.setArguments(bundle);

        // adding the fragment to the mainContainer
        getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, userIndex).commit();
*/
    }
    /**
     * This method is called when the user clicks the log out item from the slide menu.
     * It logs the user out , redirect him to the login screen, and clears the sharePreferences.
     */
    public static void logout() {

        HTTPResponse logoutHTTPResponse = new HTTPResponse() {
            @Override
            public void onSuccess(int statusCode, JSONObject body) {

            }

            @Override
            public void onFailure(int statusCode, JSONObject body) {

            }
        };

        // Logs out the logged in user.
        loggedInUser.logout(token, logoutHTTPResponse);

        // Getting a reference to the SharedPreferences.
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);

        // Clearing the SharedPreferences file.
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

        // Nullifying the token and the loggedInUser,after clearing the SharedPreferences.
        token = null;
        loggedInUser = null;

        // Navigating to the login screen.
        Intent logOutIntent = new Intent(context, LoginActivity.class);
        logOutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(logOutIntent);
        ((MainActivity) context).finish();
    }

    /**
     * This method creates and displays a snack bar with a specific message and a dismiss button
     * @param message The message of the snack bar
     */
    public static void createSnackBar(String message) {
        Snackbar.make(((MainActivity) context).findViewById(R.id.mainContainer), message,
                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).show();
    }

    /**
     * This methods is called when any item of the slide menu is clicked.
     * @param  {MenuItem} item [Selected item from the slide menu]
     * @return {boolean}
     */
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
        else if(id==R.id.user_show_item) {

            //When the Show user navigation item is clicked show user info
            UserShow userShow = new UserShow();

            // adding the variables to the fragment

            //  Bundle bundle = new Bundle();
            //  bundle.putString("user_id", new Gson().toJson(loggedInUser.getId()));
            //  bundle.putString("token", new Gson().toJson(token));
            // userShow.setArguments(bundle);

            // adding the fragment to the mainContainer

            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, userShow).commit();
        }

        // Closes the drawer menu after the item is selected.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
