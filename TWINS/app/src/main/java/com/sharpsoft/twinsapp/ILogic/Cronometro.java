package com.sharpsoft.twinsapp.ILogic;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.CollationElementIterator;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Formatter;

public class Cronometro {

    private CountDownTimer workingCountDown;
    private final DecimalFormat cronoFormatShort = new DecimalFormat("#0");
    private final DecimalFormat cronoFormatLong = new DecimalFormat("#0.0");

    public Cronometro(int tiempo, final TextView crono){

        workingCountDown = new CountDownTimer(tiempo * 1000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished>10000) crono.setText("" + millisUntilFinished / 1000);
                else crono.setText("" + cronoFormatLong.format(millisUntilFinished / 1000.0));
            }

            @Override
            public void onFinish() {
                /*ToDo*/
            }
        };
    }

    public void start() {
        workingCountDown.start();
    }
}
