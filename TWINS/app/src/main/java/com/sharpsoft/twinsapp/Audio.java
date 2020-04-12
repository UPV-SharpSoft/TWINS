package com.sharpsoft.twinsapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.net.ContentHandler;

public class Audio {
    private static final Audio audioInstance = new Audio();
    private MediaPlayer bgMusic;
    private SoundPool soundFX;

    public static Audio getInstance(){
        return audioInstance;
    }

    public void startMusic(Context context, int song){
        bgMusic = MediaPlayer.create(context, song);
        bgMusic.setLooping(true);
        bgMusic.setVolume(50, 50);
        bgMusic.start();
    }

    public void resumeMusic(Context context){
        bgMusic.start();
    }

    public void pauseMusic(Context context){
        bgMusic.pause();
    }


}
