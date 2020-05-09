package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Intent;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.GameActivity;

public abstract class IGameActivityBuilder {

    GameActivity game;

    public GameActivity getGame() {
        return game;
    }

    public abstract void totalTime();

    public abstract void timePerTurn();

    public abstract void deck();

    public abstract void dimension();

    public abstract void music();

}
