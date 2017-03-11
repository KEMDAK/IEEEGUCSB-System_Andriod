package org.ieeeguc.ieeeguc.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by abdelrahmen on 11/03/17.
 */

public class Meeting {

    private Date start_date;
    private Date end_Date;
    private String goals;
    private String duration;
    private String location;
    private String description;
    private int evaluation;
    private ArrayList<Attendee> attendees;
    private User supervisor;

    public Meeting(Date start_date, Date end_Date, String goals,
                   String duration, String location, String description,
                   int evaluation, ArrayList<Attendee> attendees, User supervisor) {

        this.start_date = start_date;
        this.end_Date = end_Date;
        this.goals = goals;
        this.duration = duration;
        this.location = location;
        this.description = description;
        this.evaluation = evaluation;
        this.attendees = attendees;
        this.supervisor = supervisor;

    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_Date() {
        return end_Date;
    }

    public String getGoals() {
        return goals;
    }

    public String getDuration() {
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

    private static class Attendee{

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

