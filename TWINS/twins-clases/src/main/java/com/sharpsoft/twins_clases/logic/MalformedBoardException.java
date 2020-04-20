package com.sharpsoft.twins_clases.logic;

public class MalformedBoardException extends RuntimeException {
    private String msg;
    public MalformedBoardException(String msg){
        this.msg = msg;
    }

    public String toString(){
        return "Malformed Board: " + msg;
    }
}
