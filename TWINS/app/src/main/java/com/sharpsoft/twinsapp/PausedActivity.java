package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    private int volume_level;

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


        //Volumen música

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        seekBarMusic = (SeekBar) findViewById(R.id.seekBarMusic);
        seekBarMusic.setMax(MAX_VOLUME);
        seekBarMusic.setProgress(MAX_VOLUME*audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)/audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                float volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                audioInstance.getMediaPlayer().setVolume(volume, volume);

                volume_level = seekBarMusic.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
              seekBarMusic.setProgress(volume_level);
            }
        });
    }
}
