package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twins_clases.logic.Dimension;

public class Level1Builder extends ILevelBuilder {

    public Level1Builder() {super.level = new Level();}

    public void totalTime(){
        level.setTotalTime(120*1000);
    }

    public void timePerTurn(){
        level.setTimePerTurn(5*1000);
    }

    public void numPairs(){
        level.setNumPairs(4);
    }

    public void dimension(){
        level.setDimension(new Dimension(4,5));
    }

    public void type() {
        level.setType(Level.Type.standard);
    }
}

