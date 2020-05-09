package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.content.Intent;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.GameActivity;
import com.sharpsoft.twinsapp.R;

public class GameActivityBuilder {
    private Context ctx;

    private Integer totalTime;
    private Deck deck;
    private Dimension dimension;
    private Integer music;
    private Integer timePerTurn;

    public GameActivityBuilder(Context ctx){
        this.ctx = ctx;
        this.timePerTurn = 5;
        this.music = R.raw.partida_default;
        this.totalTime = 60*1000;
    }

    public GameActivityBuilder setTotalTime(int totalTime){
        this.totalTime = totalTime;
        return this;
    }

    public GameActivityBuilder setTimePerTurn(int timePerTurn){
        this.timePerTurn = timePerTurn;
        return this;
    }

    public GameActivityBuilder setDeck(Deck deck){
        this.deck = deck;
        return this;
    }

    public GameActivityBuilder setDimension(Dimension dimension){
        this.dimension = dimension;
        return this;
    }

    public GameActivityBuilder setDimension(int width, int height){
        this.dimension = new Dimension(width, height);
        return this;
    }

    public GameActivityBuilder setMusic(int music){
        this.music = music;
        return this;
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
