package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;

public class Score extends com.sharpsoft.twins_clases.logic.Score {
    private TextView scoreTextView;

    public Score(TextView scoreTextView){
        super();
        this.scoreTextView = scoreTextView;
    }

    private void setScoreTextView(final int score, int leftScore){
        final String StringScore = String.valueOf(score);
        String StringLeftScore = "";
        if(leftScore > 0){
            StringLeftScore = "+" + leftScore;
            scoreTextView.setTextColor(Color.BLACK);
        }else if (leftScore < 0){
            StringLeftScore = String.valueOf(leftScore);
            scoreTextView.setTextColor(Color.RED);
        }else{
            if (score < 0) {
                scoreTextView.setTextColor(Color.RED);
            }else{
                scoreTextView.setTextColor(Color.BLACK);
            }
        }

        final String finalStringLeftScore = StringLeftScore;
        ((Activity)scoreTextView.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTextView.setText(StringScore + "  " + finalStringLeftScore);
            }
        });
    }

    private void auxSleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void animAux(final boolean firstTime){
        Thread t = new Thread(){
            public void run(){
                int textViewCurrentScore = Integer.parseInt(String.valueOf(scoreTextView.getText()).split(" ")[0]);
                int score = getScore();
                if(textViewCurrentScore != score){
                    int diff = score - textViewCurrentScore;

                    if(firstTime){
                        setScoreTextView(textViewCurrentScore, diff);
                        auxSleep(500);
                    }

                    if(diff < 0){
                        diff++;
                        textViewCurrentScore--;
                    }else{
                        diff--;
                        textViewCurrentScore++;
                    }

                    setScoreTextView(textViewCurrentScore, diff);

                    auxSleep(50);
                    animAux(false);
                }
            }
        };
        t.start();
    }

    private void anim(){
        animAux(true);
    }

    public void correct(){
        super.correct();
        anim();
    }

    public void fail(){
        super.fail();
        anim();
    }

    public void missedTurn(){
        super.missedTurn();
        anim();
    }
}
