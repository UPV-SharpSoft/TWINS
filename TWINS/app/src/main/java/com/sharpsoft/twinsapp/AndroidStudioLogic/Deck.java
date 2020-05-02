package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Random;

public class Deck {
    private List<Bitmap> cards;
    private Bitmap reverse;
    private String deckName;

    public Deck(List<Bitmap> cards, Bitmap reverse){
        this.cards = cards;
        this.reverse = reverse;
    }

    public void setName(){
        this.deckName = deckName;
    }

    public String getName(){
        return this.deckName;
    }

    public boolean quedanCartas(){
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
}
