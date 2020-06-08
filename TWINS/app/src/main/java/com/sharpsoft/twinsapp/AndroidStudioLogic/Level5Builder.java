package com.sharpsoft.twinsapp.AndroidStudioLogic;

public class Level5Builder extends ILevelBuilder {

    public Level5Builder() {super.level = new Level();}

    public void totalTime(){
        level.setTotalTime(60*1000);
    }

    public void timePerTurn(){
        level.setTimePerTurn(5*1000);
    }

    public void numPairs(){
        level.setNumPairs(8);
    }

    public void dimension(){
        level.setDimension(new Dimension(4,6));
    }

    public void type() {
        level.setType(Level.Type.standard);
    }
}
