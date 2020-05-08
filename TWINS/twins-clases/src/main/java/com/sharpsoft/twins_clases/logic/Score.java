package com.sharpsoft.twins_clases.logic;

import java.io.Serializable;

public class Score implements Serializable {
    private int numCorrect;
    private int numFails;
    private int missedTurns;

    public Score(){
        this.numFails = 0;
        this.numCorrect = 0;
        this.missedTurns = 0;
    }

    public void correct(){
        numCorrect++;
    }
    public void fail(){
        numFails++;
    }
    public void missedTurn(){
        missedTurns++;
    }

    public int getScore(){
        return (10* numCorrect) - (4* numFails) - (2* missedTurns);
    }
}
