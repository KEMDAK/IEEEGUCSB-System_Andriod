package org.ieeeguc.ieeeguc.fragments.user;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.controllers.MainActivity;
import org.ieeeguc.ieeeguc.models.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.ieeeguc.ieeeguc.controllers.MainActivity.*;
import static org.ieeeguc.ieeeguc.models.User.getUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserShow extends Fragment {

    int id=0;
    String type="";
    String first_name="";
    String last_name="";
    String email="";
    String gender="";
    String phone_number="";
    String birthdate="";
    int IEEE_membership_ID=0;
    //JSONObject settings ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view = inflater.inflate(R.layout.fragment_user_show, container, false);


        //  final int id = new Gson().fromJson(getArguments().getString("user_id"),Integer.class);
        // String token  = new Gson().fromJson(getArguments().getString("token"),String.class);
        MainActivity.loggedInUser.getUser(MainActivity.token,MainActivity.loggedInUser.getId(), new HTTPResponse() {
            @Override
            public void onSuccess(int statusCode, JSONObject body) {
                try {
                    JSONObject j = body.getJSONObject("result");
                    id = j.getInt("id");
                    type = j.getString("type");
                    first_name  = j.getString("first_name");
                    last_name = j.getString("last_name");
                    email = j.getString("email");
                    gender = j.getString("gender");
                    phone_number = j.getString("phone_number");
                    birthdate = j.getString("birthdate");
                    IEEE_membership_ID = j.getInt("IEEE_membership_ID");
                    //settings = j.getJSONObject("setting");




                } catch (Exception e) {

                    MainActivity.createSnackBar("");
                }

                ((TextView) view.findViewById(R.id.name)).setText(first_name+" "+last_name);
                ((TextView) view.findViewById(R.id.email)).setText(email);
                ((TextView) view.findViewById(R.id.id)).setText(id+"");
                ((TextView) view.findViewById(R.id.ieeemsh_id)).setText(IEEE_membership_ID+"");
                ((TextView) view.findViewById(R.id.phone)).setText(phone_number);
                ((TextView) view.findViewById(R.id.type)).setText(type);

                ((TextView) view.findViewById(R.id.bdate)).setText(birthdate);
                ((TextView) view.findViewById(R.id.gender)).setText(gender);
            }

            @Override
            public void onFailure(int statusCode, JSONObject body) {

                if(statusCode == 401) {
                    MainActivity.logout();
                }
                else if(statusCode == 400)
                {
                    //don't remember which message
                    MainActivity.createSnackBar("");
                }else if(statusCode == 500)
                {
                    MainActivity.createSnackBar(getString(R.string.error_server_down));
                }else if(statusCode == -1)
                {
                    MainActivity.createSnackBar(getString(R.string.error_connection));

                }

            }
        });

        return view;
    }

}
