package com.sharpsoft.twins_clases.logic;

public class BoardByDeck extends Board {

    public BoardByDeck(Dimension dimension, int segundosPorTurno) {
        super(dimension, segundosPorTurno);
    }

    @Override
    public void turn(int x, int y){
        Card c = cards[x][y];
        if(c.isFacedUp()) return; //salir si la carta ya ha sido girada
        if(super.isWaiting) return;

        c.turn();

        setChanged();
        notifyObservers(FlipObserver.On.Flip);

        cardsUpside.push(c);
        if(cardsUpside.size() % 2 == 0){  //Se ha girado la segunda carta
            super.turn.endTurn();
            Card c1 = cardsUpside.pop();
            Card c2 = cardsUpside.pop();
            if(c1.sameImage(c2) && c1.isSameDeck(c2)){ //Coinciden
                cardsUpside.push(c1);
                cardsUpside.push(c2);

                super.score.correct();

                setChanged();
                notifyObservers(FlipObserver.On.success);
            }else{  //No coinciden
                super.turnCards(c1, c2);

                score.fail();

                setChanged();
                notifyObservers(FlipObserver.On.failure);
            }
        }else{  //Se ha girado la primera carta
            turn.startTurn();
        }
    }
}
