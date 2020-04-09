package com.sharpsoft.twinsapp.ILogic;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.CollationElementIterator;
import java.util.Formatter;

public class Cronometro {

    private TextView textViewChrono;
    private TextView textViewFinish;
    private CountDownTimer workingCountDown;
    private Formatter millisCountFormat;

    public Cronometro(int segundos){
        workingCountDown = new CountDownTimer(segundos*1000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished>10000) {
                    textViewChrono.setText("" + millisUntilFinished / 1000);
                } else {
                    textViewChrono.setText("" + millisUntilFinished / 1000.0);
                }
            }

            @Override
            public void onFinish() {
                /* ToDo */
                textViewFinish.setText("TIME OUT!");
            }
        };
    }

    public void start() {
        //Hacer en hilo UI
        new Thread(new Runnable() {
            @Override
            public void run() {
                workingCountDown.start();
            }
        }).start();
    }
}
