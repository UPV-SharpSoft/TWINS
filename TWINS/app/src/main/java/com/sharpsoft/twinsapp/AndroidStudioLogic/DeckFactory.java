package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DeckFactory {

    public enum Decks {
        minecraft,
        fruits,
    }

    private final static String[] minecraftCards = {"RedstoneOre.png", "DiamondOre.png", "GoldOre.png", "LapislazuliOre.png", "EmeraldOre.png", "IronOre.png"};
    private final static String[] fruitCards = {"apple.png", "banana.png", "carrot.png", "eggplant.png", "kiwi.png", "lemon.png", "orange.png", "pear.png", "pineapple.png", "tomato.png", "watermelon.png"};

    public static Deck getDeck(Decks deck, Dimension dimension, int numCards,Context ctx){
        List<Bitmap> cards = new ArrayList<>();
        Bitmap reverse = null;
        String name = null;

        if(deck == Decks.minecraft){
            for(int i = 0; i < dimension.getTotal()/2; i++){
                Bitmap b = getBitmapFromAsset("CartasMinecraft/" + minecraftCards[i% minecraftCards.length % numCards], ctx);
                cards.add(b); cards.add(b);
            }
            reverse = getBitmapFromAsset("CartasMinecraft/back.png", ctx);
            name = "Minecraft";
        }else if(deck == Decks.fruits){
            for(int i = 0; i < dimension.getTotal()/2; i++){
                Bitmap b = getBitmapFromAsset("FruitsDeck/" + fruitCards[i% fruitCards.length % numCards], ctx);
                cards.add(b); cards.add(b);
            }
            reverse = getBitmapFromAsset("FruitsDeck/backFruit.png", ctx);
            name = "Fruits";
        }

        return new Deck(cards, reverse, name);
    }

    private static Bitmap getBitmapFromAsset(String path, Context ctx) {
        AssetManager assetManager = ctx.getAssets();
        InputStream is = null;
        try {
            is = assetManager.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is);
    }
}
