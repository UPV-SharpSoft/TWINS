package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;

import com.sharpsoft.twinsapp.AndroidStudioLogic.Audio;
import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;

public class MainActivity extends AppCompatActivity {
    private Audio audioInstance = Audio.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AudioFacade audioFacade = AudioFacade.getInstance();s

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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
                        Intent i = new Intent(MainActivity.this, MainMenuActivity.class);
                        startActivity(i);
                        audioInstance.stopSound(1);
                        audioInstance.stopSound(2);
                        finish();

                    }
                });
            }
        };
        t.start();


    }
}
