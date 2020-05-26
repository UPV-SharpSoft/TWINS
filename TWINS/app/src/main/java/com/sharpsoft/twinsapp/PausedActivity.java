package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Player;

public class PausedActivity extends AppCompatActivity {

    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();
    private Level level;
    private Bundle bundle;
    private Player player1, player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paused);

        final Context ctx = this;

        Button resumeGameButton = findViewById(R.id.resumeGame);
        Button restartGameButton = findViewById(R.id.restartGame);
        Button mainMenuButton = findViewById(R.id.mainMenu);
        final ImageButton muteAllButton = findViewById(R.id.muteAll);

        bundle = getIntent().getExtras();
        receiveData();
        final Boolean multiplayer = bundle.getBoolean("multiplayer");

        if (audioFacadeInstance.isMutedAll()) {
            muteAllButton.setImageResource(android.R.drawable.ic_lock_silent_mode);
        }else{
            muteAllButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
        }

        resumeGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        muteAllButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(!audioFacadeInstance.isMutedAll()){
                    muteAllButton.setImageResource(android.R.drawable.ic_lock_silent_mode);
                    audioFacadeInstance.muteAll();
                }else{
                    muteAllButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
                    audioFacadeInstance.unMuteAll();
                }
            }

        });

        restartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setTitle("Reiniciar Partida")
                        .setMessage("¿Estás seguro de que quieres reiniciar la partida? \n\nPerderás todo el progreso de la partida en curso")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                if(multiplayer){
                                    MultiGameActivity.closedMethod();
                                    finish();
                                    Intent i = new Intent(PausedActivity.this, MultiGameActivity.class);
                                    sendData(i);
                                    startActivity(i);
                                }else {
                                    //GameActivity.closedMethod();
                                    finish();
                                    Intent i = new Intent(PausedActivity.this, GameActivity.class);
                                    sendData(i);
                                    startActivity(i);
                                }


                            }
                        }).setNegativeButton("No", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setTitle("Salir de la partida")
                        .setMessage("¿Estás seguro de que quieres salir de la partida? \n\nPerderás todo el progreso de la partida en curso")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                if(multiplayer){
                                    MultiGameActivity.closedMethod();
                                }else{
                                    GameActivity.closedMethod();
                                }
                            }
                        }).setNegativeButton("No", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });




        /** Todo Música a Opciones

        seekBarMusic = findViewById(R.id.seekBarMusic);
        seekBarSounds = findViewById(R.id.seekBarSound);


        //Volumen música
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        seekBarMusic.setMax(Audio.MAX_VOLUME);
        seekBarMusic.setProgress(audioInstance.getMusicSeekbarProgress());
        seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                float volume = (float) (1 - (Math.log(Audio.MAX_VOLUME - progress) / Math.log(Audio.MAX_VOLUME)));
                if(volume > 1) volume = 1;
                audioInstance.setMusicVolume(volume, volume);
                audioInstance.setMusicSeekbarProgress(progress);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //Volumen efectos
        seekBarSounds.setMax(Audio.MAX_VOLUME);
        seekBarSounds.setProgress(audioInstance.getSoundPoolProgress());
        seekBarSounds.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = (float) (1 - (Math.log(Audio.MAX_VOLUME - progress) / Math.log(Audio.MAX_VOLUME)));
                if(volume > 1) volume = 1;
                audioInstance.setSoundPoolProgress(progress);
                audioInstance.setSoundVolume(volume);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("vol", ""+audioInstance.getSoundVolume());
                Log.i("progress", ""+audioInstance.getSoundPoolProgress());

            }
        });
         */
    }

    private void receiveData() {
        level = (Level) bundle.get("level");
        player1 = (Player) bundle.get("player1");
        player2 = (Player) bundle.get("player2");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void sendData(Intent intent){
        intent.putExtra("level", level);
        intent.putExtra("player1", player1);
        intent.putExtra("player2", player2);
    }
}
