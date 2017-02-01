package org.ieeeguc.ieeeguc.models;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class User {

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
