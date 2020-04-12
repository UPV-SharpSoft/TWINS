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

public class BarajaFactory {

    public enum Barajas{
        minecraft,
        ESPAÑOLA
    }

    private final static String[] cartasMinecraft = {"RedstoneOre.png", "DiamondOre.png", "GoldOre.png"};

    public static Baraja getBaraja(Barajas baraja, Dimension dimension, Context ctx){
        List<Bitmap> cartas = new ArrayList<>();
        Bitmap reverso = null;

        if(baraja == Barajas.minecraft){
            for(int i = 0; i < dimension.getTotal()/2; i++){
                Bitmap b = getBitmapFromAsset("CartasMinecraft/" + cartasMinecraft[i%cartasMinecraft.length], ctx);
                cartas.add(b); cartas.add(b);
            }
            reverso = getBitmapFromAsset("CartasMinecraft/stone.png", ctx);
        }

        return new Baraja(cartas, reverso);
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
