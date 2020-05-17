package com.sharpsoft.twinsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;


public class ConfigActivity extends AppCompatActivity{
    private Spinner rowsSpinner;
    private Spinner columnsSpinner;
    private SeekBar totalTimeSeekbar;
    private TextView totalTimeValueTextView;
    private SeekBar timePerTurnSeekbar;
    private TextView timePerTurnValueTextView;
    private SeekBar timeStartSeekbar;
    private TextView timeStartValueTextView;
    private SeekBar failureSeekbar;
    private TextView failureValueTextView;
    private Spinner levelTypeSpinner;
    private Spinner musicPackSpinner;
    private SeekBar FXSeekbar;
    private SeekBar musicSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        rowsSpinner = findViewById(R.id.rowsSpinner);
        columnsSpinner = findViewById(R.id.columnsSpinner);
        totalTimeSeekbar = findViewById(R.id.totalTimeSeekBar);
        totalTimeValueTextView = findViewById(R.id.totalTimeValueTextView);
        timePerTurnSeekbar = findViewById(R.id.timePerTurnSeekBar);
        timePerTurnValueTextView = findViewById(R.id.timePerTurnValueTextView);
        timeStartSeekbar = findViewById(R.id.iniTimeSeekBar);
        timeStartValueTextView = findViewById(R.id.iniTimeValueTextView);
        failureSeekbar = findViewById(R.id.timeFailSeekBar);
        failureValueTextView = findViewById(R.id.timeFailValueTextView);
        levelTypeSpinner = findViewById(R.id.levelTypeSpinner);
        musicPackSpinner = findViewById(R.id.musicSpinner);
        FXSeekbar = findViewById(R.id.FXSeekbar);
        musicSeekbar = findViewById(R.id.musicSeekbar);

        setValues(ConfigSingleton.getInstance().getLevelConfig(this));

        initSeekBarTextView(totalTimeSeekbar, totalTimeValueTextView, 120, 1);
        initSeekBarTextView(timePerTurnSeekbar, timePerTurnValueTextView, 10, 1);
        initSeekBarTextView(timeStartSeekbar, timeStartValueTextView, 10, 1);
        initSeekBarTextView(failureSeekbar, failureValueTextView, 3, 1);
    }

    private void setValues(Level level){
        totalTimeSeekbar.setProgress((level.getTotalTime()-1)/1000);
        timePerTurnSeekbar.setProgress((level.getTimePerTurn()-1)/1000);
        timeStartSeekbar.setProgress((level.getFlipStartTime()-1)/1000);
        failureSeekbar.setProgress((level.getFlipTime()-1)/1000);
    }

    private void initSeekBarTextView(SeekBar seekBar, final TextView textView, int max, int increment){
        seekBar.setMax(max);
        seekBar.incrementProgressBy(increment);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        textView.setText(String.valueOf(seekBar.getProgress()));
    }

    public void setValues(){

    }

    public void setGame(){  //falta comprobar los parametros(que esten todos puestos, poner un toast diciendo si falta algo) y lo de la musica/
        /*int width = Integer.parseInt(boardSpinner.getSelectedItem().toString());
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
                height /= 2;
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
        startActivity(i);*/
    }
}
