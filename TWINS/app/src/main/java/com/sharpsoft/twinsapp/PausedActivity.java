package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.sharpsoft.twinsapp.AndroidStudioLogic.Cronometro;

public class PausedActivity extends AppCompatActivity {

    private ImageButton imageButtonClose;
    private Cronometro cronometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paused);

        ImageButton imageButtonClose = findViewById(R.id.imageButtonClose);
        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
