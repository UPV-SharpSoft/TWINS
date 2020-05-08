package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sharpsoft.twins_clases.logic.Board;
import com.sharpsoft.twinsapp.R;

import java.io.Serializable;

public class Card implements com.sharpsoft.twins_clases.logic.Card {
    private Bitmap bitmapReverse, bitmapCard;
    private ImageView imageView;
    private View layout;
    private boolean faceUp; //bocaArriba

    private Board board;
    private int x, y;

    public Card(Bitmap bitmapReverse, Bitmap bitmapCard){
        this.bitmapCard = bitmapCard;
        this.bitmapReverse = bitmapReverse;
        faceUp = false;
    }

    @Override
    public void setBoard(Board board, int x, int y){
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public View getCardView(Context ctx, ViewGroup parent){

            LayoutInflater inflater = LayoutInflater.from(ctx);
            layout = inflater.inflate(R.layout.carta, parent, false);
            imageView = layout.findViewById(R.id.cartaImageView);

            imageView.setImageBitmap(bitmapReverse);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(board != null){
                        board.turn(x, y);
                    }
                }
            });


        return layout;
    }

    @Override
    public boolean sameImage(com.sharpsoft.twins_clases.logic.Card c) {
        if(!(c instanceof Card)) throw new RuntimeException("No son mismo tipo");
        Card o = (Card) c;

        return this.bitmapCard.sameAs(o.bitmapCard);
    }

    @Override
    public void turn() {       //Faltaria animar?
        faceUp = !faceUp;

        final Bitmap b = faceUp ? bitmapCard : bitmapReverse;
        if(layout != null){
            ((Activity) layout.getContext()).runOnUiThread(new Thread(){
                public void run(){

                    layout.animate()
                            .rotationY(180)
                            .setDuration(300)
                            .start();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(b);
                        }
                    }, 200);

                    layout.animate()
                            .rotationY(180)
                            .setDuration(300)
                            .start();
                }
            });
        }
    }

    @Override
    public boolean isFacedUp() {
        return faceUp;
    }
}
