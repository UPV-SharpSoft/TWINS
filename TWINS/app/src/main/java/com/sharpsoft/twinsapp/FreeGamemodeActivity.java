package com.sharpsoft.twinsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;


public class FreeGamemodeActivity extends AppCompatActivity{

    private Spinner deckSpinner;
    private Spinner boardSpinner;
    private Spinner boardSpinner2;
    private Spinner soundSpinner;
    private Spinner gamemodeSpinner;
    private EditText totalTime;
    private EditText turnTime;
    private EditText showCardTime;
    private EditText failTime;

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

        boardSpinner = findViewById(R.id.boardSpinner);
        boardSpinner2 = findViewById(R.id.boardSpinner2);
        soundSpinner = findViewById(R.id.soundSpinner);
        gamemodeSpinner = findViewById(R.id.gamemodeSpinner);
        totalTime = findViewById(R.id.totalTime);
        turnTime = findViewById(R.id.turnTime);
        showCardTime = findViewById(R.id.showCardTime);
        failTime = findViewById(R.id.failTime);

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

        turnTime.setHint("3-10 segundos");
        showCardTime.setHint("0-10 segundos");
        totalTime.setHint("20 - 120 segundos");
        failTime.setHint("1 - 5 segundos");
    }

    public void setGame(){  //falta comprobar los parametros(que esten todos puestos, poner un toast diciendo si falta algo) y lo de la musica/
        int width = Integer.parseInt(boardSpinner.getSelectedItem().toString());
        int height = Integer.parseInt(boardSpinner2.getSelectedItem().toString());

        int tiempoTurno;
        int totalTime;

        int tiempoVolteo;
        int tiempoVolteoInicio;
        try{
            tiempoTurno = Integer.parseInt(turnTime.getText().toString());
            totalTime = Integer.parseInt(this.totalTime.getText().toString());

            tiempoVolteo = Integer.parseInt(failTime.getText().toString());
            tiempoVolteoInicio = Integer.parseInt(showCardTime.getText().toString());
        }catch(Exception e){
            Toast.makeText(this, "Has de introducir todos los campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(totalTime < 20 || totalTime > 120) {
            Toast.makeText(this, "El tiempo total debe estar entre 20-120 segundos", Toast.LENGTH_SHORT).show();
            return;
        }
        if(tiempoTurno < 3 || tiempoTurno > 10){
            Toast.makeText(this, "El tiempo por turno debe estar entre 3-10 segundos", Toast.LENGTH_SHORT).show();
            return;
        }
        if(tiempoVolteoInicio < 0 || tiempoVolteoInicio > 10){
            Toast.makeText(this, "El tiempo de inicio debe estar entre 0-10 segundos", Toast.LENGTH_SHORT).show();
            return;
        }
        if(tiempoVolteo < 1 || tiempoVolteo > 5){
            Toast.makeText(this, "El tiempo de fallo debe estar entre 1-5 segundos", Toast.LENGTH_SHORT).show();
            return;
        }

        Level.Type type = null;
        int typePosition = gamemodeSpinner.getSelectedItemPosition();
        switch (typePosition){
            case 0:
                type = Level.Type.standard;
                break;
            case 1:
                type = Level.Type.bySet;
                break;
            case 2:
                type = Level.Type.byCard;
                break;
        }

        Dimension d = new Dimension(width, height);
        int numPairs = d.getTotal()/2;

        Level level = new Level();
        level.setDimension(d);
        level.setNumPairs(numPairs);
        level.setTimePerTurn(tiempoTurno*1000);
        level.setTotalTime(totalTime*1000);
        level.setType(type);
        level.setFlipTime(tiempoVolteo*1000);
        level.setFlipStartTime(tiempoVolteoInicio*1000);

        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("level", level);
        startActivity(i);
    }
}
