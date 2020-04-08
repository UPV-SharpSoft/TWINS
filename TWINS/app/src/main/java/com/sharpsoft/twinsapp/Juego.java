package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.sharpsoft.twins_clases.logic.Tablero;
import com.sharpsoft.twinsapp.ILogic.ICarta;

import java.io.IOException;
import java.io.InputStream;

public class Juego extends AppCompatActivity {
    private Tablero tablero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        Bitmap b = null;
        try {
            InputStream is = getAssets().open("CartasBasicas/RedstoneOre.png");
            b = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 5; i ++){
            ICarta carta = new ICarta(b, b, this);
            ((LinearLayout) findViewById(R.id.linearLayoutJuego)).addView(carta.getCartaView());
        }
    }
}
