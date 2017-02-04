package org.ieeeguc.ieeeguc.controllers;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.models.Gender;
import org.ieeeguc.ieeeguc.models.Type;
import org.ieeeguc.ieeeguc.models.User;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        final String[] message = new String[1];
        send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailText = email.getText().toString() ;
                String passwordText = password.getText().toString() ;


                if (emailText.length()!= 0 && passwordText.length()!= 0 ) {
                    login(emailText, passwordText, new HTTPResponse() {

                        @Override
                        public void onSuccess(int statusCode, JSONObject body)  {
                            Log.i("2","it is clicked");
                            if (statusCode == 200) {

                                Log.i("2","it is ok i think");


                                try {
                                    String token = body.getString("token");
                                    SharedPreferences Sp = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
                                    SharedPreferences.Editor E = Sp.edit();
                                    E.putString("token", token);
                                    E.commit();

                                    MainActivity.token = token ;

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);

                                    JSONObject USer = body.getJSONObject("user") ;
                                    int id = USer.getInt("id") ;
                                    String TYPE = USer.getString("type") ;
                                    Type type = null;
                                    String FN = USer.getString("first_name") ;
                                    String LN =USer.getString("last_name") ;
                                    String  gender = USer.getString("gender") ;
                                    Gender GENDER = null  ;
                                    String email = USer.getString("email") ;
                                    String PN = USer.getString("phone_number") ;
                                    String BDS = USer.getString("birthdate") ;
                                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-mm");
                                    Date BD = dateFormatter.parse(BDS.substring(0,10));
                                    String IEEE_membership_ID = USer.getString("IEEE_membership_ID:") ;
                                    JSONObject settings = USer.getJSONObject("settings") ;
                                    JSONObject committee =USer.getJSONObject("committee") ;
                                    String committeeName =committee.getString("committeeName") ;
                                    int committeeID = committee.getInt("committeeID") ;
                                    User user = new User(id,type,FN,LN,GENDER,email,BD ,IEEE_membership_ID,committeeID,committeeName,PN,settings) ;
                                    MainActivity.loggedInUser = user ;

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            }
                        }

                        @Override
                        public void onFailure(int statusCode, JSONObject body) {
                            Log.i("1","failed");


                            if(statusCode == 401)
                            {
                                Log.i("1","your faild ");
                                Snackbar.make(findViewById(R.id.email_sign_in_button), R.string.error_401,
                                        Snackbar.LENGTH_INDEFINITE).setAction(R.string.Ok, new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                        .show();
                            }

                            if(statusCode == 500){

                                Snackbar.make(findViewById(R.id.email_sign_in_button), R.string.error_500,
                                        Snackbar.LENGTH_INDEFINITE).setAction(R.string.Ok, new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                        .show();

                            }
                        }
                    });
                } else {
                    if (emailText.length()!= 0) {

                        Snackbar.make(findViewById(R.id.email_sign_in_button), R.string.PasswordIsEmpty,
                                Snackbar.LENGTH_INDEFINITE).setAction(R.string.Ok, new OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                                .show();
                        Log.i("2","your password is null");


                    } else {
                        if(passwordText.length()!= 0)
                        {
                            Snackbar.make(findViewById(R.id.email_sign_in_button), R.string.EmailIsEmpty,
                                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.Ok, new OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                                    .show();
                            Log.i("2","your email is null");
                        }

                        else{
                            Snackbar.make(findViewById(R.id.email_sign_in_button), R.string.PasswordAndEmailAreEmpty,
                                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.Ok, new OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                                    .show();
                            Log.i("2","your email and pass is null");
                        }
                    }
                }
            }

        });
            }


}

