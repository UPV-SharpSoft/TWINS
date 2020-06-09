package com.sharpsoft.twinsapp;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class FinalScoreTest {
    static int scoreValues [], expected [], actual[];
    static long timeLeft [];


    @BeforeClass
    public static void setUpBeforeClass(){
        scoreValues = new int[]{0, 1, -1, 5};
        timeLeft = new long[]{0, 60000, -23000, 500000};
        expected = new int[]{0, 61, -24, 505};
        actual = new int[4];
    }

    @Test
    public void calcScore_isCorrect(){
       for(int i= 0; i< scoreValues.length; i++){
           int score = scoreValues[i];
           long time = timeLeft[i];
           actual[i] = GameOverActivity.calcScore(score, time);
       }
       assertEquals(Arrays.equals(expected, actual), true);
    }
}


