package com.sharpsoft.twins_clases.logic;

import java.util.Stack;

public class Tablero {
    protected Carta[][] cartas;
    private int width;
    private int height;
    private Stack<Carta> cartasGiradas;
    private boolean estaEsperando;

    public Tablero(int width, int height){
        if( (width*height) % 2 != 0) throw new MalformedTableroException("Las cartas son impares");

        this.height = height; this.width = width;
        cartas = new Carta[width][height];

        cartasGiradas = new Stack<>();

        estaEsperando = false;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
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
        //if(cartasGiradas.contains(c)) throw new CartaGiradaException(c); //Lanzar exception si la carta esta sido girada
        if(estaEsperando) return;

        c.girar();
        cartasGiradas.push(c);
        if(cartasGiradas.size() % 2 == 0){  //Se ha girado la segunda carta
            Carta c1 = cartasGiradas.pop();
            Carta c2 = cartasGiradas.pop();
            if(c1.mismaImagen(c2)){ //Coinciden
                cartasGiradas.push(c1);
                cartasGiradas.push(c2);
            }else{  //No coinciden
               girarCartas(c1, c2);
            }
        }
    }

    public Carta getCarta(int x, int y){
        return cartas[x][y];
    }
}
