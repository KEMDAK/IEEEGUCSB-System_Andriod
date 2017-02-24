package org.ieeeguc.ieeeguc.fragments.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.controllers.MainActivity;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserUpdate extends Fragment {
    private EditText newPass;
    private EditText oldPass ;
    private EditText memberID;
    private EditText phoneNumber;
    private Button update ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_update, container, false);

        //initializing values for ID and phone number
        ((TextView) view.findViewById(R.id.memberID)).setText(MainActivity.loggedInUser.getIeeeMembershipID());
        ((TextView) view.findViewById(R.id.UserPhoneNumber)).setText(MainActivity.loggedInUser.getPhoneNumber());

        //referencing class variables to match the view
        newPass = ((EditText) view.findViewById(R.id.newPassword));
        oldPass = ((EditText) view.findViewById(R.id.oldPassword));
        memberID = ((EditText) view.findViewById(R.id.memberID));
        phoneNumber = ((EditText) view.findViewById(R.id.UserPhoneNumber));
        update = (Button) view.findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(); ;
            }
        });

        return view ;
    }

    public void updateProfile() {

        //getting info from the view
        String newPassword = newPass.getText().toString();
        String oldPassword = oldPass.getText().toString();
        String phoneNumber1 = phoneNumber.getText().toString();
        String memberShipID = memberID.getText().toString();

        if (!(oldPassword.isEmpty()) && phoneNumber1.matches("/^\\+?\\d+-?\\d+-?\\d+$/i")) {
            MainActivity.loggedInUser.editProfile(MainActivity.token, oldPassword, newPassword, memberShipID, phoneNumber1, new HTTPResponse() {
                public void onSuccess(int statusCode, JSONObject body) {
                    MainActivity.createSnackBar("profile updated Successfully");
                }

                public void onFailure(int statusCode, JSONObject body) {
                    if(statusCode == 400){
                        try {
                            MainActivity.createSnackBar(body.getString("message"));
                        } catch (JSONException e) {
                            MainActivity.createSnackBar("Internal Server Error");
                        }
                    }
                    if(statusCode==401){
                        MainActivity.logout();
                    }
                    if(statusCode == 403) {
                        MainActivity.createSnackBar("Old Password not Correct");
                    }

                    else if(statusCode == 500) {
                        MainActivity.createSnackBar(getString(R.string.error_server_down));
                    }
                    else if(statusCode == -1) {
                        MainActivity.createSnackBar(getString(R.string.error_connection));
                    }
                }
            });
        } else if(oldPassword.isEmpty()){
            MainActivity.createSnackBar("Old Password is Required");
        }
        else{
            MainActivity.createSnackBar("Incorrect phone_Number");
        }
    }
}
