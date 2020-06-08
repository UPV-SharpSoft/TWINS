package com.sharpsoft.twinsapp;

import androidx.test.core.app.ApplicationProvider;

import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigAudioTest extends TestCase {
    static int musicVolumeValues [] ;

    @BeforeClass
    public static void setUpBeforeClass(){
        musicVolumeValues = new int[]{0, 50, -2, 100, 120};
    }

    @Test
    public void setMusicVolume_isCorrect(){
        for (int valor : musicVolumeValues) {
            boolean res = ConfigSingleton.getInstance().setMusicVolume(valor, ApplicationProvider.getApplicationContext());
            assertEquals(res, valor > 0 && valor < 100);
        }
    }
}


//usando el setMusicVolume del ConfigSingleton →
    // 0 → -
    // 1 → -
    // 0.5 → -
    // 3 → error
    // -2 → error
