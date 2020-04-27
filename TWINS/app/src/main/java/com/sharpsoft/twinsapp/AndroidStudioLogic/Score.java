package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.app.Activity;
import android.widget.TextView;

public class Score extends com.sharpsoft.twins_clases.logic.Score {
    private TextView scoreTextView;

    public Score(TextView scoreTextView){
        super();
        this.scoreTextView = scoreTextView;
    }

    public void correct(){
        super.correct();
        scoreTextView.setText(String.valueOf(getScore()));
    }

    public void fail(){
        super.fail();
        scoreTextView.setText(String.valueOf(getScore()));
    }

    public void missedTurn(){
        super.missedTurn();
        ((Activity)scoreTextView.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTextView.setText(String.valueOf(getScore()));
            }
        });
    }
}
