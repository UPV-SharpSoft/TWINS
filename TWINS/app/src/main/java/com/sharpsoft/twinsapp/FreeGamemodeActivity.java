package com.sharpsoft.twinsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;


public class FreeGamemodeActivity extends AppCompatActivity{

    private Spinner deckSpinner;
    private Spinner boardSpinner;
    private Spinner boardSpinner2;
    private Spinner soundSpinner;
    private Spinner gamemodeSpinner;
    private EditText totalTime;
    private EditText turnTime;
    private EditText showCardTime;

    private Button playButton;

    private ArrayAdapter<CharSequence> adapterAudioPack;
    private ArrayAdapter<CharSequence> adapterGamemodes;
    private ArrayAdapter<CharSequence> adapterBoard;
    private ArrayAdapter<CharSequence> adapterBoard2;
    private ArrayAdapter<CharSequence> adapterDeck;

    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();


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
        showCardTime = findViewById(R.id.showCardTime);

        playButton = findViewById(R.id.playButton);

        setValues();
        audioFacadeInstance.resumeMusic();

        boardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(boardSpinner.getSelectedItem().toString().equals("3") ||
                        boardSpinner.getSelectedItem().toString().equals("5")){
                    boardSpinner2.setAdapter(adapterBoard2);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        boardSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(boardSpinner2.getSelectedItem().toString().equals("3") ||
                        boardSpinner2.getSelectedItem().toString().equals("5") ){boardSpinner.setAdapter(adapterBoard2);}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(showCardTime == null){
                    deckSpinner.animate().rotation(5).rotation(-10).rotation(5).start();
                }else
                setGame();
            }
        });

    }

    public void setValues(){
        adapterGamemodes = ArrayAdapter.createFromResource(this, R.array.gamemode, android.R.layout.simple_spinner_item);
        adapterGamemodes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gamemodeSpinner.setAdapter(adapterGamemodes);

        adapterAudioPack = ArrayAdapter.createFromResource(this, R.array.audioPack, android.R.layout.simple_spinner_item);
        adapterAudioPack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soundSpinner.setAdapter(adapterAudioPack);

        adapterBoard = ArrayAdapter.createFromResource(this, R.array.boardDimension, android.R.layout.simple_spinner_item);
        adapterBoard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterBoard2 = ArrayAdapter.createFromResource(this, R.array.boardDimension2, android.R.layout.simple_spinner_item);
        adapterBoard2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardSpinner.setAdapter(adapterBoard);
        boardSpinner2.setAdapter(adapterBoard);

        adapterDeck = ArrayAdapter.createFromResource(this, R.array.deck, android.R.layout.simple_spinner_item);
        adapterDeck.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deckSpinner.setAdapter(adapterDeck);

        turnTime.setHint("3-10 segundos");
        showCardTime.setHint("0-10 segundos");
        totalTime.setHint("20 - 120 segundos");
    }

    public void setGame(){



    }
}
