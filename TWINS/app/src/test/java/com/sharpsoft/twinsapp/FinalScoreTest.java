package com.sharpsoft.twinsapp;

import com.sharpsoft.twinsapp.AndroidStudioLogic.Music;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FinalScoreTest {
    static int scoreValues [];
    static float timeLeft [];
    static float value, res;


    @BeforeClass
    public static void setUpBeforeClass(){
        scoreValues = new int[]{0, 1, -1, 5};
    }

    @Test
    public void calcScore_isCorrect(){
       for(int i= 0; i< scoreValues.length; i++){
            = scoreValues[i];
           res = Music.getInstance().setMusicVolume();
           assertEquals(, res);
       }

    }
}


//usando el setMusicVolume del ConfigSingleton →
    // 0 → -
    // 1 → -
    // 0.5 → -
    // 3 → error
    // -2 → error
