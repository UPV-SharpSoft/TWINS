package com.sharpsoft.twins_clases.logic;

public interface Card {

    boolean sameImage(Card c);
    boolean isSameDeck(Card c);
    void setDeck(String name);
    String getDeck();
    void turn();
    boolean isFacedUp();
    void setBoard(Board board, int x, int y);
}
