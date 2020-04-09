package com.sharpsoft.twinsapp.ILogic;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BarajaFactory {

    public enum Barajas{
        minecraft,
        ESPAÑOLA
    }

    private final static String[] cartasMinecraft = {"RedstoneOre.png", "DiamondOre.png"};

    public static Baraja getBaraja(Barajas baraja, int numCartas, Context ctx){
        List<Bitmap> cartas = new ArrayList<>();
        Bitmap reverso = null;

        if(baraja == Barajas.minecraft){
            for(int i = 0; i < numCartas/2; i++){
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
