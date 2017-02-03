package org.ieeeguc.ieeeguc.models;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
                   HTTP_RESPONSE.onFailure(response.code(),null);
            }
        }
    });
}
}
