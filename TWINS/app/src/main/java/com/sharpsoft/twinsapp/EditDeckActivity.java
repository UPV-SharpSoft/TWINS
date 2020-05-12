package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckFactory;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckManagerSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditDeckActivity extends AppCompatActivity {

    private ListView listView;
    private List<Deck> deck;
    private Button buttonCreateDeck;
    private Button buttonDelete;
    private EditText editTextDeck;
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);

        listView = findViewById(R.id.ListView);
        buttonCreateDeck = findViewById(R.id.buttonCreateDeck);
        buttonDelete = findViewById(R.id.buttonDelete);
        editTextDeck = findViewById(R.id.editTextDeck);

        showList();
        deleteCard();

        audioFacadeInstance.resumeMusic();
        buttonCreateDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditDeckActivity.this, NewDeckActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showList() {
        deck = new ArrayList<>();
        deck.addAll(getAllDecks());

        Map<String, Bitmap> customDecks =  DeckManagerSingleton.getInstance().getAllDecks(this);

        AdapterEditDeck adapter = new AdapterEditDeck(this, R.layout.item_editor, deck, customDecks);
        listView.setAdapter(adapter);
    }

    private void deleteCard(){

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfigSingleton instance = ConfigSingleton.getInstance();
                String deckNameDelete = editTextDeck.getText().toString();
                try{
                    instance.removeCustomDeck(deckNameDelete,EditDeckActivity.this);
                    editTextDeck.setText("");
                }catch (Exception e){
                    Toast.makeText(EditDeckActivity.this, "No existe esa baraja personalizada!", Toast.LENGTH_LONG).show();
                }
                showList();
            }
        });

    }

    private List<Deck> getAllDecks(){
        List<Deck> res = new ArrayList<>();
        Dimension d = new Dimension(2, 1);
        for(DeckFactory.Decks decks : DeckFactory.Decks.values()){
            Deck deck = DeckFactory.getDeck(decks, d , 1,this);
            res.add(deck);
        }
        return res;
    }
}
