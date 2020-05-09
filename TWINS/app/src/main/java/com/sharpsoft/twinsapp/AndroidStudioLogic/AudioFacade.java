package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.media.SoundPool;

public class AudioFacade {

    private static final AudioFacade audioFacadeInstance = new AudioFacade();

    private static AudioFacade getInstance(){ return audioFacadeInstance; }

    private Music music = Music.getInstance();

    private Sound sound = Sound.getInstance();

    public void beginAudio(){
        sound.createSoundPool();
        sound.setOnPrepared(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                if(i1 == 0 && i==7) sound.makeSound(Sound.Sounds.shuffle);
            }
        });
    }



}
