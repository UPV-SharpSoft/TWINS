package com.sharpsoft.twins_clases.logic;

public interface Card {

    boolean sameImage(Card c);
    void turn();
    boolean isFacedUp();
    void setBoard(Board board, int x, int y);
}
