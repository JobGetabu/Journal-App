package com.job.jounalapp.datasource;

import com.google.firebase.Timestamp;

import java.util.HashMap;

/**
 * Created by Job on Monday : 6/25/2018.
 *
 *
 *
 * POJO class that maps the data coming from the
 * firestore collection into the list view in the home fragment.
 */

public class Dairy {
    private String userid;
    private Timestamp timestamp;
    private HashMap<String,String> moods;
    private String details;

    public Dairy() {
    }

    public Dairy(String userid, Timestamp timestamp, HashMap<String, String> moods, String details) {
        this.userid = userid;
        this.timestamp = timestamp;
        this.moods = moods;
        this.details = details;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public HashMap<String, String> getMoods() {
        return moods;
    }

    public void setMoods(HashMap<String, String> moods) {
        this.moods = moods;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Dairy{" +
                "timestamp=" + timestamp +
                ", moods=" + moods +
                ", details='" + details + '\'' +
                '}';
    }
}
