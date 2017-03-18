package org.ieeeguc.ieeeguc.models;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by abdelrahmen on 16/02/17.
 */

public class Committee {

    private String name;
    private String description;
    private ArrayList<User> members;
    private int id;

    public Committee(String name,String description,ArrayList<User> members,int id){

        this.name = name;
        this.description = description;
        this.members = members;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public int getId() {
        return id;
    }

    /**
     * This method is called to get all the existing committees in the database.
     * @param  {HTTPResponse} HTTP_RESPONSE [instance of HTTPResponse interface]
     * @return {void}
     */
    public static void getAllCommittees(final HTTPResponse HTTP_RESPONSE){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("http://ieeeguc.org/api/committee")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                // No Internet connection.
                HTTP_RESPONSE.onFailure(-1,null);
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response){

                int statusCode = response.code();

                try{
                    String responseBody = response.body().string();
                    JSONObject body = new JSONObject(responseBody);
                    if(statusCode >= 200 && statusCode < 300){
                        HTTP_RESPONSE.onSuccess(statusCode,body);
                    }
                    else{
                        HTTP_RESPONSE.onFailure(statusCode,body);
                    }

                } catch (Exception e) {
                    HTTP_RESPONSE.onFailure(500,null);
                }
            }
        });

    }

    /**
     * This function gets the information of a specific committee from the database.
     * @param {String} token [token of the user]
     * @param {int} id [id of the committee]
     * @param {HTTPResponse} HTTP_RESPONSE [HTTPResponse interface instance]
     * @return {void}
     */
    public static void getCommittee(int id, final HTTPResponse HTTP_RESPONSE){

        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url("http://ieeeguc.org/api/committee/" + id)
                .addHeader("user_agent", "Android")
                .build();

        client.newCall(request).enqueue(new Callback() {

            public void onFailure(Call call, IOException e) {
                HTTP_RESPONSE.onFailure(-1, null);
                call.cancel();
            }

            public void onResponse(Call call, okhttp3.Response response) {
                int statusCode = response.code();

                try {
                    String body = response.body().string();
                    JSONObject bodyJSON = new JSONObject(body);

                    if (statusCode / 100 == 2) {
                        HTTP_RESPONSE.onSuccess(statusCode, bodyJSON);
                    } else {
                        HTTP_RESPONSE.onFailure(statusCode, bodyJSON);
                    }
                } catch (Exception e){
                    HTTP_RESPONSE.onFailure(500, null);
                }

                response.close();
            }
        });
    }
}
