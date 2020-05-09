package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twins_clases.logic.Dimension;

public class Level {
    private int totalTime = 0;
    private int timePerTurn = 0;
    private DeckFactory.Decks deck = null;
    private Dimension dimension = null;
    private String gameMode = "";

    public void setTotalTime(int totalTime)  {
        this.totalTime = totalTime;
    }
    public void setTimePerTurn(int timePerTurn) {
        this.timePerTurn = timePerTurn;
    }
    public void setDeck(DeckFactory.Decks deck) {
        this.deck = deck;
    }
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

}
