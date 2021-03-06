package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
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
    private TextView titleTV;
    private Animation rotateAnim;
    private List<Bitmap> selectedDeck;

    Bundle data;
    private ArrayList<Bitmap> newDeckList;
    private String editedDeckStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_deck);
        buttonLoadImage = findViewById(R.id.buttonLoadImage);
        buttonSave = findViewById(R.id.buttonSave);
        editTextName = findViewById(R.id.editTextName);
        titleTV = findViewById(R.id.titleTV);

        rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotation);

        data = getIntent().getExtras();
        Boolean modificado = data.getBoolean("modificado");
        if(modificado){
            modifyDeck();
        }
        titleTV.setText("Crear baraja");
        titleTV.setGravity(Gravity.CENTER);

        uploadCard();

        createDeck();

    }

    private void modifyDeck() {
        titleTV.setText("Modificar baraja");
        titleTV.setGravity(Gravity.CENTER);
        editedDeckStr = data.getString("deckName");
        editTextName.setText(editedDeckStr);
        ConfigSingleton instance = ConfigSingleton.getInstance();
        List<Bitmap> cards = instance.getEditDeck(this, editedDeckStr);


        for (int i = 0; i < cards.size(); i++){
            LinearLayout layout = findViewById(R.id.upperLinearLayout);
            ImageView cardValue = new ImageView(NewDeckActivity.this);
            cardValue.setScaleType(ImageView.ScaleType.FIT_CENTER);
            cardValue.setPadding(20,0,20,0);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500, 600);
            cardValue.setLayoutParams(layoutParams);

            cardValue.setImageBitmap(cards.get(i));
            layout.addView(cardValue);

        }
        if(data.getBoolean("modificado"))
        instance.removeCustomDeck(editedDeckStr, this);
    }

    private void uploadCard() {

        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                buttonLoadImage.startAnimation(rotateAnim);

                rotateAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}

                });

            }
        });

    }

    private Thread saveDeck(final String name, final List<Bitmap> images, final Bitmap reverso){
        final ProgressDialog progress = ProgressDialog.show(this, "Guardando baraja", "Guardando las cartas...", true);
        return new Thread(){
            public void run(){
                try {
                    DeckManagerSingleton.getInstance().saveDeck(name, images, reverso, NewDeckActivity.this);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Thread(){
                    public void run(){
                        progress.dismiss();
                        finish();
                    }
                });
            }
        };
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


                    saveDeck(nameDeck, images, reverso).start();
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
        cardValue.setPadding(20,0,20,0);

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
