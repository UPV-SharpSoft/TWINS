package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckFactory;

import java.util.ArrayList;
import java.util.List;

public class NewDeckActivity extends AppCompatActivity {

    private Button buttonLoadImage;
    private ImageView imageViewDeck;
    private static int RESULT_LOAD_IMAGE = 1;
    private Spinner spinnerDeck;
    private ImageView previousCard;
    private ImageView nextCard;

    private List<Bitmap> selectedDeck;
    private  int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_deck);

        buttonLoadImage = findViewById(R.id.buttonLoadImage);
        spinnerDeck = findViewById(R.id.spinnerDeck);
        imageViewDeck = findViewById(R.id.imageViewDeck);
        previousCard = findViewById(R.id.previousCard);
        nextCard = findViewById(R.id.nextCard);

        selectDeck();

        moveCard();

    }

    private List<String> getAllDecks(){
        List<String> res = new ArrayList<>();
        Dimension d = new Dimension(2, 1);
        for(DeckFactory.Decks decks : DeckFactory.Decks.values()){
            Deck deck = DeckFactory.getDeck(decks, d , this);
            res.add(deck.getName());
        }
        return res;
    }

    private void selectDeck(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(getAllDecks());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeck.setAdapter(arrayAdapter);

        spinnerDeck.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDeck = DeckFactory.getAllImages(DeckFactory.Decks.values()[i], NewDeck.this);
                imageViewDeck.setImageBitmap(selectedDeck.get(0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void moveCard() {

        previousCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count > -1) imageViewDeck.setImageBitmap(selectedDeck.get(count--));
            }
        });

        nextCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count < selectedDeck.size()) imageViewDeck.setImageBitmap(selectedDeck.get(count++));
            }
        });
    }
}
