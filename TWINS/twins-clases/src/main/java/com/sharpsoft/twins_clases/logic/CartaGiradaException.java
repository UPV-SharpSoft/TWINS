package com.sharpsoft.twins_clases.logic;

public class CartaGiradaException extends RuntimeException {
    Carta c;

    public CartaGiradaException(Carta carta){
        this.c = c;
    }

    public String toString(){
        return "La carta: " + c.toString() + " ya ha sido girada!";
    }
}
