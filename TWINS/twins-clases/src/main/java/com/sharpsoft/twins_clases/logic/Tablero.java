package com.sharpsoft.twins_clases.logic;

import java.util.Stack;

public class Tablero {
    private Carta[][] cartas;
    private int width;
    private int height;
    private Stack<Carta> cartasGiradas;

    public Tablero(int width, int height){
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
        if(cartasGiradas.contains(c)) throw new CartaGiradaException(c); //Lanzar exception si la carta esta sido girada

        c.girar();
        cartasGiradas.push(c);
        if(cartasGiradas.size() % 2 == 0){  //Se ha girado la segunda carta
            Carta c1 = cartasGiradas.pop();
            Carta c2 = cartasGiradas.pop();
            if(c1.mismaImagen(c2)){ //Coinciden
                cartasGiradas.push(c1);
                cartasGiradas.push(c2);
            }else{  //No coinciden
                c1.girar();
                c2.girar();
            }
        }
    }
}
