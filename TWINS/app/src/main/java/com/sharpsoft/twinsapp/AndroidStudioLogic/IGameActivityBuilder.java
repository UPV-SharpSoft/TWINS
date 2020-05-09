package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Intent;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.GameActivity;

public abstract class IGameActivityBuilder {

    protected GameActivity game;

    public GameActivity getGame() {
        return game;
    }

    public abstract GameActivityBuilder totalTime();

    public abstract GameActivityBuilder timePerTurn();

    public abstract GameActivityBuilder deck();

    public abstract GameActivityBuilder dimension();

    public abstract GameActivityBuilder music();

    public abstract Intent build();
}
