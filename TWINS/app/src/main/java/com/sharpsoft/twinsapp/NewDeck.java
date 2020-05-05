package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckFactory;

import java.util.ArrayList;
import java.util.List;

public class NewDeck extends AppCompatActivity {

    private Button buttonLoadImage;
    private ImageView imageViewDeck;
    private static int RESULT_LOAD_IMAGE = 1;

    private Spinner spinnerDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_deck);

        Button buttonLoadImage = findViewById(R.id.buttonLoadImage);
        Spinner spinnerDeck = findViewById(R.id.spinnerDeck);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(getAllDecks());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeck.setAdapter(arrayAdapter);

        spinnerDeck.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                    
            }
        });

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
}
