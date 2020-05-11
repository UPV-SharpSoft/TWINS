package com.sharpsoft.twinsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;



public class FreeGamemodeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner deckSpinner;
    private Spinner boardSpinner;
    private Spinner boardSpinner2;
    private Spinner soundSpinner;
    private Spinner gamemodeSpinner;
    private EditText totalTime;
    private EditText turnTime;
    private EditText showCardTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freegame);

        deckSpinner = findViewById(R.id.deckSpinner);
        boardSpinner = findViewById(R.id.boardSpinner);
        boardSpinner2 = findViewById(R.id.boardSpinner2);
        soundSpinner = findViewById(R.id.soundSpinner);
        gamemodeSpinner = findViewById(R.id.gamemodeSpinner);
        totalTime = findViewById(R.id.totalTime);
        turnTime = findViewById(R.id.turnTime);
        showCardTime = findViewById(R.id.turnTime);

        setValues();
    }

    public void setValues(){
        ArrayAdapter<CharSequence> adapterGamemodes = ArrayAdapter.createFromResource(this, R.array.gamemode, android.R.layout.simple_spinner_item);
        adapterGamemodes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gamemodeSpinner.setAdapter(adapterGamemodes);


        ArrayAdapter<CharSequence> adapterAudioPack = ArrayAdapter.createFromResource(this, R.array.audioPack, android.R.layout.simple_spinner_item);
        adapterAudioPack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soundSpinner.setAdapter(adapterAudioPack);

        ArrayAdapter<CharSequence> adapterBoard = ArrayAdapter.createFromResource(this, R.array.boardDimension, android.R.layout.simple_spinner_item);
        adapterBoard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardSpinner.setAdapter(adapterBoard);
        boardSpinner2.setAdapter(adapterBoard);

        turnTime.setText("Tiempo de turno para girar la segunda carta. Entre 3-10 segundos");
        showCardTime.setText("Tiempo que se mostrarán las cartas al inicio. Entre 0-10 segundos");
        totalTime.setText("Tiempo total de la partida. Entre 20 - 120 segundos");

        ArrayAdapter<CharSequence> adapterDeck = ArrayAdapter.createFromResource(this, R.array.deck, android.R.layout.simple_spinner_item);
        adapterDeck.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deckSpinner.setAdapter(adapterDeck);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
