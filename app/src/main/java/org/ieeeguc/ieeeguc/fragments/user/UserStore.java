package org.ieeeguc.ieeeguc.fragments.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.controllers.MainActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserStore extends Fragment {
    private Spinner type;
    private EditText email ;
    private EditText firstName;
    private EditText lastName ;
    private DatePicker birthDate;
    private Spinner gender;
    private EditText membershipId;
    private EditText phoneNumber;
    private Button confirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_store, container, false);

        type = ((Spinner) view.findViewById(R.id.user_store_account_type));
        email = ((EditText) view.findViewById(R.id.user_store_email));
        firstName = ((EditText) view.findViewById(R.id.user_store_first_name));
        lastName = ((EditText) view.findViewById(R.id.user_store_last_name));
        birthDate = ((DatePicker) view.findViewById(R.id.user_store_birthdate));
        gender = ((Spinner) view.findViewById(R.id.user_store_gender));
        membershipId = ((EditText) view.findViewById(R.id.user_store_IEEE_membership_id));
        phoneNumber = ((EditText) view.findViewById(R.id.user_store_phone_number));
        confirm = (Button) view.findViewById(R.id.user_store_confirm);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });


        return view;
    }

    /**
     * This method extracts the info of the user and tries to call the API to create a user.
     */
    private void addUser() {
        /* reset validation errors */
        resetFormErrors();

        /* extracting input data */
        String memberType = type.getSelectedItem().toString();
        String memberEmail = email.getText().toString().toLowerCase();
        String memberFirstName = firstName.getText().toString();
        String memberLastName = lastName.getText().toString();
        String memberBirthDate = birthDate.getYear() + "-" + birthDate.getMonth() + "-" + birthDate.getDayOfMonth();
        String memberPhoneNumber = phoneNumber.getText().toString();
        String memberGender = gender.getSelectedItem().toString();
        String ieeeMemberShipID = membershipId.getText().toString();

        /* validating the input */
        boolean valid = true;
        if(memberEmail.isEmpty() || !memberEmail.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")) {
            email.setError("Please enter a valid Email.");
            valid = false;
        }
        if(memberFirstName.isEmpty()) {
            firstName.setError("Please enter a first name.");
            valid = false;
        }
        if(memberLastName.isEmpty()) {
            lastName.setError("Please enter a last name.");
            valid = false;
        }
        if(memberPhoneNumber.isEmpty() || !memberPhoneNumber.matches("\\+?\\d\\d\\d-?\\d\\d\\d-?\\d\\d\\d?\\d?\\d?\\d?")) {
            phoneNumber.setError("Please enter a valid phone number.");
            valid = false;
        }
        if(!valid)
            return;

        /* calling the API */
        MainActivity.loggedInUser.addUser(MainActivity.token, memberType, memberEmail, memberFirstName, memberLastName, memberBirthDate, memberPhoneNumber, memberGender, ieeeMemberShipID, new HTTPResponse() {
            public void onSuccess(int statusCode, JSONObject body) {
                MainActivity.createSnackBar("User is created Successfully");
                resetForm();
            }

            public void onFailure(int statusCode, JSONObject body) {
                if(statusCode == 400) {
                    try {
                        JSONArray errors = body.getJSONArray("error");
                        for (int i = 0; i < errors.length(); i++) {
                            JSONObject error = errors.getJSONObject(i);
                            String param = error.getString("param");
                            String type = error.getString("type");
                            switch (param) {
                                case "email" :
                                    switch (type) {
                                        case "required" : email.setError("Please enter an email."); break;
                                        case "validity" : email.setError("Please enter a valid email."); break;
                                        case "unique violation" : email.setError("The email is taken."); break;
                                    }
                                    break;
                                case "first_name" :
                                    switch (type) {
                                        case "required" : firstName.setError("Please enter a first name."); break;
                                        case "validity" : firstName.setError("Please enter a valid name."); break;
                                    }
                                    break;
                                case "last_name" :
                                    switch (type) {
                                        case "required" : lastName.setError("Please enter a last name."); break;
                                        case "validity" : lastName.setError("Please enter a valid name."); break;
                                    }
                                    break;
                                case "phone_number" :
                                    switch (type) {
                                        case "required" : phoneNumber.setError("Please enter a phone number."); break;
                                        case "validity" : phoneNumber.setError("Please enter a valid phone number."); break;
                                    }
                                    break;
                                case "IEEE_membership_ID" :
                                    switch (type) {
                                        case "validity" : membershipId.setError("Please enter a valid IEEE membership ID."); break;
                                    }
                                    break;
                            }
                        }
                    }
                    catch (JSONException e) {
                        MainActivity.createSnackBar(getString(R.string.error_server_down));
                    }
                }
                else if(statusCode==401){
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
    }

    /**
     * This method resets the input form.
     */
    private void resetForm() {
        resetFormErrors();
        type.setSelection(0);
        email.setText("");
        firstName.setText("");
        lastName.setText("");
        birthDate.updateDate(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
        gender.setSelection(0);
        membershipId.setText("");
        phoneNumber.setText("");
    }

    /**
     * This method resets the errors of the input form.
     */
    private void resetFormErrors() {
        email.setError(null);
        firstName.setError(null);
        lastName.setError(null);
        membershipId.setError(null);
        phoneNumber.setError(null);
    }
}
