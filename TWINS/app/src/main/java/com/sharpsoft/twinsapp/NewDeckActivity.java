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
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckFactory;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckManagerSingleton;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class NewDeckActivity extends AppCompatActivity {

    private ImageButton buttonLoadImage;
    private ImageView imageViewDeck;
    private static int RESULT_LOAD_IMAGE = 1;
    private ImageView previousCard;
    private ImageView nextCard;
    private Button buttonSave;
    private EditText editTextName;

    private List<Bitmap> selectedDeck;
    private int count = 0;

    private ArrayList<Bitmap> newDeckList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_deck);
        buttonLoadImage = findViewById(R.id.buttonLoadImage);
        buttonSave = findViewById(R.id.buttonSave);
        editTextName = findViewById(R.id.editTextName);

        uploadCard();

        createDeck();

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

                if (Pattern.matches("^[a-zA-Z]*$", nameDeck)  && !nameDeck.isEmpty()  ) {

                    LinearLayout layout = findViewById(R.id.upperLinearLayout);

                    Bitmap reverso = ((BitmapDrawable) ((ImageView) layout.getChildAt(0)).getDrawable()).getBitmap();
                    List<Bitmap> images = new ArrayList<>();
                    for (int i = 1; i < layout.getChildCount(); i++) {
                        BitmapDrawable bd = (BitmapDrawable) ((ImageView) layout.getChildAt(i)).getDrawable();
                        images.add(bd.getBitmap());
                    }

                    try {
                        DeckManagerSingleton.getInstance().saveDeck(nameDeck, images, reverso, NewDeckActivity.this);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(NewDeckActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                }

                else {
                    Toast.makeText(NewDeckActivity.this, "Tienes que especificar el nombre con carácteres alfanuméricos!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        cardValue.setScaleType(ImageView.ScaleType.FIT_CENTER);
        cardValue.setPadding(0,0,20,0);

        if(layout.getChildCount()==0){
            TextView textReverse = new TextView(NewDeckActivity.this);
            textReverse.setText("Reverso");
            textReverse.setTextColor(000000);
            layout.addView(textReverse);


        }


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500, 600);
        cardValue.setLayoutParams(layoutParams);



        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                cardValue.setImageBitmap(selectedImage);
                layout.addView(cardValue);

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
