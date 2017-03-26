package org.ieeeguc.ieeeguc.fragments.user;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.controllers.MainActivity;
import org.ieeeguc.ieeeguc.models.User;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserShow extends Fragment {
    User u;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_show, container, false);
        MainActivity.loggedInUser.getUser(MainActivity.token,MainActivity.loggedInUser.getId(), new HTTPResponse() {
            @Override
            public void onSuccess(int statusCode, JSONObject body) {
                try {
                    JSONObject j = body.getJSONObject("user");

                    int id = j.getInt("id");
                   User.Type type = User.Type.valueOf(j.getString("type").toUpperCase());
                   String first_name  = j.getString("first_name");
                   String last_name = j.getString("last_name");
                   String email = j.getString("email");
                   User.Gender gender = User.Gender.valueOf(j.getString("gender").toUpperCase());
                   String phone_number = j.getString("phone_number");
                    String birthdate = j.getString("birthdate");
                   String IEEE_membership_ID = j.getString("IEEE_membership_ID");
                    JSONObject settings = j.getJSONObject("settings");
                    SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = formate.parse(birthdate);

                    u = new User(id,type,first_name, last_name,gender,email,date,
                            IEEE_membership_ID, 0, null, phone_number,settings);


                    ((TextView) view.findViewById(R.id.name)).setText(u.getFirstName()+" "+u.getLastName());
                    ((TextView) view.findViewById(R.id.email)).setText(u.getEmail());
                    ((TextView) view.findViewById(R.id.id)).setText(u.getId()+"");
                    ((TextView) view.findViewById(R.id.ieeemsh_id)).setText(u.getIeeeMembershipID());
                    ((TextView) view.findViewById(R.id.phone)).setText(u.getPhoneNumber());
                    ((TextView) view.findViewById(R.id.type)).setText(u.getType().toString());
                    Date dat = new Date();
                    String bdate = formate.format(dat);
                    ((TextView) view.findViewById(R.id.bdate)).setText(bdate);
                    ((TextView) view.findViewById(R.id.gender)).setText(u.getGender().toString());

                    } catch (Exception e) {
                    MainActivity.createSnackBar(getString(R.string.error_server_down));
                }

            }

            @Override
            public void onFailure(int statusCode, JSONObject body) {

                if(statusCode == 401) {
                    MainActivity.logout();
                }
                else if(statusCode == 400 || statusCode == 403)
                {
                    MainActivity.createSnackBar(getString(R.string.error_server_down));
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
