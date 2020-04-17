package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.widget.TextView;

public class Puntuacion extends com.sharpsoft.twins_clases.logic.Puntuacion {
    private TextView puntuacionTextView;

    public Puntuacion(TextView puntuacionTextView){
        super();
        this.puntuacionTextView = puntuacionTextView;
    }

    public void acierto(){
        super.acierto();
        puntuacionTextView.setText(String.valueOf(getPuntuacion()));
    }

    public void fallo(){
        super.fallo();
        puntuacionTextView.setText(String.valueOf(getPuntuacion()));
    }
}
