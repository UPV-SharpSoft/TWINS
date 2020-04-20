package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;

import com.sharpsoft.twinsapp.AndroidStudioLogic.Audio;

public class MainActivity extends AppCompatActivity {
    private Audio audioInstance = Audio.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioInstance.createSoundPool(this);
        audioInstance.setOnPrepared(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                if(i1 == 0)audioInstance.makeSound(Audio.Sounds.shuffle);
            }
        });

        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, Juego.class);
                        startActivity(i);
                        finish();
                    }
                });
            }
        };
        t.start();


    }
}
