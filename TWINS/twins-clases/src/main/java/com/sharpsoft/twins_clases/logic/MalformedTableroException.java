package com.sharpsoft.twins_clases.logic;

public class MalformedTableroException extends RuntimeException {
    private String msg;
    public MalformedTableroException(String msg){
        this.msg = msg;
    }

    public String toString(){
        return "Malformed Tablero: " + msg;
    }
}
