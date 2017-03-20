package org.ieeeguc.ieeeguc.fragments.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ieeeguc.ieeeguc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserShow extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_show, container, false);
    }

}
