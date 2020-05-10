package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twins_clases.logic.Dimension;

public class Level3Builder extends ILevelBuilder {

    public Level3Builder() {super.level = new Level();}

    public void totalTime(){
        level.setTotalTime(80*1000);
    }

    public void timePerTurn(){
        level.setTimePerTurn(5*1000);
    }

    public void numPairs(){
        level.setNumPairs(6);
    }

    public void dimension(){
        level.setDimension(new Dimension(5,4));
    }
}
