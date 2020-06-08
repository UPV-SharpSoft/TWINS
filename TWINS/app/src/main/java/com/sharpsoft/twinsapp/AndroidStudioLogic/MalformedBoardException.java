package com.sharpsoft.twinsapp.AndroidStudioLogic;

public class MalformedBoardException extends RuntimeException {
    private String msg;
    public MalformedBoardException(String msg){
        this.msg = msg;
    }

    public String toString(){
        return "Malformed Board: " + msg;
    }
}
