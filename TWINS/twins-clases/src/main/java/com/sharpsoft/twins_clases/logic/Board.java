package com.sharpsoft.twins_clases.logic;

import java.util.Observable;
import java.util.Stack;

public abstract class Board extends Observable {
    protected Card[][] cards;
    protected Dimension dimension;
    protected Stack<Card> cardsUpside;
    protected boolean isWaiting;
    protected Score score;
    protected Turn turn;
    private Integer tiempoVolteo;

    public Board(Dimension dimension, int secondsPerTurn){
        this.dimension = dimension;

        int width = dimension.width;
        int height = dimension.height;

        if( ((width*height) % 2 != 0) ) throw new MalformedBoardException("The cards are odd");

        cards = new Card[width][height];

        cardsUpside = new Stack<>();

        isWaiting = false;

        score = new Score();

        turn = new Turn(score, secondsPerTurn*1000, this);
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setTiempoVolteo(int tiempoVolteo){
        this.tiempoVolteo = tiempoVolteo;
    }

    void turnCards(final Card c1, final Card c2){
        isWaiting = true;
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(tiempoVolteo == null ? 500: tiempoVolteo);
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
                for(Card[] c1 : cards){
                    for(Card card : c1){
                        card.turn();
                    }
                }
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isWaiting = false;
                for(Card[] c1 : cards){
                    for(Card card : c1){
                        card.turn();
                    }
                }
            }
        }.start();
    }

    public void turn(int x, int y){
        Card c = cards[x][y];
        if(c.isFacedUp()) return; //salir si la carta ya ha sido girada
        if(isWaiting) return;

        c.turn();

        setChanged();
        notifyObservers(FlipObserver.On.Flip);

        cardsUpside.push(c);
        if(cardsUpside.size() % 2 == 0){  //Se ha girado la segunda carta
            turn.endTurn();
            Card c1 = cardsUpside.pop();
            Card c2 = cardsUpside.pop();
            if(isSameCard(c1, c2)){ //Coinciden
                cardsUpside.push(c1);
                cardsUpside.push(c2);

                score.correct();

                setChanged();
                notifyObservers(FlipObserver.On.success);
            }else{  //No coinciden
               turnCards(c1, c2);

               score.fail();

               setChanged();
               notifyObservers(FlipObserver.On.failure);
            }
        }else{  //Se ha girado la primera carta
            turn.startTurn();
        }
    }

    public Card getCard(int x, int y){
        return cards[x][y];
    }

    public boolean isComplete(){
        return cardsUpside.size() == dimension.getTotal();
    }

    public Score getScore(){
        return this.score;
    }

    public void setScore(Score score){
        this.score = score;
        turn.setScore(score);
    }

    protected abstract boolean isSameCard(Card c1, Card c2);
}


