package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Random;

public class Baraja {
    private List<Bitmap> cartas;
    private Bitmap reverso;

    public Baraja(List<Bitmap> cartas, Bitmap reverso){
        this.cartas = cartas;
        this.reverso = reverso;
    }

    public boolean quedanCartas(){
        return !cartas.isEmpty();
    }

    public Bitmap sacarCarta(){
        int i = new Random().nextInt(cartas.size());
        return cartas.remove(i);
    }

    public Bitmap getReverso(){
        return reverso;
    }
}
