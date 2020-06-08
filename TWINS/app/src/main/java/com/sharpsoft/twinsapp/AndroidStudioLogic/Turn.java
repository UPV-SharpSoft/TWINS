package com.sharpsoft.twinsapp.AndroidStudioLogic;

import java.util.Observable;
import java.util.Observer;

public class Turn extends Observable {
    private ScoreSuperclass score;
    private int duration;
    private Board board;
    private Thread thread;

    private enum type{startTurn, endTurn, lostTurn}

    private void initThread(){
        thread = new Thread(){
            public void run(){
                try{
                    Thread.sleep(duration);
                    lostTurn();
                } catch (InterruptedException | RuntimeException e) {this.interrupt(); e.printStackTrace();}
            }
        };
    }

    public Turn(ScoreSuperclass score, int duration, Board board){
        this.score = score;
        this.duration = duration;
        this.board = board;
    }

    public void setScore(ScoreSuperclass score){
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

        startTurn();
    }

    private void lostTurn(){
        thread.interrupt();
        score.missedTurn();
        if(board.cardsUpside.size() % 2 == 1){
            CardInterface lastFlipCard = board.cardsUpside.pop();
            lastFlipCard.turn();
        }
        setChanged();
        notifyObservers(type.lostTurn);

        startTurn();
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
            System.out.println("asd" + " " + o.toString());
        }

        public abstract void onStart();
        public abstract void onEnd();
        public abstract void lost();
    }
}
