package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, Juego.class);
                        startActivity(i);
                        finish();
                    }
                });
            }
        }, 1000);


        //Música de fondo partida
        MediaPlayer player = new MediaPlayer();
        player.setLooping(true);
        try {
            player.setDataSource("C:\\Users\\Carlos\\OneDrive - UPV\\Github\\TWINS\\TWINS\\twins-clases\\src\\main\\java\\com\\sharpsoft\\twins_clases\\Sounds\\partida-default.mp3");
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
    }
}
