package org.ieeeguc.ieeeguc.fragments.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.controllers.MainActivity;
import org.json.JSONObject;

import static org.ieeeguc.ieeeguc.R.id.birthdate;
import static org.ieeeguc.ieeeguc.R.id.ieeeMemberShipID;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserStore extends Fragment {
    private EditText type;
    private EditText email ;
    private EditText password;
    private EditText firstName;
    private EditText lastName ;
    private EditText birthDate;
    private EditText gender;
    private EditText memberShipId;
    private EditText phoneNumber;
    private Button createUser ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_store, container, false);

        type = ((EditText) view.findViewById(R.id.type));
        email = ((EditText) view.findViewById(R.id.email));
        password = ((EditText) view.findViewById(R.id.password));
        firstName = ((EditText) view.findViewById(R.id.firstName));
        lastName = ((EditText) view.findViewById(R.id.lastName));
        birthDate = ((EditText) view.findViewById(birthdate));
        gender = ((EditText) view.findViewById(R.id.gender));
        memberShipId = ((EditText) view.findViewById(ieeeMemberShipID));
        phoneNumber = ((EditText) view.findViewById(R.id.phoneNumber));
        createUser = (Button) view.findViewById(R.id.createUser);


        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMember();
            }
        });


        return view;
    }

    public void addMember() {

        //getting info from the view
        String memberType = type.getText().toString();
        String memberEmail = email.getText().toString();
        String memberPass = password.getText().toString();
        String memberFirstName = firstName.getText().toString();
        String memberLastName = lastName.getText().toString();
        String memberBirthDate = birthDate.getText().toString();
        String memberPhoneNumber = phoneNumber.getText().toString();
        String memberGender = gender.getText().toString();
        String ieeeMemberShipID = memberShipId.getText().toString();

        if (       !(memberType.isEmpty())
                && !(memberEmail.isEmpty())
                && !(memberPass.isEmpty())
                && !(memberFirstName.isEmpty())
                && !(memberLastName.isEmpty())
                && !(memberBirthDate.isEmpty()
                && !(memberGender.isEmpty())
                && memberPhoneNumber.matches("\\+?\\d\\d\\d-?\\d\\d\\d-?\\d\\d\\d?\\d?\\d?\\d?"))
                 ) {
            MainActivity.loggedInUser.addUser(MainActivity.token,memberType,memberEmail,memberPass,memberFirstName,memberLastName,memberBirthDate
                    ,memberPhoneNumber,memberGender,ieeeMemberShipID, new HTTPResponse() {
                public void onSuccess(int statusCode, JSONObject body) {
                    MainActivity.createSnackBar("Member is created Successfully");
                }

                public void onFailure(int statusCode, JSONObject body) {
                    if(statusCode==401){
                        MainActivity.logout();
                    }
                    else if(statusCode == 500) {
                        MainActivity.createSnackBar(getString(R.string.error_server_down));
                    }
                    else if(statusCode == -1) {
                        MainActivity.createSnackBar(getString(R.string.error_connection));
                    }
                }
            });
        } else if(memberType.isEmpty()){
            MainActivity.createSnackBar("Type is Required");
        }
        else if(memberEmail.isEmpty()){
            MainActivity.createSnackBar("Email is Required");
        }
        else if(memberPass.isEmpty()){
            MainActivity.createSnackBar("Password is Required");
        }
        else if(memberFirstName.isEmpty()){
            MainActivity.createSnackBar("FirstName is Required");
        }
        else if(memberLastName.isEmpty()){
            MainActivity.createSnackBar("LastName is Required");
        }
        else if(memberBirthDate.isEmpty()){
            MainActivity.createSnackBar("BirthDate is Required");
        }
        else if(memberGender.isEmpty()){
            MainActivity.createSnackBar("Gender is Required");
        }
        else{
            MainActivity.createSnackBar("Incorrect phone_Number");
        }
    }

}
