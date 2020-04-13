package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class PausedActivity extends AppCompatActivity {

    private ImageButton imageButtonClose;
    private SeekBar seekBarMusic;
    private Audio audioInstance = Audio.getInstance();
    private AudioManager audioManager;
    private final static int MAX_VOLUME = 100;
    private int curVolume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paused);

        ImageButton imageButtonClose = findViewById(R.id.imageButtonClose);
        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        seekBarMusic = (SeekBar) findViewById(R.id.seekBarMusic);




        //Volumen música
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        seekBarMusic.setMax(MAX_VOLUME);
        seekBarMusic.setProgress(audioInstance.getMusicSeekbarProgress());
        seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                float volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                audioInstance.setMusicVolume(volume, volume);
                audioInstance.setMusicSeekbarProgress(progress);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });



    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
