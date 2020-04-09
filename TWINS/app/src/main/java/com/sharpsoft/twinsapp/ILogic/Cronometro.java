package com.sharpsoft.twinsapp.ILogic;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.CollationElementIterator;

public class Cronometro {

    private TextView textViewChrono;
    private TextView textViewFinish;

    public Cronometro(){}

    public void countDown() {

        CountDownTimer workingCountDown = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewChrono.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                textViewFinish.setText("TIME OUT!");
            }
        }.start();

    }
}
