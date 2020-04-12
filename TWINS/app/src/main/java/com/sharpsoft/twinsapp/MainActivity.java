package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Audio audioInstance = Audio.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioInstance.createSoundPool(this);

        audioInstance.makeSound(Audio.Sounds.shuffle);

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

    }
}
