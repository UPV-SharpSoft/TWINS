package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.util.ArrayList;
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

    public List<Bitmap> getAllBitmaps(){return this.cards;}

    public void setName(String deckName){
        this.deckName = deckName;
    }

    public String getName(){
        return this.deckName;
    }

    public Bitmap removeCard(){
        int i = new Random().nextInt(cards.size());
        return cards.remove(i);
    }

    public Bitmap getReverse(){
        return reverse;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return (obj instanceof Deck && ((Deck) obj).deckName.equals(this.deckName));
    }

}
