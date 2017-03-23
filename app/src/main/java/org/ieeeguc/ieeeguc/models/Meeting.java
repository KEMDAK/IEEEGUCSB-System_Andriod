package org.ieeeguc.ieeeguc.models;

import android.util.Log;
import org.ieeeguc.ieeeguc.HTTPResponse;
import org.ieeeguc.ieeeguc.controllers.MainActivity;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class Meeting {

    public static final MediaType CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");
    private int id;
    private Date start_date;
    private Date end_Date;
    private JSONObject goals;
    private int duration;
    private String location;
    private String description;
    private int evaluation;
    private ArrayList<Attendee> attendees;
    private User supervisor;
    private Date created_at;
    private Date updated_at;
    public Meeting(int id, Date start_date, Date end_Date, JSONObject goals,
                   int duration, String location, String description,
                   int evaluation, ArrayList<Attendee> attendees,
                   User supervisor, Date created_at, Date updated_at) {

        this.id = id;
        this.start_date = start_date;
        this.end_Date = end_Date;
        this.goals = goals;
        this.duration = duration;
        this.location = location;
        this.description = description;
        this.evaluation = evaluation;
        this.attendees = attendees;
        this.supervisor = supervisor;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    /**
     * This method is called when a user requests to see a meeting details.
     * @param {int}          id            [Meeting id].
     * @param {HTTPResponse} httpResponse  [Interface instance].
     * @param {String}       token         [Authorization token].
     */
    public static void getMeeting(int id, final HTTPResponse httpResponse, String token){

        String stringId = String.valueOf(id);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("Authorization",token)
                .header("user_agent","Android")
                .url("http://ieeeguc.org/api/meeting/" + stringId)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                MainActivity.UIHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        httpResponse.onFailure(-1,null);
                    }
                });
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    String body = response.body().string();
                    final JSONObject responseBody =  new JSONObject(body);
                    final int statusCode = response.code();
                    MainActivity.UIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(statusCode/100 == 2)
                                httpResponse.onSuccess(statusCode,responseBody);
                            httpResponse.onFailure(statusCode,responseBody);
                        }
                    });
                } catch (JSONException e) {
                    MainActivity.UIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            httpResponse.onFailure(500,null);
                        }
                    });
                }
                response.close();
            }
        });
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_Date() {
        return end_Date;
    }

    public JSONObject getGoals() {
        return goals;
    }

    public int getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public int getId() {
        return id;
    }

    /**
     * this method is called when a user of Type at least Upper Board wants to create a meeting in the Database
     * @param {String} userToken [token of the requesting user]
     * @param {Date} startDate , endDate [StartDate and EndDate of the Meeting]
     * @param {String} location [Location of the Meeting]
     * @param {Array of Strings} goals [the goals of the Meeting]
     * @param {String} description [Description of the Meeting]
     * @param {Array of Integers} attendees [the ids of the attendees of the Meeting]
     * @param {HTTPResponse} HTTP_RESPONSE [HTTPResponse interface instance]
     * @return {void}
     */
    public static void addMeeting(String userToken,Date startDate , Date endDate, String location, String[] goals, String description, int [] attendees , final HTTPResponse HTTP_RESPONSE){

        OkHttpClient client= new OkHttpClient();
        JSONObject jsonBody = new JSONObject();
        try{
            jsonBody.put("start_date",startDate);
            jsonBody.put("end_date",endDate);
            jsonBody.put("location",location);
            jsonBody.put("goals", new JSONArray(Arrays.toString(goals)));
            jsonBody.put("description",description);
            jsonBody.put("attendees",new JSONArray(Arrays.toString(attendees)));


            RequestBody body = RequestBody.create(CONTENT_TYPE, jsonBody.toString());
            Request request=new Request.Builder()
                    .url("http://ieeeguc.org/api/meeting")
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
            HTTP_RESPONSE.onFailure(500,null);
        }
    }

    public static class Attendee{

        private User user;
        private String review;
        private int rating;

        public Attendee(User user, String review, int rating) {
            this.user = user;
            this.review = review;
            this.rating = rating;
        }
    }
}

