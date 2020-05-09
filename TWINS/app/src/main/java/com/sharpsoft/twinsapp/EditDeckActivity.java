package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckFactory;

import java.util.ArrayList;
import java.util.List;

public class EditDeckActivity extends AppCompatActivity {

    private ListView listView;
    private List<Deck> deck;
    private Button buttonCreateDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);

        listView = findViewById(R.id.ListView);
        buttonCreateDeck = findViewById(R.id.buttonCreateDeck);

        showList();

    }

    private void showList() {
        deck = new ArrayList<>();
        deck.addAll(getAllDecks());

        AdapterEditDeck adapter = new AdapterEditDeck(this, R.layout.item_editor, deck);
        listView.setAdapter(adapter);

        buttonCreateDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditDeckActivity.this, NewDeckActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Deck> getAllDecks(){
        List<Deck> res = new ArrayList<>();
        Dimension d = new Dimension(2, 1);
        for(DeckFactory.Decks decks : DeckFactory.Decks.values()){
            Deck deck = DeckFactory.getDeck(decks, d , this);
            res.add(deck);
        }
        return res;
    }
}
