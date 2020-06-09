package com.sharpsoft.twinsapp.AndroidStudioLogic;

public interface CardInterface {

    boolean sameImage(CardInterface c);
    void setDeck(String name);
    String getDeck();
    void turn();
    boolean isFacedUp();
    void setBoard(Board board, int x, int y);
}
