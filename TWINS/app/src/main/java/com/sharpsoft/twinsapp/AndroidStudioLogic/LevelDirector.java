package com.sharpsoft.twinsapp.AndroidStudioLogic;

public class LevelDirector {

    public void Construct(ILevelBuilder levelBuilder){
        levelBuilder.dimension();
        levelBuilder.totalTime();
        levelBuilder.timePerTurn();
        levelBuilder.numPairs();
    }

}
