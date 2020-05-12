package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twins_clases.logic.Dimension;

import java.io.Serializable;

public class Level implements Serializable {
    private int totalTime;
    private int timePerTurn;
    private int numPairs;
    private Dimension dimension;
    private Type type;
    private int flipTime;
    private Integer startTime;

    public enum Type{
        standard,
        byCard,
        bySet
    }

    public void setTotalTime(int totalTime)  {
        this.totalTime = totalTime;
    }
    public void setTimePerTurn(int timePerTurn) {
        this.timePerTurn = timePerTurn;
    }
    public void setNumPairs(int numPairs) {
        this.numPairs = numPairs;
    }
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
    public void setType(Type type){this.type = type;}
    public void setFlipTime(int flipTime){
        this.flipTime = flipTime;
    }
    public void setFlipStartTime(int tiempoVolteoInicio){
        this.startTime = tiempoVolteoInicio;
    }

    public int getTotalTime(){
        return this.totalTime;
    }

    public int getTimePerTurn(){
        return this.timePerTurn;
    }

    public int getNumPairs(){
        return this.numPairs;
    }

    public Dimension getDimension(){
        return this.dimension;
    }

    public Type getType(){
        return this.type;
    }

    public int getFlipTime(){
        return this.flipTime;
    }

    public Integer getFlipStartTime(){
        return this.startTime;
    }
}

