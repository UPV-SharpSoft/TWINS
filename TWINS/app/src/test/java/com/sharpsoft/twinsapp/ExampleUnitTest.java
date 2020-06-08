package com.sharpsoft.twinsapp;

import com.sharpsoft.twinsapp.AndroidStudioLogic.Dimension;
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
        //Board: 5x4
        //Different cards: 4
        //Game mode: Standard
        //Time: 120s

        ILevelBuilder levelBuilder = new Level1Builder();
        LevelDirector levelDirector = new LevelDirector();
        levelDirector.Construct(levelBuilder);
        Level level;
        level = levelBuilder.getLevel();

        assertEquals(120, level.getTotalTime());
        assertEquals(Level.Type.standard, level.getType());
        assertEquals(4, level.getNumPairs());
        assertEquals(new Dimension(5,4), level.getDimension());
    }
    public void level5_isCorrect(){
        //Board: 6x4
        //Different cards: 8
        //Game mode: Standard
        //Time: 60s

        ILevelBuilder levelBuilder = new Level1Builder();
        LevelDirector levelDirector = new LevelDirector();
        levelDirector.Construct(levelBuilder);
        Level level;
        level = levelBuilder.getLevel();

        assertEquals(60, level.getTotalTime());
        assertEquals(Level.Type.standard, level.getType());
        assertEquals(8, level.getNumPairs());
        assertEquals(new Dimension(6,4), level.getDimension());
    }
}