package org.ieeeguc.ieeeguc.models;

import android.widget.TextView;

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

import static org.ieeeguc.ieeeguc.controllers.MainActivity.token;

/**
 * Class to be used as a container for the user.
 */
public class User{

    public static final MediaType CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");

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
     * this method is called when the user forget the password and send an email to the user email address
     * @param {string} email [email of the user]
     * @param {HTTPResponse} HTTP_RESPONSE [HTTPResponse interface instance]
     * @return {void}
     */
    public static void forgetPassword(String email, final HTTPResponse HTTP_RESPONSE) {
        HashMap <String,String>body = new HashMap();
        body.put("email", email) ;
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://ieeeguc.org/api/forgotPassword")
                .post(RequestBody.create(CONTENT_TYPE,(new JSONObject(body)).toString()))
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                HTTP_RESPONSE.onFailure(-1, null);
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String body = response.body().string();
                    HTTP_RESPONSE.onSuccess(200,new JSONObject(body)) ;
                } catch (JSONException e) {
                    HTTP_RESPONSE.onFailure(500, null); ;
                }

                response.close();
            }
        });
    }

    /**
     * This Method is used by the user to login to the Server
     * @param {string} email [email of the user]
     * @param {string} password [password of the user]
     * @param {HttpResponse} HTTP_RESPONSE [http interface instance which is the response coming from the server after logging in containing info of the user in the Database]
     * @return {void}
     */
    public static void login(String email , String password ,final HTTPResponse HTTP_RESPONSE){

        OkHttpClient client = new OkHttpClient();
        JSONObject jsonBody = new JSONObject();
        try{
            jsonBody.put("email",email);
            jsonBody.put("password",password);
            RequestBody body = RequestBody.create(CONTENT_TYPE, jsonBody.toString());
            Request request = new Request.Builder()
                    .url("http://ieeeguc.org/api/login")
                    .header("user_agent","Android")
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    HTTP_RESPONSE.onFailure(-1,null);
                    call.cancel();
                }
                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        String responseData = response.body().string();
                        JSONObject json = new JSONObject(responseData);
                        int x = response.code();
                        String y = Integer.toString(x);
                        if(y.charAt(0)== '2'){
                            HTTP_RESPONSE.onSuccess(x,json);
                        }
                        else{
                            HTTP_RESPONSE.onFailure(x,json);
                        }
                    } catch (Exception e) {
                        HTTP_RESPONSE.onFailure(500,null);
                    }
                    response.close();
                }
            });
        }catch(JSONException e){
            HTTP_RESPONSE.onFailure(-1,null);
        }

    }

    /**
     * this method is called when a user wants to get the info of a specific committee
     * @param {String} id [id of the desired Committee]
     * @param {HTTPResponse} HTTP_RESPONSE [HTTPResponse interface instance]
     * @return {void}
     */
    public static void getCommittee(String id,final HTTPResponse HTTP_RESPONSE){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url("http://ieeeguc.org/api/Committee/"+id)
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                HTTP_RESPONSE.onFailure(-1,null);
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    JSONObject json = new JSONObject(responseData);
                    int x = response.code();
                    String y = Integer.toString(x);
                    if(y.charAt(0)== '2'){
                        HTTP_RESPONSE.onSuccess(x,json);
                    }
                    else{
                        HTTP_RESPONSE.onFailure(x,json);
                    }
                } catch (JSONException e) {
                    HTTP_RESPONSE.onFailure(500,null);
                }
                response.close();
            }
        });
    }

    /**
     * this method is called when a user of Type at least Upper Board wants to create a user in the Database
     * @param {String} userToken [token of the requesting user]
     * @param {String} email,password,firstName,LastName,birthdate,PhoneNumber,gender,id [all attributes of the added user]
     * @param {HTTPResponse} HTTP_RESPONSE [HTTPResponse interface instance]
     * @return {void}
     */

    public static void addUser(String userToken,String type ,String email ,String password , String firstName ,String lastName ,String birthDate, String phoneNumber,
    String gender,String id , final HTTPResponse HTTP_RESPONSE){
        OkHttpClient client= new OkHttpClient();
        JSONObject jsonBody = new JSONObject();
        try{
            jsonBody.put("type",type);
            jsonBody.put("email",email);
            jsonBody.put("password",password);
            jsonBody.put("first_name",firstName);
            jsonBody.put("last_name",lastName);
            jsonBody.put("birthdate",birthDate);
            jsonBody.put("phone_number",phoneNumber);
            jsonBody.put("gender",gender);
            jsonBody.put("IEEE_membership_ID",id);

            RequestBody body = RequestBody.create(CONTENT_TYPE, jsonBody.toString());
        Request request=new Request.Builder()
                .url("http://ieeeguc.org/api/User")
                .addHeader("Authorization",userToken)
                .addHeader("user_agent","Android")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                HTTP_RESPONSE.onFailure(-1,null);
                call.cancel();
            }
            public void onResponse(Call call, okhttp3.Response response)  {
                try {
                    String responseData = response.body().string();
                    JSONObject json = new JSONObject(responseData);
                    int x = response.code();
                    String y = Integer.toString(x);
                    if(y.charAt(0)== '2'){
                        HTTP_RESPONSE.onSuccess(x,json);
                    }
                    else{
                        HTTP_RESPONSE.onFailure(x,json);
                    }
                } catch (Exception e) {
                    HTTP_RESPONSE.onFailure(500,null);
                }
                response.close();
            }
        });
        }catch(JSONException e){
            HTTP_RESPONSE.onFailure(-1,null);
        }
    }
    /**
     * this method is called when the user to get information about some other user , the returned body will differ according to type of requested user
     * @param {String} token [token of the user]
     * @param {int} id [id of the user]
     * @param {HTTPResponse} HTTP_RESPONSE [HTTPResponse interface instance]
     * @return {void}
     */
    public static void getUser(String token, int id, final HTTPResponse HTTP_RESPONSE){

        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url("http://ieeeguc.org/api/User/"+id)
                .addHeader("Authorization",token)
                .addHeader("user_agent","Android")
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                HTTP_RESPONSE.onFailure(-1,null);
                call.cancel();
            }
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                int code=response.code();
                String c=code+"";
                String body=response.body().string();
                try {
                    JSONObject rr =new JSONObject(body);
                    if(c.charAt(0)=='2'){
                        HTTP_RESPONSE.onSuccess(code,rr);

                    }else {
                        HTTP_RESPONSE.onFailure(code,rr);
                    }
                }catch (JSONException e){
                    HTTP_RESPONSE.onFailure(code,null);
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
     * @param {HTTPResponse} HTTP_RESPONSE     [HTTPResponse interface instance]
     * @return {void}
     */
    public void editProfile(String token, String oldPassword, String newPassword, String IeeeMembershipID, String phoneNumber, final HTTPResponse HTTP_RESPONSE) {

        OkHttpClient client = new OkHttpClient();
        HashMap<String, String> body = new HashMap<>();
        body.put("old_password",oldPassword);
        body.put("new_password",newPassword);
        body.put("IEEE_membership_ID",IeeeMembershipID);
        body.put("phone_number",phoneNumber);

        Request request = new Request.Builder().put(RequestBody.create(CONTENT_TYPE,
                new JSONObject(body).toString()))
                .addHeader("Authorization", token)
                .addHeader("user_agent", "Android")
                .url("http://ieeeguc.org/api/user").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                //No Internet Connection.
                HTTP_RESPONSE.onFailure(-1, null);
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response){

                //Getting the status code.
                int statusCode = response.code();
                String code = String.valueOf(statusCode);

                if (code.charAt(0) == '2') {

                    // The received code is of the format 2xx, and the call was successful.

                    try {

                        JSONObject responseBody = new JSONObject(response.body().string());
                        HTTP_RESPONSE.onSuccess(statusCode, responseBody);

                    } catch (Exception e) {

                        HTTP_RESPONSE.onFailure(500, null);
                    }

                } else {

                    // The received code is of the format 3xx or 4xx or 5xx,
                    // and the call wasn't successful.

                    try {
                        JSONObject responseBody = new JSONObject(response.body().string());
                        HTTP_RESPONSE.onFailure(statusCode, responseBody);
                    } catch (Exception e) {

                        HTTP_RESPONSE.onFailure(500, null);
                    }

                }

                response.close();

            }
        });

    }

    /**
     * This method is called when the user logs out.
     * @param  {String}        token         [token of the user]
     * @param  {HTTPResponse}  HTTP_RESPONSE  [HTTP_RESPONSE interface instance]
     * @return {void}
     */
    public void logout(String token, final HTTPResponse HTTP_RESPONSE){

        OkHttpClient ok = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("Authorization",token)
                .addHeader("user_agent","Android")
                .url("http://ieeeguc.org/api/logout")
                .build();

        ok.newCall(request).enqueue(new Callback() {
            @Override

            public void onFailure(Call call, IOException e) {

                HTTP_RESPONSE.onFailure(-1, null);
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
                        HTTP_RESPONSE.onSuccess(code,j);
                    }
                    else
                    {
                        HTTP_RESPONSE.onFailure(code,j);
                    }
                } catch (JSONException e) {
                    HTTP_RESPONSE.onFailure(500,null);
                }

                response.close();
            }
        });
    }
}
