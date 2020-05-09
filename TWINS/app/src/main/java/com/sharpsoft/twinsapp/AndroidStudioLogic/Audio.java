package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import com.sharpsoft.twinsapp.R;

public class Audio {
    private static final Audio audioInstance = new Audio();

    private boolean muted;

    public static Audio getInstance() { return audioInstance; }

    public boolean isMuted() { return muted; }
    public void setMuted(boolean muted) { this.muted = muted; }
    
}
