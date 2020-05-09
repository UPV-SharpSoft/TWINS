package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twinsapp.LevelsActivity;

public abstract class ILevelBuilder {

    Level level;

    public Level getLevel() {
        return level;
    }

    public abstract void totalTime();

    public abstract void timePerTurn();

    public abstract void deck();

    public abstract void dimension();

    public abstract void gameMode();

}
