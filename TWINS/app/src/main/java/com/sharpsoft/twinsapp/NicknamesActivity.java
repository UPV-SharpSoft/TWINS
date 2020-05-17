package com.sharpsoft.twinsapp;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NicknamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nicknames);

    }
}
