package com.sharpsoft.twins_clases.logic;

import java.util.Observable;
import java.util.Stack;

public class Tablero extends Observable {
    protected Carta[][] cartas;
    private Dimension dimension;
    private Stack<Carta> cartasGiradas;
    private boolean estaEsperando;

    public Tablero(Dimension dimension){
        this.dimension = dimension;

        int width = dimension.width;
        int height = dimension.height;

        if( ((width*height) % 2 != 0) || (width * height == 0)) throw new MalformedTableroException("Las cartas son impares");

        cartas = new Carta[width][height];

        cartasGiradas = new Stack<>();

        estaEsperando = false;

    }

    public Dimension getDimension() {
        return dimension;
    }

    private void girarCartas(final Carta c1, final Carta c2){
        estaEsperando = true;
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                c1.girar();
                c2.girar();
                estaEsperando = false;
            }
        }.start();
    }

    public void girar(int x, int y){
        Carta c = cartas[x][y];
        if(c.estaBocaArriba()) return; //salir si la carta ya ha sido girada
        if(estaEsperando) return;

        estaEsperando = true;

        c.girar();

        setChanged();
        notifyObservers(FlipObserver.On.Flip);

        cartasGiradas.push(c);
        if(cartasGiradas.size() % 2 == 0){  //Se ha girado la segunda carta
            Carta c1 = cartasGiradas.pop();
            Carta c2 = cartasGiradas.pop();
            if(c1.mismaImagen(c2)){ //Coinciden
                cartasGiradas.push(c1);
                cartasGiradas.push(c2);

                setChanged();
                notifyObservers(FlipObserver.On.success);

                estaEsperando = false;
            }else{  //No coinciden
               girarCartas(c1, c2);

                setChanged();
               notifyObservers(FlipObserver.On.failure);
            }
        }else{
            estaEsperando = false;
        }
    }

    public Carta getCarta(int x, int y){
        return cartas[x][y];
    }

    public boolean isComplete(){
        return cartasGiradas.size() == dimension.getTotal();
    }

}
