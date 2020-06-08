package com.sharpsoft.twinsapp.AndroidStudioLogic;

public class Level4Builder extends ILevelBuilder {

    public Level4Builder() {super.level = new Level();}

    public void totalTime(){
        level.setTotalTime(60*1000);
    }

    public void timePerTurn(){
        level.setTimePerTurn(5*1000);
    }

    public void numPairs(){
        level.setNumPairs(6);
    }

    public void dimension(){
        level.setDimension(new Dimension(4,6));
    }

    public void type() {
        level.setType(Level.Type.standard);
    }
}
