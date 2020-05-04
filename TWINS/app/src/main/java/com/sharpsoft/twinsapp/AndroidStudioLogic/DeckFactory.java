package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sharpsoft.twins_clases.logic.Dimension;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DeckFactory {

    public enum Decks {
        minecraft,
        ESPAÑOLA
    }

    private final static String[] minecraftCards = {"RedstoneOre.png", "DiamondOre.png", "GoldOre.png",
                                        "LapislazuliOre.png", "EmeraldOre.png", "IronOre.png"};

    public static Deck getDeck(Decks deck, Dimension dimension, Context ctx){
        List<Bitmap> cards = new ArrayList<>();
        Bitmap reverse = null;
        String name = null;

        if(deck == Decks.minecraft){
            for(int i = 0; i < dimension.getTotal()/2; i++){
                Bitmap b = getBitmapFromAsset("CartasMinecraft/" + minecraftCards[i% minecraftCards.length], ctx);
                cards.add(b); cards.add(b);
            }
            reverse = getBitmapFromAsset("CartasMinecraft/stone.png", ctx);
            name = "Minecraft";
        }

        return new Deck(cards, reverse, name);
    }

    public static List<Bitmap> getAllImages(Decks deck, Context ctx){
        List<Bitmap> res = new ArrayList<>();
        if(deck == Decks.minecraft){
            for(String s : minecraftCards){
                res.add(getBitmapFromAsset("CartasMinecraft/" + s, ctx));
            }
        }
        return res;
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
