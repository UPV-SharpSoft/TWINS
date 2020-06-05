package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twins_clases.logic.FlipObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardBySet extends BoardStandard {
    private TextView suggestedTextView;
    protected com.sharpsoft.twins_clases.logic.Card suggestedCard;
    private Deck set2;


    public BoardBySet(Dimension dimension, int segundosPorTurno, Deck set, Deck set2) {
        super(new Dimension(0,0), segundosPorTurno, null);

        super.addObserver(new FlipObserver() {
            @Override
            public void onFlip() {

            }

            @Override
            public void onSuccess() {
                if(!BoardBySet.super.isComplete()) setSuggestedSet();
            }

            @Override
            public void onFailure() {

            }
        });

        super.dimension = new Dimension(dimension.width, dimension.height * 2);
        super.cards = new com.sharpsoft.twins_clases.logic.Card[dimension.width][dimension.height * 2];
        List<Bitmap> allBitmaps = set.getAllBitmaps();
        allBitmaps.addAll(set2.getAllBitmaps());
        int firstSecondDeck = allBitmaps.size()/2;

        Random random = new Random();

        for(int x = 0; x < super.dimension.width; x++){
            for(int y = 0; y < super.dimension.height; y++){
                int rand = random.nextInt(allBitmaps.size());
                Bitmap b = allBitmaps.remove(rand);
                final Card card = new Card(set.getReverse(), b);
                card.setDeck(rand < firstSecondDeck ? set.getName(): set2.getName());
                card.setBoard(this, x, y);
                this.cards[x][y] = card;

                if(rand < firstSecondDeck) firstSecondDeck--;
            }
        }
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

        suggestedTextView = new TextView(ctx);
        suggestedTextView.setText("asdasdasdasdasdasdasdasdasdasdasdasdasdasdasd");
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        suggestedTextView.setLayoutParams(tvParams);
        suggestedTextView.setTextColor(Color.BLACK);
        boardLayout.addView(suggestedTextView);

        setSuggestedSet();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        boardLayout.setLayoutParams(layoutParams);

        return boardLayout;
    }

    @Override
    protected boolean isSameCard(com.sharpsoft.twins_clases.logic.Card c1, com.sharpsoft.twins_clases.logic.Card c2) {
        return c1.sameImage(c2) && c1.getDeck().equals(suggestedTextView.getText()) && c2.getDeck().equals(suggestedTextView.getText());
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
