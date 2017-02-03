package org.ieeeguc.ieeeguc.controllers;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static org.ieeeguc.ieeeguc.models.User.login;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    AutoCompleteTextView email ;
    EditText password ;
    Button send ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (AutoCompleteTextView) findViewById(R.id.email) ;
        password = (EditText) findViewById(R.id.password) ;
        send = (Button) findViewById(R.id.email_sign_in_button) ;
        send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailText = email.getText().toString() ;
                String passwordText = password.getText().toString() ;

                if (emailText != null && passwordText != null) {
                    login(emailText, passwordText, new HTTPResponse() {

                        @Override
                        public void onSuccess(int statusCode, JSONObject body)  {
                            Log.i("2","");
                            if (statusCode == 200) {


                                try {
                                    String token = body.getString("token");
                                    SharedPreferences Sp = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
                                    SharedPreferences.Editor E = Sp.edit();
                                    E.putString("token", token);
                                    E.commit();

                                    MainActivity.token = token ;


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }

                        @Override
                        public void onFailure(int statusCode, JSONObject body) {
                            Log.i("1","");

                            if(statusCode == 401)
                            {
                                Toast.makeText(getApplicationContext(), "Your password or email are incorrect ", Toast.LENGTH_LONG).show();
                            }
                            if(statusCode == 500){
                                Toast.makeText(getApplicationContext(), "IEEE is down", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    if (emailText != null) {
                        Toast.makeText(getApplicationContext(), "You forget to write your password", Toast.LENGTH_LONG).show();
                        Log.i("3","");
                    } else {
                        if(passwordText != null)
                        { Toast.makeText(getApplicationContext(), "You forget to write your email", Toast.LENGTH_LONG).show();
                        Log.i("4","");}

                        else{
                            Log.i("5","");
                            Toast.makeText(getApplicationContext(), "You forget to write your email and password", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

    });


}
}

