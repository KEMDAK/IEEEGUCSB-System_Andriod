package org.ieeeguc.ieeeguc.fragments.user;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserUpdate extends Fragment {
    private View thisView ;
    private EditText New;
    private EditText old ;
    private EditText memberID;
    private EditText phoneNumber;
    private Button update ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_update, container, false);
        thisView = view ;

        ((TextView) view.findViewById(R.id.memberID)).setText(MainActivity.loggedInUser.getIeeeMembershipID());
        ((TextView) view.findViewById(R.id.UserPhoneNumber)).setText(MainActivity.loggedInUser.getPhoneNumber());
        New = ((EditText) view.findViewById(R.id.newPassword));
        old = ((EditText) view.findViewById(R.id.oldPassword));
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
        String newPassword = New.getText().toString();
        String oldPassword = old.getText().toString();
        String phoneNumber1 = phoneNumber.getText().toString();
        String memberShipID = memberID.getText().toString();
        if (newPassword.length() != 0 && oldPassword.length() != 0) {
            MainActivity.loggedInUser.editProfile(MainActivity.token, oldPassword, newPassword, memberShipID, phoneNumber1, new HTTPResponse() {
                public void onSuccess(int statusCode, JSONObject body) {
                    Snackbar.make(thisView.findViewById(R.id.update), "profile updated successfully",
                            Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                }

                public void onFailure(int statusCode, JSONObject body) {
                    if(statusCode == 401) {
                        Snackbar.make(thisView.findViewById(R.id.update), getString(R.string.error_incorrect_credentials),
                                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    }

                    else if(statusCode == 500) {
                        Snackbar.make(thisView.findViewById(R.id.update), getString(R.string.error_server_down),
                                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    }
                    else if(statusCode == -1) {
                        Snackbar.make(thisView.findViewById(R.id.update), getString(R.string.error_connection),
                                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    }
                }
            });
        } else {

            if (newPassword.length() != 0) {
                Snackbar.make(thisView.findViewById(R.id.update), "New Password is required",
                        Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {}}).show();
            }
            else if(oldPassword.length() != 0) {
                Snackbar.make(thisView.findViewById(R.id.update), "Old Password is required",
                        Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }
        }
    }
}
