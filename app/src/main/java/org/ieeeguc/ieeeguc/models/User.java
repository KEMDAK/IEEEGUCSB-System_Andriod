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


import static android.R.attr.y;
import static android.view.View.X;
import static android.view.View.Y;

public class User {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
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

    public static void ForgetPassword(String email, final HTTPResponse HTTPR) {
        HashMap <String,String>p = new HashMap();
        p.put("email",email) ;
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://ieeeguc.org/api/login")
                .post(RequestBody.create(JSON,( new JSONObject(p)).toString()))
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) {
                try {
                    HTTPR.onSuccess(200,new JSONObject(response.body().toString())) ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void login(String email , String password ,final HTTPResponse HTTP_RESPONSE){
        final JSONObject jsonB = new JSONObject();
        try{
            jsonB.put("email",email);
            jsonB.put("password",password);
        }catch(JSONException e){
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonB.toString());
        Request request = new Request.Builder()
                .url("http://ieeeguc.org/api/login")
                .header("user_agent","Android")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    JSONObject json = new JSONObject(responseData);
                    int x = response.code();
                    String y = Integer.toString(x);
                    if(y.charAt(0)== '2'){
                        HTTP_RESPONSE.onSuccess(response.code(),json);
                    }
                    else{
                        HTTP_RESPONSE.onFailure(response.code(),json);
                    }
                } catch (JSONException e) {
                    HTTP_RESPONSE.onFailure(response.code(),null);}}});}}