package com.sharpsoft.twinsapp;

import android.app.Application;
import android.app.Instrumentation;

import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import androidx.test.core.app.ApplicationProvider;
import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;

public class ConfigAudioTest extends TestCase {
    static int musicVolumeValues [] ;



    @BeforeClass
    public static void setUpBeforeClass(){
        musicVolumeValues = new int[]{0, 50, -2, 100, 120};
    }

    @Test
    public void setMusicVolume_isCorrect(){
       for(int i= 0; i< musicVolumeValues.length; i++){
           int valor = musicVolumeValues[i];
           boolean res = ConfigSingleton.getInstance().setMusicVolume(valor, ApplicationProvider);
           assert(0, );
       }

    }
}


//usando el setMusicVolume del ConfigSingleton →
    // 0 → -
    // 1 → -
    // 0.5 → -
    // 3 → error
    // -2 → error
