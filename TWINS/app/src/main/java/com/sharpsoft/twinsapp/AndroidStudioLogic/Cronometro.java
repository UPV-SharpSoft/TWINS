package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.sharpsoft.twinsapp.Audio;

import java.text.DecimalFormat;

public class Cronometro {

    private CountDownTimer workingCountDown;
    private Context caller;
    private final DecimalFormat cronoFormatLong = new DecimalFormat("#0.0");
    private long mTimeLeftInMillis = 600000;
    private long mEndTime;
    private Audio audioInstance = Audio.getInstance();

    public Cronometro(int tiempo, final TextView crono, Context context){

        this.caller = context;

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
                audioInstance.stopMusic(caller);
                audioInstance.makeSound(Audio.Sounds.gameover);
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
