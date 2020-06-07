package com.sharpsoft.twinsapp;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ILevelBuilder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level1Builder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level2Builder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level3Builder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level4Builder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level5Builder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.LevelDirector;

import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BuilderTest {

    static ILevelBuilder level1Builder;
    static ILevelBuilder level2Builder = new Level2Builder();
    static ILevelBuilder level3Builder = new Level3Builder();
    static ILevelBuilder level4Builder = new Level4Builder();
    static ILevelBuilder level5Builder = new Level5Builder();

    static Level level1, level2, level3, level4, level5;

    static LevelDirector levelDirector = new LevelDirector();

    @BeforeClass
    public static void setUpBeforeClass(){
        level1Builder = new Level1Builder();
        level2Builder = new Level2Builder();
        level3Builder = new Level3Builder();
        level4Builder = new Level4Builder();
        level5Builder = new Level5Builder();

        levelDirector.Construct(level1Builder);
        levelDirector.Construct(level2Builder);
        levelDirector.Construct(level3Builder);
        levelDirector.Construct(level4Builder);
        levelDirector.Construct(level5Builder);

        level1 = level1Builder.getLevel();
        level2 = level2Builder.getLevel();
        level3 = level3Builder.getLevel();
        level4 = level4Builder.getLevel();
        level5 = level5Builder.getLevel();
    }


    @Test
    public void level1_isCorrect(){
        //Board: 5x4
        //Different cards: 4
        //Game mode: Standard
        //Time: 120s


        assertEquals(120000, level1.getTotalTime());
        assertEquals(5000, level1.getTimePerTurn());
        assertEquals(4, level1.getNumPairs());
        assertEquals(new Dimension(4,5).getHeight(), level1.getDimension().getHeight());
        assertEquals(new Dimension(4,5).getWidth(), level1.getDimension().getWidth());
        assertEquals(Level.Type.standard, level1.getType());
    }

    @Test
    public void level2_isCorrect(){
        //Board: 5x4
        //Different cards: 4
        //Game mode: Standard
        //Time: 100s

        assertEquals(100000, level2.getTotalTime());
        assertEquals(5000, level2.getTimePerTurn());
        assertEquals(4, level2.getNumPairs());
        assertEquals(new Dimension(4,5).getHeight(), level2.getDimension().getHeight());
        assertEquals(new Dimension(4,5).getWidth(), level2.getDimension().getWidth());
        assertEquals(Level.Type.standard, level2.getType());
    }

    @Test
    public void level3_isCorrect(){
        //Board: 5x4
        //Different cards: 6
        //Game mode: Standard
        //Time: 80s

        assertEquals(80000, level3.getTotalTime());
        assertEquals(5000, level3.getTimePerTurn());
        assertEquals(6, level3.getNumPairs());
        assertEquals(new Dimension(4,5).getHeight(), level3.getDimension().getHeight());
        assertEquals(new Dimension(4,5).getWidth(), level3.getDimension().getWidth());
        assertEquals(Level.Type.standard, level3.getType());
    }

    @Test
    public void level4_isCorrect(){
        //Board: 6x4
        //Different cards: 6
        //Game mode: Standard
        //Time: 60s

        assertEquals(60000, level4.getTotalTime());
        assertEquals(5000, level4.getTimePerTurn());
        assertEquals(6, level4.getNumPairs());
        assertEquals(new Dimension(4,6).getHeight(), level4.getDimension().getHeight());
        assertEquals(new Dimension(4,6).getWidth(), level4.getDimension().getWidth());
        assertEquals(Level.Type.standard, level4.getType());
    }

    @Test
    public void level5_isCorrect(){
        //Board: 6x4
        //Different cards: 8
        //Game mode: Standard
        //Time: 60s

        assertEquals(60000, level5.getTotalTime());
        assertEquals(5000, level5.getTimePerTurn());
        assertEquals(8, level5.getNumPairs());
        assertEquals(new Dimension(4,6).getHeight(), level5.getDimension().getHeight());
        assertEquals(new Dimension(4,6).getWidth(), level5.getDimension().getWidth());
        assertEquals(Level.Type.standard, level5.getType());
    }
}