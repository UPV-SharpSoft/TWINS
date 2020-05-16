package com.sharpsoft.twins_clases.logic;

import java.io.Serializable;
import java.util.Calendar;

public class FinalScore implements Serializable {
    private String type;
    private int points;
    private int time;
    private Calendar date;

    public FinalScore (String type, int points, int time, Calendar date) {
        this.type = type;
        this.points = points;
        this.date = date;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public int getPoints() {
        return points;
    }

    public int getTime() {
        return time;
    }

    public Calendar getDate() {
        return date;
    }
}
