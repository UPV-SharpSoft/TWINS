package com.sharpsoft.twinsapp.ILogic;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sharpsoft.twinsapp.Juego;
import com.sharpsoft.twinsapp.R;

import java.text.CollationElementIterator;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Formatter;

public class Cronometro {

    private CountDownTimer workingCountDown;
    private Context caller;
    private final DecimalFormat cronoFormatLong = new DecimalFormat("#0.0");
    private long mTimeLeftInMillis = 600000;
    private long mEndTime;
    private MediaPlayer finalTiempoMusic;

    public Cronometro(int tiempo, final TextView crono, Context context){

        this.caller = context;
        finalTiempoMusic = MediaPlayer.create(caller, R.raw.gameover_default);

        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        workingCountDown = new CountDownTimer(tiempo * 1000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished>10000) crono.setText("" + millisUntilFinished / 1000);
                else crono.setText("" + cronoFormatLong.format(millisUntilFinished / 1000.0));

                mTimeLeftInMillis = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                /*ToDo*/
                finalTiempoMusic.start();
                crono.setText(cronoFormatLong.format(0));
            }
        };
    }

    public void start() {
        workingCountDown.start();
    }

    public Long timeLeft(){
        return mEndTime;
    }
    public void pause() {
        workingCountDown.cancel();
    }

}
