package com.sharpsoft.twinsapp;

import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigAudioTest extends TestCase {
    static int musicVolumeValues;

    @BeforeClass
    public static void setUpBeforeClass(){

    }

    @Test
    public void setMusicVolume_isCorrect(){
       // assertEquals(0, );
    }
}


//usando el setMusicVolume del ConfigSingleton →
    // 0 → -
    // 1 → -
    // 0.5 → -
    // 3 → error
    // -2 → error
