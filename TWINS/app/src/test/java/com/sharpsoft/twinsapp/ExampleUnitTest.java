package com.sharpsoft.twinsapp;

import com.sharpsoft.twinsapp.AndroidStudioLogic.ILevelBuilder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level1Builder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.LevelDirector;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void level1_isCorrect(){
        ILevelBuilder levelBuilder = new Level1Builder();
        LevelDirector levelDirector = new LevelDirector();
        levelDirector.Construct(levelBuilder);
        Level level;
        level = levelBuilder.getLevel();

        assertEquals(120, level.getTotalTime());
        assertEquals(Level.Type.standard, level.getType());
    }
}