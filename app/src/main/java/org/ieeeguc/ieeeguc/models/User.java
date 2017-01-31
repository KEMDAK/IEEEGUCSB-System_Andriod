package org.ieeeguc.ieeeguc.models;

import org.json.JSONObject;

import java.util.Date;
public class User {
    private  type Type;
private  String firstName;
    private  String LastName;
    private String email;
    private Gender gender;
    private Date birthdate;
    private String ieeeMembershipID;
    private int committeeID;
    private String committeeName;
    private int id;
    private String phoneNumber;
    private JSONObject settings;


    public User( type Type, String firstName, String lastName, String email, Gender gender, Date birthdate, String ieeeMembershipID,
                 String PhoneNumber){
        this.Type=Type;
        this.firstName=firstName;
        this.LastName=lastName;
        this.gender=gender;
        this.birthdate=birthdate;
        this.email=email;
        this.ieeeMembershipID=ieeeMembershipID;
        this.phoneNumber=phoneNumber;

    }
    public int getId() {
        return id;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }



    public JSONObject getSettings() {
        return settings;
    }


    public type getType() {
        return Type;
    }


    public String getFirstName() {
        return firstName;
    }



    public String getLastName() {
        return LastName;
    }



    public String getEmail() {
        return email;
    }



    public Date getBirthdate() {
        return birthdate;
    }



    public Gender getGender() {
        return gender;
    }



    public String getIeeeMembershipID() {
        return ieeeMembershipID;
    }



    public int getCommitteeID() {
        return committeeID;
    }



    public String getCommitteeName() {
        return committeeName;
    }



}
