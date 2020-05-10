package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final AudioFacade audioFacade = AudioFacade.getInstance();

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioFacade.initializeAudio(this);

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
                        audioFacade.stopSound(1);
                        audioFacade.stopSound(2);
                        finish();

                    }
                });
            }
        };
        t.start();


    }
}
