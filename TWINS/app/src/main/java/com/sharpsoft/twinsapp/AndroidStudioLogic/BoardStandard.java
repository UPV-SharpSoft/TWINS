package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class BoardStandard extends Board {

    public BoardStandard(Dimension dimension, int secsPerTurn, Deck set) {
        super(dimension, secsPerTurn);
        for(int x = 0; x < dimension.width; x++){
            for(int y = 0; y < dimension.height; y++){
                final Card card = new Card(set.getReverse(), set.removeCard());
                card.setDeck(set.getName());
                card.setBoard(this, x, y);
                this.cards[x][y] = card;
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

        return boardLayout;
    }

    @Override
    protected boolean isSameCard(CardInterface c1, CardInterface c2) {
        return c1.sameImage(c2);
    }
}
