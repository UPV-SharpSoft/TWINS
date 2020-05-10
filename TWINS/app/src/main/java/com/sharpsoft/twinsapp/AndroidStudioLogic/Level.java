package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twins_clases.logic.Dimension;

import java.io.Serializable;

public class Level implements Serializable {
    private int totalTime = 0;
    private int timePerTurn = 0;
    private int numPairs = 0;
    private Dimension dimension = null;

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
}
