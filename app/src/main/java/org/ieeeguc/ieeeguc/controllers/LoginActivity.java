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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.models.User;
import org.ieeeguc.ieeeguc.models.User.Gender;
import org.ieeeguc.ieeeguc.models.User.Type;
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

    EditText email ;
    EditText password ;
    Button send ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email) ;
        password = (EditText) findViewById(R.id.password) ;
        send = (Button) findViewById(R.id.email_sign_in_button) ;
        final String[] message = new String[1];
        send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin() ;
        }});
    }

    public void attemptLogin() {
        String emailText = email.getText().toString() ;
        String passwordText = password.getText().toString() ;


        if (emailText.length()!= 0 && passwordText.length()!= 0 ) {
            login(emailText, passwordText, new HTTPResponse() {

                @Override
                public void onSuccess(int statusCode, JSONObject body)  {

                    if (statusCode == 200) {




                        try {
                            String token = body.getString("token");
                            SharedPreferences Sp = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
                            SharedPreferences.Editor E = Sp.edit();
                            E.putString("token", token);
                            E.commit();

                            MainActivity.token = token ;



                            JSONObject JsonUser = body.getJSONObject("user") ;
                            int id = JsonUser.getInt("id") ;
                            String StringType = JsonUser.getString("type") ;
                            Type type ;
                            switch (StringType ){
                                case "Admin" :  type = Type.ADMIN ; break ;
                                case "Upper Board" : type = Type.UPPER_BOARD ; break ;
                                case "High Board" : type = Type.HIGH_BOARD ;break ;
                                default:type = Type.MEMBER ; break ;
                            }
                            String FN = JsonUser.getString("first_name") ;
                            String LN =JsonUser.getString("last_name") ;
                            String  StringGender = JsonUser.getString("gender") ;
                            Gender gender ;
                            switch (StringGender){
                                case "male" : gender = Gender.MALE ; break ;
                                default: gender= Gender.FEMALE ;
                            }
                            String email = JsonUser.getString("email") ;
                            String PN = JsonUser.getString("phone_number") ;
                            String BDS = JsonUser.getString("birthdate") ;
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd");
                            Date BD = dateFormatter.parse(BDS.substring(0,10));
                            String IEEE_membership_ID = JsonUser.getString("IEEE_membership_ID") ;
                            JSONObject settings = JsonUser.getJSONObject("settings") ;
                            String committeeName ;
                            int committeeID ;
                            if(JsonUser.has("committee")  ){
                                JSONObject committee =JsonUser.getJSONObject("committee") ;
                                 committeeName =committee.getString("committeeName") ;
                                 committeeID = committee.getInt("committeeID") ;
                                }
                            else{
                                 committeeName =null;
                                 committeeID = 0;

                            }

                            User user = new User(id,type,FN,LN,gender,email,BD ,IEEE_membership_ID,committeeID,committeeName,PN,settings) ;
                            MainActivity.loggedInUser = user ;
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }

                @Override
                public void onFailure(int statusCode, JSONObject body) {


                    if(statusCode == 401)
                    {
                        Snackbar.make(findViewById(R.id.email_sign_in_button), "your email or password is wrong",
                                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                                .show();
                    }

                    if(statusCode == 500){

                        Snackbar.make(findViewById(R.id.email_sign_in_button), "IEEE is down",
                                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new OnClickListener() {
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

                Snackbar.make(findViewById(R.id.email_sign_in_button), "please enter your password",
                        Snackbar.LENGTH_INDEFINITE).setAction("Ok", new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                        .show();


            } else {
                if(passwordText.length()!= 0)
                {
                    Snackbar.make(findViewById(R.id.email_sign_in_button), "please enter your email",
                            Snackbar.LENGTH_INDEFINITE).setAction("Ok", new OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                            .show();
                }

                else{
                    Snackbar.make(findViewById(R.id.email_sign_in_button), "please enter your password and email",
                            Snackbar.LENGTH_INDEFINITE).setAction("Ok", new OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                            .show();

                }
            }
        }
    }

}


