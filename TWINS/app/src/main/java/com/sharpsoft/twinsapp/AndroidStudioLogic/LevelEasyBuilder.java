package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twins_clases.logic.Dimension;

public class LevelEasyBuilder extends ILevelBuilder {

    public LevelEasyBuilder() {super.level = new Level();}

    public void totalTime(){
        level.setTotalTime(60*1000);
    }

    public void timePerTurn(){
        level.setTimePerTurn(5*1000);
    }

    public void deck(){
        level.setDeck(DeckFactory.Decks.fruits);
    }

    public void dimension(){
        Dimension dimension = new Dimension(2,3);
        level.setDimension(dimension);
    }

    public void gameMode(){
        level.setGameMode("Estándar");
    }

}
