package com.sharpsoft.twins_clases.logic;

import java.util.Stack;

public class Tablero {
    protected Carta[][] cartas;
    private int width;
    private int height;
    private Stack<Carta> cartasGiradas;

    private boolean estaGirando;

    public Tablero(int width, int height){
        if( (width*height) % 2 != 0) throw new MalformedTableroException("Las cartas son impares");

        this.height = height; this.width = width;
        cartas = new Carta[width][height];

        cartasGiradas = new Stack<>();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void girar(int x, int y){
        Carta c = cartas[x][y];
        if(cartasGiradas.contains(c)) return;
        if(estaGirando) return;

        c.girar();
        estaGirando = true;
        cartasGiradas.push(c);
        if(cartasGiradas.size() % 2 == 0){  //Se ha girado la segunda carta
            Carta c1 = cartasGiradas.pop();
            Carta c2 = cartasGiradas.pop();
            if(c1.mismaImagen(c2)){ //Coinciden
                cartasGiradas.push(c1);
                cartasGiradas.push(c2);
                estaGirando = false;
            }else{  //No coinciden
                girarDentroDe(c1, c2, 1000);
            }
        }else{
            estaGirando = false;
        }
    }

    private void girarDentroDe(final Carta c1, final Carta c2, final long milis){
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(milis);
                    c1.girar();
                    c2.girar();
                    estaGirando = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public Carta getCarta(int x, int y){
        return cartas[x][y];
    }
}
