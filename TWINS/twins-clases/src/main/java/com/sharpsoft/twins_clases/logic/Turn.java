package com.sharpsoft.twins_clases.logic;

import java.util.Observable;
import java.util.Observer;

public class Turn extends Observable {
    private Score score;
    private int duration;
    private Board board;
    private Player player;
    private Thread thread;

    private enum type{startTurn, endTurn, lostTurn}

    private void initThread(){
        thread = new Thread(){
            public void run(){
                try{
                    Thread.sleep(duration);
                    lostTurn();
                } catch (InterruptedException e) {}
            }
        };
    }

    private void lostTurn(){
        score.missedTurn();
        Card lastFlipCard = board.cardsUpside.pop();
        lastFlipCard.turn();
        setChanged();
        notifyObservers(type.lostTurn);
    }

    public Turn(Score score, int duration, Board board, Player player){
        this.score = score;
        this.duration = duration;
        this.board = board;
        this.player = player;
    }

    public void setScore(Score score){
        this.score = score;
    }

    public void startTurn(){
        initThread();
        thread.start();
        setChanged();
        notifyObservers(type.startTurn);
    }

    public void endTurn(){
        thread.interrupt();
        setChanged();
        notifyObservers(type.endTurn);
    }

    public int getDuration(){return this.duration;}

    public static abstract class TurnObserver implements Observer{

        @Override
        public void update(Observable observable, Object o) {
            if(o == type.startTurn){
                onStart();
            }else if(o == type.endTurn){
                onEnd();
            }else if(o == type.lostTurn){
                lost();
            }
        }

        public abstract void onStart();
        public abstract void onEnd();
        public abstract void lost();
    }
}
