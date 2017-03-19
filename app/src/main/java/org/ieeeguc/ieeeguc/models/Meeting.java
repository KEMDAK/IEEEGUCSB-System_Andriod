package org.ieeeguc.ieeeguc.models;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Meeting {

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
}

