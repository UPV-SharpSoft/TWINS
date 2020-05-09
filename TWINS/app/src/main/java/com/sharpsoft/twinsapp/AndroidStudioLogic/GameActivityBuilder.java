package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.content.Intent;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.GameActivity;
import com.sharpsoft.twinsapp.R;

public class GameActivityBuilder extends IGameActivityBuilder {

    Intent i = new Intent(ctx, GameActivity.class);

    public GameActivityBuilder() {super.game = new GameActivity();}

    public void totalTime(){
        i.putExtra("time", 60*1000);
    }

    public void timePerTurn(){

    }

    public void deck(){

    }

    public void dimension(){

    }

    public void music(){

    }

}
