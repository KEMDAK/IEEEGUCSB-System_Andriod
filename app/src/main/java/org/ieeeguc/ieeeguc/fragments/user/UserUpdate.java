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

        ((TextView) view.findViewById(R.id.memberID)).setText(MainActivity.loggedInUser.getIeeeMembershipID());
        ((TextView) view.findViewById(R.id.UserPhoneNumber)).setText(MainActivity.loggedInUser.getPhoneNumber());
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
        String newPassword = newPass.getText().toString();
        String oldPassword = oldPass.getText().toString();
        String phoneNumber1 = phoneNumber.getText().toString();
        String memberShipID = memberID.getText().toString();

        if (!(newPassword.isEmpty()) && !(oldPassword.isEmpty()) && !(phoneNumber1.isEmpty() || phoneNumber1.length()!=13)) {
            MainActivity.loggedInUser.editProfile(MainActivity.token, oldPassword, newPassword, memberShipID, phoneNumber1, new HTTPResponse() {
                public void onSuccess(int statusCode, JSONObject body) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "profile updated successfully",
                            Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                }

                public void onFailure(int statusCode, JSONObject body) {
                    if(statusCode == 401) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.error_incorrect_credentials),
                                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    }

                    else if(statusCode == 500) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.error_server_down),
                                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    }
                    else if(statusCode == -1) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.error_connection),
                                Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    }
                }
            });
        } else {

            if (newPassword.isEmpty()) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "New Password is required",
                        Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {}}).show();
            }
            else if(oldPassword.isEmpty()) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Old Password is required",
                        Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }
            else if(phoneNumber1.isEmpty() || phoneNumber1.length()!= 13){
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Correct Phone Number is required",
                        Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }
        }
    }
}
