package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twins_clases.logic.Score;
import com.sharpsoft.twins_clases.logic.Turn;

import java.io.Serializable;

public class Player implements Serializable {
    private int color;
    private String nickname;
    private com.sharpsoft.twins_clases.logic.Score score;

    public Player(int color, String nickname) {
        this.color = color;
        this.nickname = nickname;
        this.score = new com.sharpsoft.twins_clases.logic.Score();
    }

    public String getNickname() {
        return nickname;
    }

    public int getColor() {
        return color;
    }

    public Score getScore() {
        return score;
    }

    public void resetScore(){
        score = new Score();
    }

}
