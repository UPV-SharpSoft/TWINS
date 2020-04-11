package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sharpsoft.twinsapp.ILogic.Cronometro;

public class PausedActivity extends AppCompatActivity {

    private ImageButton imageButtonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paused);

        ImageButton imageButtonClose = findViewById(R.id.imageButtonClose);
        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Bundle bundle = getIntent().getExtras();

                if (bundle != null) {
                    String string = bundle.getString("cronometro");
                }*/

                finish();
            }
        });
    }

}
