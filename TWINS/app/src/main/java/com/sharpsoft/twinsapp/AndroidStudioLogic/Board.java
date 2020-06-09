package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.view.View;

import java.util.Observable;
import java.util.Stack;

public abstract class Board extends Observable {
    protected CardInterface[][] cards;
    protected Dimension dimension;
    protected Stack<CardInterface> cardsUpside;
    protected boolean isWaiting;
    protected ScoreSuperclass score;
    protected Turn turn;
    private Integer flipTime;
    boolean firstTime = true;

    public Board(Dimension dimension, int secondsPerTurn){
        this.dimension = dimension;

        int width = dimension.width;
        int height = dimension.height;

        if( ((width*height) % 2 != 0) ) throw new MalformedBoardException("The cards are odd");

        cards = new CardInterface[width][height];

        cardsUpside = new Stack<>();

        isWaiting = false;

        score = new ScoreSuperclass();

        turn = new Turn(score, secondsPerTurn, this);
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setFlipTime(int flipTime){
        this.flipTime = flipTime;
    }

    void turnCards(final CardInterface c1, final CardInterface c2){
        isWaiting = true;
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(flipTime == null ? 500: flipTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                c1.turn();
                c2.turn();
                isWaiting = false;
            }
        }.start();
    }

    public void flipAllCardsDuring(final int time){
        isWaiting = true;
        new Thread(){
            public void run(){
                for(CardInterface[] c1 : cards){
                    for(CardInterface card : c1){
                        card.turn();
                    }
                }
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isWaiting = false;
                for(CardInterface[] c1 : cards){
                    for(CardInterface card : c1){
                        card.turn();
                    }
                }
            }
        }.start();
    }

    public void turn(int x, int y){
        CardInterface c = cards[x][y];
        if(c.isFacedUp()) return;
        if(isWaiting) return;
        if(firstTime) turn.startTurn(); firstTime = false;
        c.turn();

        setChanged();
        notifyObservers(FlipObserver.On.Flip);

        cardsUpside.push(c);
        if(cardsUpside.size() % 2 == 0){
            turn.endTurn();
            CardInterface c1 = cardsUpside.pop();
            CardInterface c2 = cardsUpside.pop();
            if(isSameCard(c1, c2)){
                cardsUpside.push(c1);
                cardsUpside.push(c2);

                score.correct();

                setChanged();
                notifyObservers(FlipObserver.On.success);
            }else{
               turnCards(c1, c2);

               score.fail();

               setChanged();
               notifyObservers(FlipObserver.On.failure);
            }
        }
    }

    public CardInterface getCard(int x, int y){
        return cards[x][y];
    }

    public boolean isComplete(){
        return cardsUpside.size() == dimension.getTotal();
    }

    public ScoreSuperclass getScore(){
        return this.score;
    }

    public void setScore(ScoreSuperclass score){
        this.score = score;
        turn.setScore(score);
    }

    public Turn getTurn() {
        return turn;
    }

    protected abstract boolean isSameCard(CardInterface c1, CardInterface c2);
    public abstract View getView(Context ctx);
}


