package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twins_clases.logic.Turn;

public class Player {
    private int color;
    private String nickname;
    private Turn turno;

    public String getNickname() {
        return nickname;
    }

    public int getColor() {
        return color;
    }

    public Turn getTurno() {
        return turno;
    }
}
