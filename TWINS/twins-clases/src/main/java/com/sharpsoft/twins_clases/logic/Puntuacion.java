package com.sharpsoft.twins_clases.logic;

public class Puntuacion {
    private int numAciertos;
    private int numFallos;

    public Puntuacion(){
        this.numFallos = 0;
        this.numAciertos = 0;
    }

    public void acierto(){
        numAciertos++;
    }
    public void fallo(){
        numFallos++;
    }

    public int getPuntuacion(){
        return (10*numAciertos) - (4*numFallos);
    }
}
