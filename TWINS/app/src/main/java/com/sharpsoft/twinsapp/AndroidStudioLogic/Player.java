package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twins_clases.logic.Turn;

public class Player {
    private int color;
    private String nickname;
    private Turn turno;

    public Player(int color, String nickname) {
        this.color = color;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public int getColor() {
        return color;
    }

    public void setTurno(Turn turno) {
        this.turno = turno;
    }

    public Turn getTurno() {
        return turno;
    }
}
