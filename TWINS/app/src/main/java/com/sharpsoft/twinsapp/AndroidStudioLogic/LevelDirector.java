package com.sharpsoft.twinsapp.AndroidStudioLogic;

public class LevelDirector {

    public void Construct(ILevelBuilder levelBuilder){
        levelBuilder.totalTime();
        levelBuilder.timePerTurn();
        levelBuilder.deck();
        levelBuilder.dimension();
        levelBuilder.gameMode();
    }

}
