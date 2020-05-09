package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.media.SoundPool;

public class AudioFacade {

    private static final AudioFacade audioFacadeInstance = new AudioFacade();

    public static AudioFacade getInstance(){ return audioFacadeInstance; }

    private Music music = Music.getInstance();

    private Sound sound = Sound.getInstance();


    public void resumeMusic(){
        music.resumeMusic();
    }

    public void pauseMusic(){
        music.pauseMusic();
    }

    public void startMusic(Context ctx, int song){
        music.startMusic(ctx, song);
    }

    public void stopMusic(){
        music.stopMusic();
    }

    public void setMusicVolume(float volume){
        music.setMusicVolume(volume);
    }

    public float getMusicVolume(){
        return music.getMusicVolume();
    }

    public void initializeAudio(Context ctx){
        sound.createSoundPool(ctx);
        sound.setOnPrepared(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                if(i1 == 0 && i==7) sound.makeSound(Sound.Sounds.shuffle);
            }
        });
    }

    public void stopSound(int streamID){
        sound.stopSound(streamID);
    }

    public void makeSound(Sound.Sounds soundID){
        sound.makeSound(soundID);
    }

}
