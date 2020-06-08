package com.sharpsoft.twinsapp;

import android.app.Application;
import android.app.Instrumentation;

import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Music;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.Array;

public class FacadeTest {
    static float musicVolumeValues [] ;
    static float valor, res;


    @BeforeClass
    public static void setUpBeforeClass(){
        musicVolumeValues = new float[]{0, 1, -1, 5, (float) 0.8};
    }

    @Test
    public void setMusicVolume_isCorrect(){
       for(int i= 0; i< musicVolumeValues.length; i++){
           valor = musicVolumeValues[i];
           res = Music.getInstance().setMusicVolume(valor);
           assertEquals(valor, res);
       }

    }
}


//usando el setMusicVolume del ConfigSingleton →
    // 0 → -
    // 1 → -
    // 0.5 → -
    // 3 → error
    // -2 → error
