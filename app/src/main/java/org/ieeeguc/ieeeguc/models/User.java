package org.ieeeguc.ieeeguc.models;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.json.JSONException;
import org.json.JSONObject;
<<<<<<< HEAD

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class User {
    private Type Type;
private  String firstName;
    private  String lastName;
    private String email;
    private Gender gender;
    private Date birthdate;
    private String ieeeMembershipID;
    private int committeeID;
    private String committeeName;
    private int id;
    private String phoneNumber;
    private JSONObject settings;


    public User(org.ieeeguc.ieeeguc.models.Type type, String firstName, String lastName, Gender gender, String email, Date birthdate, String ieeeMembershipID, int committeeID, String committeeName, int id, String phoneNumber, JSONObject settings) {
        Type = type;
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


    public org.ieeeguc.ieeeguc.models.Type getType() {
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


    public static void getUser(String token, int id, final HTTPResponse httpResponse){
        /*
        this method is called when the user to get information about some other user , the returned body will differ
        according to type of requested user
        *@param {String} token [token of the user]
        * @param {int} id [id of the user]
        *@param {HTTPResponse}
         httpResponse
          [httpResponse interface instance]
         *@return {void}
        */
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url("http://ieeeguc.org/api/User/"+id)
                .addHeader("Authorization",token)
                .addHeader("user_agent","Android")
                .build();
                client.newCall(request).enqueue(new Callback() {
                    public void onFailure(Call call, IOException e) {
                        httpResponse.onFailure(-1,null);
                        call.cancel();
                    }
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        int code=response.code();
                        String c=code+"";
                        String body=response.body().toString();
                    try {
                         JSONObject rr =new JSONObject(body);
                        if(c.charAt(0)=='2'){
                            httpResponse.onSuccess(code,rr);

                        }else {
                            httpResponse.onFailure(code,rr);
                        }
                    }catch (JSONException e){
httpResponse.onFailure(code,null);
                    }
                    }
                });

    }
=======
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
>>>>>>> 997224bab12137d5a27413ee6bb8c44e6862fe37
}
