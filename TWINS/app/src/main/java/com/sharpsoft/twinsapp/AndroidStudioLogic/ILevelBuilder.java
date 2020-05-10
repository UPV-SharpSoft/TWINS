package com.sharpsoft.twinsapp.AndroidStudioLogic;

public abstract class ILevelBuilder {

    Level level;

    public Level getLevel() {
        return level;
    }

    public abstract void totalTime();

    public abstract void timePerTurn();

    public abstract void numPairs();

    public abstract void dimension();


}

