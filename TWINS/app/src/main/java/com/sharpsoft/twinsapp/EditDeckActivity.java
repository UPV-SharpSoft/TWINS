package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckFactory;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckManagerSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.RemoveCustomDeckObservable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditDeckActivity extends AppCompatActivity {

    private ListView listView;
    private List<Deck> deck;
    private ImageButton buttonCreateDeck;
    private EditText editTextDeck;
    private ImageButton modifyDeckButton;
    private ImageButton deleteDeckButton;
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);

        listView = findViewById(R.id.ListView);
        buttonCreateDeck = findViewById(R.id.buttonCreateDeck);
        deleteDeckButton = findViewById(R.id.deleteDeck);
        //editTextDeck = findViewById(R.id.editTextDeck);

        showList();

        ConfigSingleton instance = ConfigSingleton.getInstance();
        instance.addObserver(new RemoveCustomDeckObservable() {
            @Override
            public void onRemove(String deckName) {
                showList();
            }
        });

        //deleteCard();

        audioFacadeInstance.resumeMusic();

        final Animation rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotation);
        buttonCreateDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonCreateDeck.startAnimation(rotateAnim);
                rotateAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(EditDeckActivity.this, NewDeckActivity.class);
                        intent.putExtra("modificado", false);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

            }
        });
    }

    private void showList() {
        deck = new ArrayList<>();
        deck.addAll(getAllDecks());

        Map<String, Bitmap> customDecks =  DeckManagerSingleton.getInstance().getAllDecks(this);

        AdapterEditDeck adapter = new AdapterEditDeck(this, R.layout.item_editor, customDecks);
        listView.setAdapter(adapter);

    }

    /*private void deleteCard(){

        deleteDeckButton.setOnClickListener(new View.OnClickListener() {
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

            }
        });

    }*/

    private List<Deck> getAllDecks(){
        List<Deck> res = new ArrayList<>();
        Dimension d = new Dimension(2, 1);
        for(DeckFactory.Decks decks : DeckFactory.Decks.values()){
            Deck deck = DeckFactory.getDeck(decks, d , 1,this);
            res.add(deck);
        }
        return res;
    }

    @Override
    public void onResume(){
        super.onResume();
        showList();
    }
}
