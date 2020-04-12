package com.sharpsoft.twins_clases.logic;

import java.util.Observer;

public abstract class FlipObserver implements Observer {
    public enum On{
        Flip, success, failure
    }
}
