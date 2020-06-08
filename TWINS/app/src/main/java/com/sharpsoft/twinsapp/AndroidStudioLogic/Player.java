package com.sharpsoft.twinsapp.AndroidStudioLogic;

import java.io.Serializable;

public class Player implements Serializable {
    private int color;
    private String nickname;
    private ScoreSuperclass score;

    public Player(int color, String nickname) {
        this.color = color;
        this.nickname = nickname;
        this.score = new ScoreSuperclass();
    }

    public String getNickname() {
        return nickname;
    }

    public int getColor() {
        return color;
    }

    public ScoreSuperclass getScore() {
        return score;
    }

    public void resetScore(){
        score = new ScoreSuperclass();
    }

}
