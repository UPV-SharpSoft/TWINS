package com.sharpsoft.twinsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    private Level level;

    private int columns, rows;

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

        level = ConfigSingleton.getInstance().getLevelConfig(this);

        setValues();
        setSoundValues();
        setSpinners();

        initSeekBarTextView(totalTimeSeekbar, totalTimeValueTextView, 120, 1);
        initSeekBarTextView(timePerTurnSeekbar, timePerTurnValueTextView, 10, 1);
        initSeekBarTextView(timeStartSeekbar, timeStartValueTextView, 10, 1);
        initSeekBarTextView(failureSeekbar, failureValueTextView, 3, 1);
    }

    private void setValues(){
        totalTimeSeekbar.setProgress((level.getTotalTime()-1)/1000);
        timePerTurnSeekbar.setProgress((level.getTimePerTurn()-1)/1000);
        timeStartSeekbar.setProgress((level.getFlipStartTime()-1)/1000);
        failureSeekbar.setProgress((level.getFlipTime()-1)/1000);
    }

    private void setSoundValues(){
        int music = ConfigSingleton.getInstance().getMusicVolume(this);
        int fx = ConfigSingleton.getInstance().getFXVolume(this);

        FXSeekbar.setMax(100);
        FXSeekbar.setProgress(fx);

        musicSeekbar.setMax(100);
        musicSeekbar.setProgress(music);

        musicSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ConfigSingleton.getInstance().setMusicVolume(i, ConfigActivity.this);
                AudioFacade.getInstance().setMusicVolume(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        FXSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ConfigSingleton.getInstance().setFXVolume(i, ConfigActivity.this);
                AudioFacade.getInstance().setSoundVolume(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setSpinners(){
        String[] aux = new String[]{"2", "3", "4", "5", "6"};
        final List<String> valuesRows = new ArrayList<>(Arrays.asList(aux));
        final List<String> valuesColumns = new ArrayList<>(Arrays.asList(aux));
        final ArrayAdapter<String> adapterRows = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, valuesRows);
        ArrayAdapter<String> adapterColumns = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, valuesColumns);
        rowsSpinner.setAdapter(adapterRows);
        columnsSpinner.setAdapter(adapterColumns);

        rowsSpinner.setSelection(valuesRows.indexOf(String.valueOf(level.getDimension().height)));
        columnsSpinner.setSelection(valuesColumns.indexOf(String.valueOf(level.getDimension().width)));
        rows = level.getDimension().height;
        columns = level.getDimension().width;

        if(level.getType() == Level.Type.bySet){
            rows = level.getDimension().height * 2;
            rowsSpinner.setSelection(valuesRows.indexOf(String.valueOf(level.getDimension().height * 2)));
        }

        rowsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int selected = Integer.parseInt((String) rowsSpinner.getSelectedItem());
                rows = selected;
                valuesColumns.clear();
                for(int j = 2; j <= 6; j++){
                    if( (j*selected) % 2 == 0) valuesColumns.add(String.valueOf(j));
                }
                columnsSpinner.setSelection(valuesColumns.indexOf(String.valueOf(columns)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        columnsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int selected = Integer.parseInt((String) columnsSpinner.getSelectedItem());
                columns = selected;
                valuesRows.clear();
                for(int j = 2; j <= 6; j++){
                    if( (j*selected) % 2 == 0) valuesRows.add(String.valueOf(j));
                }
                rowsSpinner.setSelection(valuesRows.indexOf(String.valueOf(rows)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> valuesTypes = new ArrayList<>();
        for(Level.Type t : Level.Type.values()){
            valuesTypes.add(t.toString());
        }
        ArrayAdapter typesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, valuesTypes);
        levelTypeSpinner.setAdapter(typesAdapter);
        levelTypeSpinner.setSelection(valuesTypes.indexOf(level.getType().toString()));

        levelTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Level.Type type = Level.Type.values()[levelTypeSpinner.getSelectedItemPosition()];
                if (type == Level.Type.bySet){
                    valuesRows.clear();
                    for(int j = 2; j <= 4; j++){
                        if(j%2 == 0) valuesRows.add(String.valueOf(j));
                    }
                    if(!valuesRows.contains(String.valueOf(rows))){
                        rowsSpinner.setSelection(0);
                        rows = Integer.parseInt((String) rowsSpinner.getSelectedItem());
                    }
                }else if(type == Level.Type.byCard){
                    valuesRows.clear();
                    for(int j = 2; j <= 4; j++){
                        valuesRows.add(String.valueOf(j));
                    }
                    if(!valuesRows.contains(String.valueOf(rows))){
                        rowsSpinner.setSelection(0);
                        rows = Integer.parseInt((String) rowsSpinner.getSelectedItem());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

    @Override
    public void onBackPressed(){
        int width = columns;
        int height = rows;
        if(Level.Type.values()[levelTypeSpinner.getSelectedItemPosition()] == Level.Type.bySet) height /= 2;
        Dimension d = new Dimension(width, height);

        level.setDimension(d);
        level.setTotalTime(Integer.parseInt(totalTimeValueTextView.getText().toString()) * 1000);
        level.setTimePerTurn(Integer.parseInt(timePerTurnValueTextView.getText().toString()) * 1000);
        level.setFlipStartTime(Integer.parseInt(timeStartValueTextView.getText().toString()) * 1000);
        level.setFlipTime(Integer.parseInt(failureValueTextView.getText().toString()) * 1000);
        level.setType(Level.Type.values()[levelTypeSpinner.getSelectedItemPosition()]);

        ConfigSingleton.getInstance().setLevelConfig(level, this);

        super.onBackPressed();
    }
}