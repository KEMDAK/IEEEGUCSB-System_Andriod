package org.ieeeguc.ieeeguc.models;

import org.ieeeguc.ieeeguc.HTTPResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Meeting {

    private static final MediaType CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");

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

    public void edit(Date startDate,Date endDate,String location,String goals,String description,int[] attendees,String token,int id,final HTTPResponse HTTP_RESPONSE){
        OkHttpClient client = new OkHttpClient();
        JSONObject j = new JSONObject();
        try {
            j.put("start_date", startDate);
            j.put("end_date", endDate);
            j.put("location", location);
            j.put("goals", goals);
            j.put("description", description);
            j.put("attendees", attendees);

            RequestBody body = RequestBody.create(CONTENT_TYPE, j.toString());
            Request request = new Request.Builder()
                    .url("http://ieeeguc.org/api//meeting/"+id)
                    .header("user_agent", "Android")
                    .header("Authorization", token)
                    .put(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
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
        }catch (JSONException e) {
            HTTP_RESPONSE.onFailure(500,null);
        }


    }
}

