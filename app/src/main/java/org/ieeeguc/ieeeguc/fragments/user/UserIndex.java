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


/**
 * A simple {@link Fragment} subclass.
 */
public class UserIndex extends Fragment {

    private User[] users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_index, container, false);
        users = new Gson().fromJson(getArguments().getString("users"), User[].class);


        ((TextView) view.findViewById(R.id.text)).setText(users[0].getEmail());
        return view;
    }

}
