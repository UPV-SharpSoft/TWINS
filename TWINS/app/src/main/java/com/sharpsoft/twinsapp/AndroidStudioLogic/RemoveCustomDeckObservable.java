package com.sharpsoft.twinsapp.AndroidStudioLogic;

import java.util.Observable;
import java.util.Observer;

public abstract class RemoveCustomDeckObservable implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        onRemove((String) arg);
    }

    public abstract void onRemove(String deckName);
}
