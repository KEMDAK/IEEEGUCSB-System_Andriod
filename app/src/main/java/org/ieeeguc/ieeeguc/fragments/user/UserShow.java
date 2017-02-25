package org.ieeeguc.ieeeguc.fragments.user;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.controllers.MainActivity;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserShow extends Fragment {
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
                    JSONObject j = body.getJSONObject("user");
                    final int id=j.getInt("id");
                    final String type= j.getString("type");
                    final String first_name  = j.getString("first_name");
                    final String last_name= j.getString("last_name");
                    final String email= j.getString("email");
                    final String gender= j.getString("gender");
                    final String phone_number= j.getString("phone_number");
                    final String birthdate= j.getString("birthdate");
                    final int IEEE_membership_ID = j.getInt("IEEE_membership_ID");
                    //settings = j.getJSONObject("setting");
                    Handler refresh = new Handler(Looper.getMainLooper());
                    refresh.post(new Runnable() {
                        public void run()
                        {
                            ((TextView) view.findViewById(R.id.name)).setText(first_name+" "+last_name);
                            ((TextView) view.findViewById(R.id.email)).setText(email);
                            ((TextView) view.findViewById(R.id.id)).setText(id+"");
                            ((TextView) view.findViewById(R.id.ieeemsh_id)).setText(IEEE_membership_ID+"");
                            ((TextView) view.findViewById(R.id.phone)).setText(phone_number);
                            ((TextView) view.findViewById(R.id.type)).setText(type);
                            ((TextView) view.findViewById(R.id.bdate)).setText(birthdate);
                            ((TextView) view.findViewById(R.id.gender)).setText(gender);
                        }
                    });

                } catch (Exception e) {

                    MainActivity.createSnackBar(getString(R.string.error_server_down));
                }
            }

            @Override
            public void onFailure(int statusCode, JSONObject body) {

                if(statusCode == 401) {
                    MainActivity.logout();
                }
                else if(statusCode == 400)
                {
                    //do
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
