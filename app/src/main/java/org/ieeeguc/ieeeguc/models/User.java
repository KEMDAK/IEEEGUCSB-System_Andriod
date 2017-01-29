package org.ieeeguc.ieeeguc.models;

import java.util.Date;
public class User {
    private Enum type;
private  String firstName;
    private  String LastName;
    private String email;
    private Enum gender;
    private Date birthdate;
    private String ieeeMembershipID;
    private int committeeID;
    private String committeeName;

    public User(Enum type, String firstName, String lastName, String email, Enum gender, Date birthdate, String ieeeMembershipID,
                int committeeID, String committeeName){
        this.type=type;
        this.firstName=firstName;
        this.LastName=lastName;
        this.gender=gender;
        this.birthdate=birthdate;
        this.email=email;
        this.ieeeMembershipID=ieeeMembershipID;
        this.committeeID = committeeID;
        this.committeeName=committeeName;

    }
    public Enum getType() {
        return type;
    }

    public void setType(Enum type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Enum getGender() {
        return gender;
    }

    public void setGender(Enum gender) {
        this.gender = gender;
    }

    public String getIeeeMembershipID() {
        return ieeeMembershipID;
    }

    public void setIeeeMembershipID(String ieeeMembershipID) {
        this.ieeeMembershipID = ieeeMembershipID;
    }

    public int getCommitteeID() {
        return committeeID;
    }

    public void setCommitteeID(int committeeID) {
        this.committeeID = committeeID;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

}
