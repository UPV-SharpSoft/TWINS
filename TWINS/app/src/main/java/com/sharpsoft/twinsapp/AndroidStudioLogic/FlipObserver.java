package com.sharpsoft.twinsapp.AndroidStudioLogic;

import java.util.Observable;
import java.util.Observer;

public abstract class FlipObserver implements Observer {
    public enum On{
        Flip, success, failure
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o == On.Flip){
            onFlip();
        }else if(o == On.success){
            onSuccess();
        }else if(o == On.failure){
            onFailure();
        }
    }

    public abstract void onFlip();
    public abstract void onSuccess();
    public abstract void onFailure();
}

