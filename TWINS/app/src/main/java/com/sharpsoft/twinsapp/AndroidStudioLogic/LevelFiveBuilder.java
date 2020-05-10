package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twins_clases.logic.Dimension;

public class LevelFiveBuilder extends ILevelBuilder {

    public LevelFiveBuilder() {super.level = new Level();}

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
}
