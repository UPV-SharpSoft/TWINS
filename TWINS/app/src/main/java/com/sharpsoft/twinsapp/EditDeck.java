package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EditDeck extends AppCompatActivity {

    private ListView listView;
    private List<String> deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);

        listView = findViewById(R.id.ListView);

        deck = new ArrayList<String>();
        deck.add("Baraja 1");
        deck.add("Baraja 2");
        deck.add("Baraja 3");
        deck.add("Baraja 4");
        deck.add("Baraja 5");
        deck.add("Crear Baraja");

        AdapterEditDeck adapter = new AdapterEditDeck(this, R.layout.item_editor, deck);
        listView.setAdapter(adapter);
    }
}
