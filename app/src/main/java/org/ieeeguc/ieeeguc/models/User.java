package org.ieeeguc.ieeeguc.models;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class User{
    /**
     * This method is called when the user performs an editing operation on his profile.
     * @param token The access token which's provided when a user logs in.
     * @param oldPassword the current user's account password.
     * @param newPassword the new password that will override the old one.
     * @param IEEE_membership_ID
     * @param httpResponse Used to control the flow of the app after the editProfile is performed.
     */

    public void editProfile(String token,
                            String oldPassword,
                            String newPassword,
                            String IEEE_membership_ID,
                            final HTTPResponse httpResponse) {

        if (oldPassword == password && newPassword.length() >= 6) {

            // The user entered the right old password and a valid new password,
            // then we the app will perform with sending the editing request to the server.

            OkHttpClient client = new OkHttpClient();
            HashMap<String, String> body = new HashMap<>();
            body.put("type", type);
            body.put("email", email);
            body.put("password", newPassword);
            body.put("first_name", first_name);
            body.put("last_name", last_name);
            body.put("birthdate", birthdate);
            body.put("gender", gender);
            body.put("IEEE_membership_ID", IEEE_membership_ID);


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
                            httpResponse.onFailure(-500, null);
                        }

                    } else {

                        // The received code is of the format 3xx or 4xx or 5xx,
                        // and the call wasn't successful.

                        try {
                            JSONObject responseBody = new JSONObject(response.body().toString());
                            httpResponse.onFailure(statusCode, responseBody);
                        } catch (JSONException e) {

                            httpResponse.onFailure(-500, null);
                        }

                    }


                }
            });

        }
    }

        /**
         * This method is called after the user logouts
         * @param  {String}        token         [token of the user]
         * @param  {HTTPResponse}  httpResponse  [httpResponse interface instance]
         * @return {void}
         */
        public void logout(String token, final HTTPResponse httpResponse){
            OkHttpClient ok = new OkHttpClient();
            Request request = new Request.Builder().addHeader("Authorization",token).addHeader("user_agent","Android").build();


            ok.newCall(request).enqueue(new Callback() {
                @Override

                public void onFailure(Call call, IOException e) {
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
                        httpResponse.onFailure(-1,null);
                    }
                }
            });
        }

    }

