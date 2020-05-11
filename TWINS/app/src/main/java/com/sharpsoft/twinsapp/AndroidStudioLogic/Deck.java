package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Random;

public class Deck {
    private List<Bitmap> cards;
    private Bitmap reverse;
    private String deckName;

    public Deck(List<Bitmap> cards, Bitmap reverse, String name){
        this.cards = cards;
        this.reverse = reverse;
        this.deckName = name;
    }

    public void setName(String deckName){
        this.deckName = deckName;
    }

    public String getName(){
        return this.deckName;
    }

    public boolean EmptyDeck(){
        return !cards.isEmpty();
    }

    public Bitmap sacarCarta(){
        int i = new Random().nextInt(cards.size());
        return cards.remove(i);
    }

    public Bitmap getReverse(){
        return reverse;
    }

    public int getNumCartas(){
        return cards.size();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return (obj instanceof Deck && ((Deck) obj).deckName.equals(this.deckName));
    }

    public void mergeDeck(Deck oDeck){
        cards.addAll(oDeck.cards);
    }
}
