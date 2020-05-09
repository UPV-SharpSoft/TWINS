package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Intent;

import com.sharpsoft.twins_clases.logic.Dimension;

public interface IBuilder {

    GameActivityBuilder setTotalTime(int totalTime);

    GameActivityBuilder setTimePerTurn(int timePerTurn);

    GameActivityBuilder setDeck(Deck deck);

    GameActivityBuilder setDimension(Dimension dimension);

    GameActivityBuilder setDimension(int width, int height);

    GameActivityBuilder setMusic(int music);

    Intent build();
}
