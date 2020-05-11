package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twins_clases.logic.FlipObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardBySet extends Board {
    private TextView suggestedTextView;
    protected com.sharpsoft.twins_clases.logic.Card suggestedCard;

    public BoardBySet(Dimension dimension, int segundosPorTurno, Deck set, Deck set2) {
        super(dimension, segundosPorTurno, set);

        super.addObserver(new FlipObserver() {
            @Override
            public void onFlip() {

            }

            @Override
            public void onSuccess() {
                if(BoardBySet.super.isComplete()) setSuggestedSet();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public View getView(Context ctx){
        LinearLayout boardLayout = new LinearLayout(ctx);
        boardLayout.setOrientation(LinearLayout.VERTICAL);
        boardLayout.setGravity(Gravity.CENTER);

        for(int y = 0; y < getDimension().height; y++){
            LinearLayout horizontalLayout = new LinearLayout(ctx);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setGravity(Gravity.CENTER);
            for(int x = 0; x < getDimension().width; x++){
                Card card = (Card) this.getCard(x, y);
                View cardView = card.getCardView(ctx, horizontalLayout);

                ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(cardView.getLayoutParams());
                marginParams.setMargins(5,5,5,5);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(marginParams);
                cardView.setLayoutParams(layoutParams);

                horizontalLayout.addView(cardView);
            }
            boardLayout.addView(horizontalLayout);
        }


        /*suggestedImageView = new ImageView(ctx);
        boardLayout.addView(suggestedImageView);

        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(200,200);
        suggestedImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        suggestedImageView.setLayoutParams(imageViewParams);*/

        setSuggestedSet();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        boardLayout.setLayoutParams(layoutParams);

        return boardLayout;
    }

    @Override
    protected boolean isSameCard(com.sharpsoft.twins_clases.logic.Card c1, com.sharpsoft.twins_clases.logic.Card c2) {
        return c1.sameImage(c2) && c1.sameImage(suggestedCard);
    }

    private List<com.sharpsoft.twins_clases.logic.Card> getDownsideCards(){
        List<com.sharpsoft.twins_clases.logic.Card> res = new ArrayList<>();
        for(com.sharpsoft.twins_clases.logic.Card[] aux : cards){
            for(com.sharpsoft.twins_clases.logic.Card card : aux){
                if(!card.isFacedUp()) res.add(card);
            }
        }
        return res;
    }

    protected void setSuggestedSet(){
        Random r = new Random();
        List<com.sharpsoft.twins_clases.logic.Card> downsideCards = getDownsideCards();
        int randomIndex =  r.nextInt(downsideCards.size());
        suggestedCard = downsideCards.get(randomIndex);

        Card sugCard = (Card) suggestedCard;
        suggestedTextView.setText(sugCard.getDeck());
    }
}
