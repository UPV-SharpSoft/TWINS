package com.sharpsoft.twins_clases.logic;

public class Dimension {
    public int width;
    public int height;

    public Dimension(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getTotal(){
        return width*height;
    }
}
