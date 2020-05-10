package com.sharpsoft.twinsapp.AndroidStudioLogic;

import com.sharpsoft.twins_clases.logic.Dimension;

import java.util.Random;

public class LevelRandomBuilder extends ILevelBuilder {
    private Random random;

    public LevelRandomBuilder(){
        this.random = new Random();
    }

    @Override
    public void totalTime() {
        int width = level.getDimension().width;
        int height = level.getDimension().height;
        int time = (int) Math.floor(width*height*2.22)*1000;
        level.setTotalTime(time);
    }

    @Override
    public void timePerTurn() {
        level.setTimePerTurn(5*1000);
    }

    @Override
    public void numPairs() {
        int numCartas = random.nextInt(((level.getDimension().getTotal()+1)/2)-2) + 2;
        level.setNumPairs(numCartas);
    }

    @Override
    public void dimension() {
        int width = random.nextInt(5-2) + 2;
        int height = random.nextInt(7-3) + 3;
        while((width * height) % 2 == 1){
            width = random.nextInt(5-2) + 2;
            height = random.nextInt(7-3) + 3;
        }
        level.setDimension(new Dimension(width, height));
    }
}
