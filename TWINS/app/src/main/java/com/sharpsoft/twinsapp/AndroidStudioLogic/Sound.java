package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import com.sharpsoft.twinsapp.R;

public class Sound {

    //VARIABLES
    private SoundPool soundFX;

    private int flipSound;
    private int gameOverSound;
    private int correctSound;
    private int victorySound;
    private int incorrectSound;
    private int shuffleSound;
    private int buttonSound;

    public enum Sounds {flip, gameover, correct, victory, incorrect, shuffle, button};

    private boolean muted;
    private float soundVolume = 1;

    private static final Sound soundInstance = new Sound();

    //MÉTODOS

    public static Sound getInstance(){return soundInstance;}

    public SoundPool getSoundPool(){return soundFX;}

    public float getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float soundVolume) {
        if(soundVolume > 1){
            this.soundVolume = 1;}
        else {
            this.soundVolume = soundVolume;
        }
    }

    public void setOnPrepared(SoundPool.OnLoadCompleteListener listener){
        soundFX.setOnLoadCompleteListener(listener);
    }

    public void createSoundPool(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundFX = new SoundPool.Builder()
                    .setMaxStreams(9)
                    .build();
        } else {
            soundFX = new SoundPool(9, AudioManager.STREAM_MUSIC, 0);
        }

        //Precarga de sonidos default
        flipSound = soundFX.load(context, R.raw.flip_default, 2);
        gameOverSound = soundFX.load(context, R.raw.gameover_default, 2);
        correctSound = soundFX.load(context, R.raw.correct_default, 2);
        victorySound = soundFX.load(context, R.raw.victoria_default, 2);
        incorrectSound = soundFX.load(context, R.raw.incorrect_default, 2);
        shuffleSound = soundFX.load(context, R.raw.shuffle, 5);
        buttonSound = soundFX.load(context, R.raw.button, 2);
    }

    public void stopSound(int streamID){
        if(soundFX != null){
            try{
                soundFX.stop(streamID);
            }catch(Exception e){
                Log.w(Audio.class.getName(), String.format("Failed to stop the soundPool player: %s", e));
            }
        }
    }

    public void makeSound(Sounds sound) {
        switch (sound) {
            case flip:
                soundFX.play(flipSound, soundVolume, soundVolume, 0, 0, 1);
                break;
            case gameover:
                soundFX.play(gameOverSound, soundVolume, soundVolume, 0, 0, 1);
                break;
            case correct:
                soundFX.play(correctSound, soundVolume, soundVolume, 0, 0, 1);
                break;
            case victory:
                soundFX.play(victorySound, soundVolume, soundVolume, 0, 0, 1);
                break;
            case incorrect:
                soundFX.play(incorrectSound, soundVolume, soundVolume, 0, 0, 1);
                break;
            case shuffle:
                Log.i("STREAM ID",  soundFX.play(shuffleSound, soundVolume, soundVolume, 0, 0, 1) + "");
                break;
            case button:
                soundFX.play(buttonSound, soundVolume, soundVolume, 0, 0, 1);
                break;

        }

    }
}
