package org.ieeeguc.ieeeguc.models;

import java.util.ArrayList;

/**
 * Created by abdelrahmen on 16/02/17.
 */

public class Committee {

    private String name;
    private String description;
    private ArrayList<User> members;

    public Committee(String name,String description,ArrayList<User> members){

        this.name = name;
        this.description = description;
        this.members = members;
    }
}
