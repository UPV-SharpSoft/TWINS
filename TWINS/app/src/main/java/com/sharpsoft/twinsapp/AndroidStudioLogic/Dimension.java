package com.sharpsoft.twinsapp.AndroidStudioLogic;

import java.io.Serializable;

public class Dimension implements Serializable{
    public int width;
    public int height;

    public Dimension(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getTotal(){return width*height;}

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
