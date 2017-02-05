package org.ieeeguc.ieeeguc.models;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class User{

    public static enum Type { ADMIN, HIGH_BOARD, MEMBER, UPPER_BOARD }
    public static enum Gender { MALE, FEMALE }

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
        return type;
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

    /**
     * this method is called when the user to get information about some other user , the returned body will differ according to type of requested user
     * @param {String} token [token of the user]
     * @param {int} id [id of the user]
     * @param {HTTPResponse} httpResponse [httpResponse interface instance]
     * @return {void}
     */
    public static void getUser(String token, int id, final HTTPResponse httpResponse){

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

                response.close();
            }
        });
    }

    /**
     * This method is called when the user performs an editing operation on his profile.
     * @param {String}       token            [user's token]
     * @param {String}       oldPassword      [user's current password]
     * @param {String}       newPassword      [user's new password]
     * @param {String}       IeeeMembershipID [user's IEEE membership id]
     * @param {String}       phoneNumber      [user's phone number]
     * @param {HTTPResponse} httpResponse     [HTTPResponse interface instance]
     * @return {void}
     */
    public void editProfile(String token,
                            String oldPassword,
                            String newPassword,
                            String IeeeMembershipID,
                            String phoneNumber,
                            final HTTPResponse httpResponse) {

        OkHttpClient client = new OkHttpClient();
        HashMap<String, String> body = new HashMap<>();
        body.put("old_password",oldPassword);
        body.put("new_password",newPassword);
        body.put("IEEE_membership_ID",IeeeMembershipID);
        body.put("phone_number",phoneNumber);

        Request request = new Request.Builder().put(RequestBody.create(MediaType.parse("application/json"),
                new JSONObject(body).toString()))
                .addHeader("Authorization", token)
                .addHeader("user_agent", "Android")
                .url("http://ieeeguc.org/api/user").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                //No Internet Connection.
                httpResponse.onFailure(-1, null);
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //Getting the status code.
                int statusCode = response.code();
                String code = String.valueOf(statusCode);

                if (code.charAt(0) == '2') {

                    // The received code is of the format 2xx, and the call was successful.

                    try {

                        JSONObject responseBody = new JSONObject(response.body().toString());
                        httpResponse.onSuccess(statusCode, responseBody);

                    } catch (JSONException e) {

                        httpResponse.onFailure(500, null);
                    }

                } else {

                    // The received code is of the format 3xx or 4xx or 5xx,
                    // and the call wasn't successful.

                    try {
                        JSONObject responseBody = new JSONObject(response.body().toString());
                        httpResponse.onFailure(statusCode, responseBody);
                    } catch (JSONException e) {

                        httpResponse.onFailure(500, null);
                    }

                }

                response.close();

            }
        });

    }


    /**
     * This method is called when the user logs out.
     * @param  {String}        token         [token of the user]
     * @param  {HTTPResponse}  httpResponse  [httpResponse interface instance]
     * @return {void}
     */
    public void logout(String token, final HTTPResponse httpResponse){

        OkHttpClient ok = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("Authorization",token)
                .addHeader("user_agent","Android")
                .url("http://ieeeguc.org/api/logout")
                .build();

        ok.newCall(request).enqueue(new Callback() {
            @Override

            public void onFailure(Call call, IOException e) {

                httpResponse.onFailure(-1, null);
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                int code = response.code();
                String body = response.body().string();

                try {
                    JSONObject j = new JSONObject(body);
                    if(code/100 == 2)
                    {
                        httpResponse.onSuccess(code,j);
                    }
                    else
                    {
                        httpResponse.onFailure(code,j);
                    }
                } catch (JSONException e) {
                    httpResponse.onFailure(500,null);
                }

                response.close();
            }
        });
    }

}
