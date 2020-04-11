package com.sharpsoft.twins_clases.logic;

public interface Carta {

    boolean mismaImagen(Carta c);
    void girar();
    boolean estaBocaArriba();
    void setTablero(Tablero tablero, int x, int y);
}
