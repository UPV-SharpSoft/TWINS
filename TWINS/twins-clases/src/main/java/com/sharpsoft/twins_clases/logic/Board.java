package com.sharpsoft.twins_clases.logic;

import java.util.Observable;
import java.util.Stack;

public class Board extends Observable {
    protected Card[][] cards;
    private Dimension dimension;
    private Stack<Card> cardsUpside; //cartasGiradas
    private boolean isWaiting;
    private Score score;

    public Board(Dimension dimension){
        this.dimension = dimension;

        int width = dimension.width;
        int height = dimension.height;

        if( ((width*height) % 2 != 0) || (width * height == 0)) throw new MalformedBoardException("The cards are odd");

        cards = new Card[width][height];

        cardsUpside = new Stack<>();

        isWaiting = false;

        score = new Score();
    }

    public Dimension getDimension() {
        return dimension;
    }

    private void turnCards(final Card c1, final Card c2){
        isWaiting = true;
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                c1.turn();
                c2.turn();
                isWaiting = false;
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
            Card c1 = cardsUpside.pop();
            Card c2 = cardsUpside.pop();
            if(c1.sameImage(c2)){ //Coinciden
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
    }

}