package com.sharpsoft.twinsapp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import java.net.ContentHandler;

public class Audio {
    private static final Audio audioInstance = new Audio();
    private MediaPlayer bgMusic;
    private SoundPool.Builder soundFXBuilder;
    private SoundPool soundFX;

    private int flipSound;
    private int gameOverSound;
    private int correctSound;
    private int victorySound;
    private int incorrectSound;
    private int shuffleSound;

    public enum Sounds {flip, gameover, correct, victory, incorrect, shuffle}

    public static Audio getInstance() {
        return audioInstance;
    }

    public void startMusic(Context context, int song) {
        bgMusic = MediaPlayer.create(context, song);
        bgMusic.setLooping(true);
        bgMusic.setVolume(50, 50);
        bgMusic.start();
    }

    public void resumeMusic(Context context) {
        bgMusic.start();
    }

    public void pauseMusic(Context context) {
        bgMusic.pause();
    }

    public void stopMusic(Context context) {
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
                    .setMaxStreams(3)
                    .build();
        } else {
            soundFX = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }

        //Precarga de sonidos default
        flipSound = soundFX.load(context, R.raw.flip_default, 1);
        gameOverSound = soundFX.load(context, R.raw.gameover_default, 1);
        correctSound = soundFX.load(context, R.raw.correct_default, 1);
        victorySound = soundFX.load(context, R.raw.victoria_default, 1);
        incorrectSound = soundFX.load(context, R.raw.incorrect_default, 1);
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
