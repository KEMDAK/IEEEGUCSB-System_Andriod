package org.ieeeguc.ieeeguc.fragments.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.controllers.LoginActivity;
import org.ieeeguc.ieeeguc.controllers.MainActivity;
import org.ieeeguc.ieeeguc.models.User;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.N;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserUpdate extends Fragment {
    private User user;
    private MainActivity mainAct;
    private View thisView ;
    private EditText New;
    private EditText old ;
    private EditText memberID;
    private EditText phoneNumber;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_update, container, false);
        thisView = view ;
        user = new Gson().fromJson(getArguments().getString("users"), User.class);
        ((TextView) view.findViewById(R.id.memberID)).setText(user.getIeeeMembershipID());
        ((TextView) view.findViewById(R.id.UserPhoneNumber)).setText(user.getPhoneNumber());
        New = ((EditText) view.findViewById(R.id.newPassword));
        old = ((EditText) view.findViewById(R.id.oldPassword));
        memberID = ((EditText) view.findViewById(R.id.memberID));
        phoneNumber = ((EditText) view.findViewById(R.id.UserPhoneNumber));
        return view ;
    }
          public void updateProfile(View updateview) {
              String newPassword = New.getText().toString();
              String oldPassword = old.getText().toString();
              String phoneNumber1 = phoneNumber.getText().toString();
              String memberShipID = memberID.getText().toString();
              if (newPassword.length() != 0 && oldPassword.length() != 0) {
                  user.editProfile(mainAct.token, oldPassword, newPassword, memberShipID, phoneNumber1, new HTTPResponse() {
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
