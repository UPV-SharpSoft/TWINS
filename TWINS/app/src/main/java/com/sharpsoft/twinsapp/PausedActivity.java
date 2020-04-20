package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.sharpsoft.twinsapp.AndroidStudioLogic.Audio;

public class PausedActivity extends AppCompatActivity {

    private ImageButton imageButtonClose;
    private SeekBar seekBarMusic;
    private SeekBar seekBarSounds;
    private Audio audioInstance = Audio.getInstance();
    private AudioManager audioManager;



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
        seekBarSounds = (SeekBar) findViewById(R.id.seekBarSound);




        //Volumen música
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        seekBarMusic.setMax(Audio.MAX_VOLUME);
        seekBarMusic.setProgress(audioInstance.getMusicSeekbarProgress());
        seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                float volume = (float) (1 - (Math.log(Audio.MAX_VOLUME - progress) / Math.log(Audio.MAX_VOLUME)));
                audioInstance.setMusicVolume(volume, volume);
                audioInstance.setMusicSeekbarProgress(progress);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //Volumen efectos
        seekBarSounds.setMax(Audio.MAX_VOLUME);
        seekBarSounds.setProgress(audioInstance.getSoundPoolProgress());
        seekBarSounds.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = (float) (1 - (Math.log(Audio.MAX_VOLUME - progress) / Math.log(Audio.MAX_VOLUME)));
                audioInstance.setSoundPoolProgress(progress);
                audioInstance.setSoundVolume(volume);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("vol", ""+audioInstance.getSoundVolume());
                Log.i("progress", ""+audioInstance.getSoundPoolProgress());

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
