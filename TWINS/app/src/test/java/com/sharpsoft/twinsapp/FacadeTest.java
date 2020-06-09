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
        musicVolumeValues = new float[]{0, 1, -1, 5, 0.8f};
    }

    @Test
    public void setMusicVolume_isCorrect(){
       for(int i= 0; i < musicVolumeValues.length; i++){
           valor = musicVolumeValues[i];
           res = Music.getInstance().setMusicVolume(valor);
           if(valor > 1){
               assertEquals(1, res, 0.0);
           }else if (valor < 0){
               assertEquals(0, res, 0.0);
           }else{
               assertEquals(valor, res, 0.0);
           }
       }

    }
}


//usando el setMusicVolume del ConfigSingleton →
    // 0 → -
    // 1 → -
    // 0.5 → -
    // 3 → error
    // -2 → error
