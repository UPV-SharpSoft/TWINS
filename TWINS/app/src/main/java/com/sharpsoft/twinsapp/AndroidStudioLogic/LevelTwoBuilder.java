package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twins_clases.logic.Dimension;

public class LevelTwoBuilder extends ILevelBuilder {

    public LevelTwoBuilder() {super.level = new Level();}

    public void totalTime(){
        level.setTotalTime(100*1000);
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
}
