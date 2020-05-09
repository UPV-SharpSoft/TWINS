package com.sharpsoft.twinsapp.AndroidStudioLogic;

public class GameActivityDirector {

    public void Construct(IGameActivityBuilder gameBuilder){
            gameBuilder.totalTime();
            gameBuilder.timePerTurn();
            gameBuilder.deck();
            gameBuilder.dimension();
            gameBuilder.music();
            gameBuilder.build();
    }

}
