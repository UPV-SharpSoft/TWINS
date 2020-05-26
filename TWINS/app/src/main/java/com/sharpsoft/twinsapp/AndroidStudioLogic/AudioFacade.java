package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.media.SoundPool;

public class AudioFacade {

    private static final AudioFacade audioFacadeInstance = new AudioFacade();
    private Music music = Music.getInstance();
    private Sound sound = Sound.getInstance();
    private float prevMusicVolume;
    private float prevSoundVolume;

    public static AudioFacade getInstance(){ return audioFacadeInstance; }

    public boolean isMutedAll() { return getSoundVolume()==0 && getMusicVolume()==0; }

    public void muteAll(){
        prevSoundVolume = sound.getSoundVolume();
        prevMusicVolume = music.getMusicVolume();
        sound.setSoundVolume(0);
        music.setMusicVolume(0);
    }

    public void unMuteAll(){
        sound.setSoundVolume(prevSoundVolume);
        music.setMusicVolume(prevMusicVolume);
    }

    public void setMusicGame(Context ctx, int song){
        audioFacadeInstance.stopMusic();
        audioFacadeInstance.startMusic(ctx,song);
        audioFacadeInstance.setMusicVolume(audioFacadeInstance.getMusicVolume());
    }

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

    public void stopSound(int streamID){
        sound.stopSound(streamID);
    }

    public void makeSound(Sound.Sounds soundID){
        sound.makeSound(soundID);
    }

    public float getSoundVolume() {
        return sound.getSoundVolume();
    }

    public void setSoundVolume(float soundVolume) {sound.setSoundVolume(soundVolume);}

    public void initializeSound(final Context ctx){
        sound.createSoundPool(ctx);
        sound.setOnPrepared(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                //setMusicVolume(ConfigSingleton.getInstance().getMusicVolume(ctx)/100f);
                setSoundVolume(ConfigSingleton.getInstance().getFXVolume(ctx)/100f);
                if(i1 == 0 && i==7) sound.makeSound(Sound.Sounds.shuffle);
            }
        });
    }

}
