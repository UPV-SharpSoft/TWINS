package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class Music {

    //VARIABLES
    private MediaPlayer bgMusic;
    private boolean muted;
    private static float musicVolume = 1;


    //MÉTODOS
    public static float getMusicVolume() { return musicVolume; }

    public void setMusicVolume(float left, float right) {
        if(left > 1) {
            this.musicVolume = 1;
        } else {
            musicVolume = left;
        };

        bgMusic.setVolume(left,right);
    }

    public void startMusic(Context context, int song) {
        bgMusic = MediaPlayer.create(context, song);
        bgMusic.setLooping(true);
        bgMusic.setVolume(100, 100);
        bgMusic.start();
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

    public void resumeMusic() {
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


}
