package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
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

    private ArrayList<Bitmap> newDeckList;

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

    private void selectDeck() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(getAllDecks());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
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
                count = (count + selectedDeck.size() - 1) % selectedDeck.size();
                imageViewDeck.setImageBitmap(selectedDeck.get(count));
            }
        });

        nextCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = (count + 1) % selectedDeck.size();
                imageViewDeck.setImageBitmap(selectedDeck.get(count));
            }
        });
    }

    private void addCard() {

        imageViewDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout = findViewById(R.id.upperLinearLayout);

                if (layout.getChildCount() >= 12) {
                    Toast.makeText(NewDeckActivity.this, "Has alcanzado el máximo de cartas!", Toast.LENGTH_LONG).show();
                    return;
                }

                ImageView cardValue = new ImageView(NewDeckActivity.this);
                cardValue.setImageDrawable(imageViewDeck.getDrawable());
                cardValue.setScaleType(ImageView.ScaleType.FIT_CENTER);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(250, 300);
                cardValue.setLayoutParams(layoutParams);

                //BitmapDrawable drawable = (BitmapDrawable) cardValue.getDrawable();
                //Bitmap bitmap = drawable.getBitmap();

                //newDeckList.add(bitmap);

                layout.addView(cardValue);

            }
        });
    }

    private void uploadCard() {

        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
            }
        });

    }

    private void createDeck() {

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameDeck = editTextName.getText().toString();
                Deck newDeck = new Deck(newDeckList, newDeckList.get(0), nameDeck);
            }
        });

    }

    private List<String> getAllDecks() {
        List<String> res = new ArrayList<>();
        Dimension d = new Dimension(2, 1);
        for (DeckFactory.Decks decks : DeckFactory.Decks.values()) {
            Deck deck = DeckFactory.getDeck(decks, d, this);
            res.add(deck.getName());
        }
        return res;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LinearLayout layout = findViewById(R.id.upperLinearLayout);

        if (layout.getChildCount() >= 12) {
            Toast.makeText(NewDeckActivity.this, "Has alcanzado el máximo de cartas!", Toast.LENGTH_LONG).show();
            return;
        }

        ImageView cardValue = new ImageView(NewDeckActivity.this);
        cardValue.setImageDrawable(imageViewDeck.getDrawable());
        cardValue.setScaleType(ImageView.ScaleType.FIT_CENTER);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(250, 300);
        cardValue.setLayoutParams(layoutParams);

        layout.addView(cardValue);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                cardValue.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(NewDeckActivity.this, "Algo ha ido mal :(",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(NewDeckActivity.this, "No has elegido foto!",
                    Toast.LENGTH_LONG).show();
        }
    }
}