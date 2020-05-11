package com.sharpsoft.twinsapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;



public class FreeGamemodeActivity extends AppCompatActivity {

    private Spinner deckSpinner;
    private Spinner boardSpinner;
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
    }

}
