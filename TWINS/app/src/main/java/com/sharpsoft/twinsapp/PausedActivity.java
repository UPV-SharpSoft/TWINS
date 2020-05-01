package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.sharpsoft.twinsapp.AndroidStudioLogic.Audio;

public class PausedActivity extends AppCompatActivity {

    private ImageButton imageButtonClose;
    private SeekBar seekBarMusic;
    private SeekBar seekBarSounds;
    private Audio audioInstance = Audio.getInstance();
    private AudioManager audioManager;
    float soundVolume;
    float musicVolume;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paused);

        Button resumeGameButton = findViewById(R.id.resumeGame);
        Button restartGameButton = findViewById(R.id.restartGame);
        Button mainMenuButton = findViewById(R.id.mainMenu);
        final ImageButton muteAllButton = findViewById(R.id.muteAll);
        resumeGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        if (audioInstance.isMuted()) {
            muteAllButton.setImageResource(android.R.drawable.ic_lock_silent_mode);
        }else{
            muteAllButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
        }

        soundVolume = Audio.getSoundVolume();
        musicVolume = Audio.getMusicVolume();

        muteAllButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.i("volumenes", soundVolume + " " + musicVolume);
                if(!audioInstance.isMuted()){
                    soundVolume = Audio.getSoundVolume();
                    musicVolume = Audio.getMusicVolume();
                    audioInstance.setMuted(true);
                    muteAllButton.setImageResource(android.R.drawable.ic_lock_silent_mode);
                    audioInstance.setMusicVolume(0,0);
                    audioInstance.setSoundVolume(0);
                }else{
                    audioInstance.setMuted(false);
                    muteAllButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
                    audioInstance.setMusicVolume(1, 1);
                    audioInstance.setSoundVolume(1);
                }
            }

        });

        /** Todo Música a Opciones

        seekBarMusic = findViewById(R.id.seekBarMusic);
        seekBarSounds = findViewById(R.id.seekBarSound);


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
                if(volume > 1) volume = 1;
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
                if(volume > 1) volume = 1;
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
         */
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
