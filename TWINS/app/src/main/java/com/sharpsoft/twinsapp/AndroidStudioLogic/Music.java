package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class Music {

    private MediaPlayer bgMusic;
    private float musicVolume = 1;
    private static final Music musicInstance = new Music();
    
    public static Music getInstance(){return musicInstance;}

    public float getMusicVolume() { return musicVolume; }

    public float setMusicVolume(float volume) {
        if(volume > 1) {
            this.musicVolume = 1;
        } else {
            musicVolume = volume;
        }
        return musicVolume;
    }

    public void startMusic(Context context, int song) {
        bgMusic = MediaPlayer.create(context, song);

        bgMusic.setVolume(musicVolume, musicVolume);

        bgMusic.setLooping(true);
        int volume = ConfigSingleton.getInstance().getMusicVolume(context);
        bgMusic.setVolume(volume, volume);
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
                Log.w(Music.class.getName(), String.format("Failed to stop and release media player: %s", e));
            }
        }
    }

    public void resumeMusic() {
        if(bgMusic != null) {
            bgMusic.setVolume(musicVolume, musicVolume);
            bgMusic.start();
        }
    }

    public void pauseMusic() {
        if(bgMusic != null){
            try{
                if(bgMusic.isPlaying()){
                    bgMusic.pause();
                }
            }catch (Exception e){
                Log.w(Music.class.getName(), String.format("Failed to pause media player: %s", e));
            }
        }
    }


}
