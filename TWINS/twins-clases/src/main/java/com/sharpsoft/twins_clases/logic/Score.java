package com.sharpsoft.twins_clases.logic;

public class Score {
    private int numCorrect;
    private int numFails;

    public Score(){
        this.numFails = 0;
        this.numCorrect = 0;
    }

    public void correct(){
        numCorrect++;
    }
    public void fail(){
        numFails++;
    }

    public int getScore(){
        return (10* numCorrect) - (4* numFails);
    }
}