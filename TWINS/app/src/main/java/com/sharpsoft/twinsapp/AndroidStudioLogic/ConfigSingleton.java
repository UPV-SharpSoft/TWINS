package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;

import com.sharpsoft.twins_clases.logic.Dimension;

public class ConfigSingleton {
    private static ConfigSingleton instance = new ConfigSingleton();

    private DeckFactory.Decks selectedDeck = DeckFactory.Decks.minecraft;
    private String customDeck;
    private boolean isCustomDeck = false;

    public static ConfigSingleton getInstance(){return instance;}

    public Deck getSelectedDeck(Dimension d, int numCartas, Context ctx){
        if(isCustomDeck){
            return DeckManagerSingleton.getInstance().getDeck(d, customDeck, numCartas, ctx);
        }else{
            return DeckFactory.getDeck(selectedDeck, d, numCartas, ctx);
        }
    }

    public void setSelectedDeck(DeckFactory.Decks decks){
        this.isCustomDeck = false;
        this.selectedDeck = decks;
    }

    public void setSelectedDeck(String customDeckName){
        this.isCustomDeck = true;
        this.customDeck = customDeckName;
    }
}
