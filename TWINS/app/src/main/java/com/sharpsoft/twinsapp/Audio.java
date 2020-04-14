package com.sharpsoft.twinsapp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

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

    private static float soundVolume = 1;
    private static int soundPoolProgress = 100;

    private static int mPlayerProgress = 100;

    public static float getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }

    public static int getSoundPoolProgress() {
        return soundPoolProgress;
    }

    public void setSoundPoolProgress(int soundPoolProgress) {
        this.soundPoolProgress = soundPoolProgress;
    }

    public enum Sounds {flip, gameover, correct, victory, incorrect, shuffle}

    public static Audio getInstance() {
        return audioInstance;
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

    public void setMusicSeekbarProgress(int progress){
        mPlayerProgress = progress;
    }

    public int getMusicSeekbarProgress(){
        return mPlayerProgress;
    }

    public void setOnPrepared(SoundPool.OnLoadCompleteListener listener){
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

    public void pauseMusic() {
        if(bgMusic != null){
            try{
                if(bgMusic.isPlaying()){
                    bgMusic.pause();
                }
            }catch (Exception e){
                Log.w(Audio.class.getName(), String.format("Failed to pause media player: %s", e));
            }
        }
    }

    public void stopMusic(){
        if(bgMusic != null){
            try{
                if(bgMusic.isPlaying()){
                    bgMusic.stop();
                    bgMusic.release();
                }
            }catch (Exception e){
                Log.w(Audio.class.getName(), String.format("Failed to stop and release media player: %s", e));
            }
        }
    }
    public void setMusicVolume(float left, float right) {bgMusic.setVolume(left,right);}



    /**
     * Próximamente, cuando hagamos más packs de Sonidos, habrá que precargar los sonidos
     * ¿en un método aparte?
     */



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
                soundFX.play(shuffleSound, soundVolume, soundVolume, 0, 0, 1);
                break;

        }

    }

}
