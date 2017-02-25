package org.ieeeguc.ieeeguc.fragments.user;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view = inflater.inflate(R.layout.fragment_user_show, container, false);
        final int id = new Gson().fromJson(getArguments().getString("user_id"),Integer.class);
        String token  = new Gson().fromJson(getArguments().getString("token"),String.class);
        getUser(token, id, new HTTPResponse() {
            @Override
            public void onSuccess(int statusCode, JSONObject body) {
                try {
                    JSONObject j = body.getJSONObject("result");
                    int id = (int) j.getInt("id");
                    User.Type type = (User.Type) j.get("type");
                    String first_name  = j.getString("first_name");
                    String last_name = j.getString("last_name");
                    String email = j.getString("email");
                    User.Gender gender = (User.Gender) j.get("gender");
                    String phone_number = j.getString("phone_number");
                    Date birthdate = (Date) j.get("birthdate");
                    int IEEE_membership_ID = j.getInt("IEEE_membership_ID");
                    JSONObject settings = j.getJSONObject("setting");

                    ((TextView) view.findViewById(R.id.name)).setText(first_name+" "+last_name);
                    ((TextView) view.findViewById(R.id.email)).setText(email);
                    ((TextView) view.findViewById(R.id.id)).setText(id+"");
                    ((TextView) view.findViewById(R.id.ieeemsh_id)).setText(IEEE_membership_ID+"");
                    ((TextView) view.findViewById(R.id.phone)).setText(phone_number);
                    ((TextView) view.findViewById(R.id.type)).setText(type.toString());
                     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    ((TextView) view.findViewById(R.id.bdate)).setText((sdf.format(birthdate)));
                    ((TextView) view.findViewById(R.id.gender)).setText(gender.toString());

                } catch (Exception e) {
                    Snackbar.make(findViewById(R.id.email_sign_in_button), getString(R.string.error_server_down),
                            Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                }
            }

            @Override
            public void onFailure(int statusCode, JSONObject body) {

                if(statusCode == 401) {
                    MainActivity.logout();
                }
                else if(statusCode == 400)
                {

                }else if(statusCode == 500)
                {

                }else if(statusCode == -1)
                {
                    Snackbar.make(findViewById(R.id.email_sign_in_button), getString(R.string.error_connection),
                            Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();

                }

            }
        });

        return view;
    }

}
