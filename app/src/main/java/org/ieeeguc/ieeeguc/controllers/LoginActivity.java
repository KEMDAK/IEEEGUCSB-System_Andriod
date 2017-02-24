package org.ieeeguc.ieeeguc.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;
import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.models.User;
import org.ieeeguc.ieeeguc.models.User.Gender;
import org.ieeeguc.ieeeguc.models.User.Type;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.ieeeguc.ieeeguc.models.User.login;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText email ;
    private EditText password ;
    private Button send ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email) ;
        password = (EditText) findViewById(R.id.password) ;
        send = (Button) findViewById(R.id.email_sign_in_button) ;

        send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin() ;
            }

        });
    }

    /**
     * this method is called when the user click the button and the app attempt to log in
     */
    public void attemptLogin() {
        String emailText = email.getText().toString() ;
        String passwordText = password.getText().toString() ;

        if (emailText.length()!= 0 && passwordText.length()!= 0) {
            login(emailText, passwordText, new HTTPResponse() {

                @Override
                public void onSuccess(int statusCode, JSONObject body) {

                    try {
                        String token = body.getString("token");
                        SharedPreferences Sp = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
                        SharedPreferences.Editor E = Sp.edit();
                        E.putString("token", token);


                        MainActivity.token = token;

                        JSONObject jsonUser = body.getJSONObject("user");
                        int id = jsonUser.getInt("id");
                        String stringType = jsonUser.getString("type");
                        Type type;
                        switch (stringType) {
                            case "Admin":
                                type = Type.ADMIN;
                                break;
                            case "Upper Board":
                                type = Type.UPPER_BOARD;
                                break;
                            case "High Board":
                                type = Type.HIGH_BOARD;
                                break;
                            default:
                                type = Type.MEMBER;
                                break;
                        }
                        String FN = jsonUser.getString("first_name");
                        String LN = jsonUser.getString("last_name");
                        String stringGender = jsonUser.getString("gender");
                        Gender gender;
                        switch (stringGender) {
                            case "male":
                                gender = Gender.MALE;
                                break;
                            default:
                                gender = Gender.FEMALE;
                        }
                        String email = jsonUser.getString("email");
                        String PN = jsonUser.getString("phone_number");
                        String BDS = jsonUser.getString("birthdate");
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd");
                        Date BD = dateFormatter.parse(BDS.substring(0, 10));
                        String IEEE_membership_ID = jsonUser.getString("IEEE_membership_ID");
                        JSONObject settings = jsonUser.getJSONObject("settings");
                        String committeeName;
                        int committeeID;
                        if (jsonUser.has("committee")) {
                            JSONObject committee = jsonUser.getJSONObject("committee");
                            committeeName = committee.getString("committeeName");
                            committeeID = committee.getInt("committeeID");
                        } else {
                            committeeName = null;
                            committeeID = 0;
                        }

                        User user = new User(id, type, FN, LN, gender, email, BD, IEEE_membership_ID, committeeID, committeeName, PN, settings);
                        MainActivity.loggedInUser = user;
                        E.putString("user", new Gson().toJson(user));
                        E.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        Snackbar.make(findViewById(R.id.email_sign_in_button), getString(R.string.error_server_down),
                                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, JSONObject body) {

                    if(statusCode == 401) {
                        Snackbar.make(findViewById(R.id.email_sign_in_button), getString(R.string.error_incorrect_credentials),
                                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    }

                    else if(statusCode == 500) {
                        Snackbar.make(findViewById(R.id.email_sign_in_button), getString(R.string.error_server_down),
                                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    }
                    else if(statusCode == -1) {
                        Snackbar.make(findViewById(R.id.email_sign_in_button), getString(R.string.error_connection),
                                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    }
                }
            });
        } else {

            if (emailText.length() != 0) {
                Snackbar.make(findViewById(R.id.email_sign_in_button), getString(R.string.error_invalid_email),
                        Snackbar.LENGTH_INDEFINITE).setAction("Ok", new OnClickListener() {
                    @Override
                    public void onClick(View view) {}}).show();
            }
            else if(passwordText.length() != 0) {
                Snackbar.make(findViewById(R.id.email_sign_in_button), getString(R.string.error_empty_password),
                        Snackbar.LENGTH_INDEFINITE).setAction("Ok", new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }
            else{
                Snackbar.make(findViewById(R.id.email_sign_in_button), getString(R.string.error_empty_credentials),
                        Snackbar.LENGTH_INDEFINITE).setAction("Ok", new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }
        }
    }
}


