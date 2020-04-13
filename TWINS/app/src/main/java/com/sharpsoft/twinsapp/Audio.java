package com.sharpsoft.twinsapp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.net.ContentHandler;

public class Audio {
    private static final Audio audioInstance = new Audio();
    private MediaPlayer bgMusic;
    private SoundPool soundFX;

    private int flipSound;
    private int gameOverSound;
    private int correctSound;
    private int victorySound;
    private int incorrectSound;
    private int shuffleSound;

    private static int mPlayerProgress = 100;

    public enum Sounds {flip, gameover, correct, victory, incorrect, shuffle}

    public static Audio getInstance() {
        return audioInstance;
    }

    public void setMusicSeekbarProgress(int progress){
        mPlayerProgress = progress;
    }

    public int getMusicSeekbarProgress(){
        return mPlayerProgress;
    }

    public void setOnPreared(SoundPool.OnLoadCompleteListener listener){
        soundFX.setOnLoadCompleteListener(listener);
    }

    public void startMusic(Context context, int song) {
        bgMusic = MediaPlayer.create(context, song);
        bgMusic.setLooping(true);
        bgMusic.setVolume(100, 100);
        bgMusic.start();
    }

    public void resumeMusic(Context context) {
        bgMusic.start();
    }

    public void pauseMusic(Context context) {
        bgMusic.pause();
    }

    public void setMusicVolume(float left, float right) {bgMusic.setVolume(left,right);}

    public void stopMusic(Context context){
        bgMusic.stop();
        bgMusic.release();
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
        shuffleSound = soundFX.load(context, R.raw.shuffle, 1);
    }

    /**
     * Próximamente, cuando hagamos más packs de Sonidos, habrá que precargar los sonidos
     * ¿en un método aparte?
     */

    public void makeSound(Sounds sound) {
        switch (sound) {
            case flip:
                soundFX.play(flipSound, 1, 1, 0, 0, 1);
                break;
            case gameover:
                soundFX.play(gameOverSound, 1, 1, 0, 0, 1);
                break;
            case correct:
                soundFX.play(correctSound, 1, 1, 0, 0, 1);
                break;
            case victory:
                soundFX.play(victorySound, 1, 1, 0, 0, 1);
                break;
            case incorrect:
                soundFX.play(incorrectSound, 1, 1, 0, 0, 1);
                break;
            case shuffle:
                soundFX.play(shuffleSound, 1, 1, 0, 0, 1);
                break;

        }

    }

}
