package org.ieeeguc.ieeeguc.models;

import org.json.JSONObject;
import java.util.Date;

public class User {
	private int id;
	private Type type;
	private String firstName;
	private String lastName;
	private String email;
	private Gender gender;
	private Date birthdate;
	private String ieeeMembershipID;
	private int committeeID;
	private String committeeName;
	private String phoneNumber;
	private JSONObject settings;


	public User(int id, Type type, String firstName, String lastName, Gender gender, String email, Date birthdate, String ieeeMembershipID, int committeeID, String committeeName, String phoneNumber, JSONObject settings) {
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.birthdate = birthdate;
		this.ieeeMembershipID = ieeeMembershipID;
		this.committeeID = committeeID;
		this.committeeName = committeeName;
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.settings = settings;
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

	public Type getType() {
		return Type;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
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
