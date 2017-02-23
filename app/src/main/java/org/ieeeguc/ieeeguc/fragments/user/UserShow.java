package org.ieeeguc.ieeeguc.fragments.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.ieeeguc.ieeeguc.R;
import org.ieeeguc.ieeeguc.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserShow extends Fragment {

    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_show, container, false);
        user = new Gson().fromJson(getArguments().getString("user_show"), User.class);
        ((TextView) view.findViewById(R.id.name)).setText(user.getFirstName()+" "+user.getLastName());
        ((TextView) view.findViewById(R.id.email)).setText(user.getEmail());
        ((TextView) view.findViewById(R.id.id)).setText(user.getId()+"");
        ((TextView) view.findViewById(R.id.committee_id)).setText(user.getCommitteeID()+"");
        ((TextView) view.findViewById(R.id.committee_name)).setText(user.getCommitteeName());
        ((TextView) view.findViewById(R.id.ieeemsh_id)).setText(user.getIeeeMembershipID());
        ((TextView) view.findViewById(R.id.phone)).setText(user.getPhoneNumber());
        ((TextView) view.findViewById(R.id.type)).setText(user.getType().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = user.getBirthdate();
        ((TextView) view.findViewById(R.id.bdate)).setText((sdf.format(date)));
        ((TextView) view.findViewById(R.id.gender)).setText(user.getGender().toString());



        return view;
    }

}
