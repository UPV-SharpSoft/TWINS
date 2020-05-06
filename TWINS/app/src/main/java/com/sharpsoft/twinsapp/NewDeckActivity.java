package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private Button buttonSave;
    private EditText editTextName;

    private List<Bitmap> selectedDeck;
    private int count = 0;
    private int position = 0;

    private ArrayList<Bitmap> newList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_deck);

        buttonLoadImage = findViewById(R.id.buttonLoadImage);
        spinnerDeck = findViewById(R.id.spinnerDeck);
        imageViewDeck = findViewById(R.id.imageViewDeck);
        previousCard = findViewById(R.id.previousCard);
        nextCard = findViewById(R.id.nextCard);
        buttonSave = findViewById(R.id.buttonSave);
        editTextName = findViewById(R.id.editTextName);

        selectDeck();

        moveCard();

        addCard();

        uploadCard();

        createDeck();

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
                selectedDeck = DeckFactory.getAllImages(DeckFactory.Decks.values()[i], NewDeckActivity.this);
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
                count--;
                if(count > -1) {
                    imageViewDeck.setImageBitmap(selectedDeck.get(count));
                }
                else{count++;}
            }
        });

        nextCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if(count < selectedDeck.size()) {
                    imageViewDeck.setImageBitmap(selectedDeck.get(count));
                }
                else{count--;}
            }
        });
    }

    private void addCard(){

        imageViewDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView cardValue;

                if (position == 0) {
                    cardValue = findViewById(R.id.card0);
                } else if (position == 1) {
                     cardValue = findViewById(R.id.card1);
                } else if(position == 2) {
                     cardValue = findViewById(R.id.card2);
                }else if(position == 3) {
                     cardValue = findViewById(R.id.card3);
                }else if(position == 4) {
                     cardValue = findViewById(R.id.card4);
                }else if(position == 5) {
                     cardValue = findViewById(R.id.card5);
                }else if(position == 6) {
                     cardValue = findViewById(R.id.card6);
                }else if(position == 7) {
                     cardValue = findViewById(R.id.card7);
                }else if(position == 8) {
                     cardValue = findViewById(R.id.card8);
                }else if(position == 9) {
                     cardValue = findViewById(R.id.card9);
                }else {
                     cardValue = findViewById(R.id.card10);
                }
                
                position++;

                cardValue.setScaleType(ImageView.ScaleType.CENTER_CROP);

                cardValue.setImageDrawable(imageViewDeck.getDrawable());

            }
        });
    }

    private void uploadCard(){

        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

    }

    private void createDeck() {

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameDeck = editTextName.getText().toString();
                Deck newDeck = new Deck (newList, newList.get(0), nameDeck);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView card2 = findViewById(R.id.card2);
            card2.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

    }
}
