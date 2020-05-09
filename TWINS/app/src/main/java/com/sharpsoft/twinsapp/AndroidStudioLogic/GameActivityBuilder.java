package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.content.Intent;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.GameActivity;
import com.sharpsoft.twinsapp.R;

public class GameActivityBuilder extends IGameActivityBuilder {

    public GameActivityBuilder() {super.game = new GameActivity();}

    public GameActivityBuilder totalTime(){
    }

    public GameActivityBuilder timePerTurn(){
    }

    public GameActivityBuilder deck(){

    }

    public GameActivityBuilder dimension(){

    }

    public GameActivityBuilder music(){

    }

    public Intent build(){
        Board board = new com.sharpsoft.twinsapp.AndroidStudioLogic.Board(dimension, timePerTurn, deck);
        GameActivity.board = board;
        Intent i = new Intent(ctx, GameActivity.class);
        i.putExtra("music", music);
        i.putExtra("time", totalTime);
        return i;
    }
}
