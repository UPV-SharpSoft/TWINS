package com.sharpsoft.twinsapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ILevelBuilder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.LevelDirector;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level5Builder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level4Builder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level1Builder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level3Builder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level2Builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class LevelsActivity extends AppCompatActivity {

    int passedLevels;

    private final int NUMBERLEVELS = 5;

    private ImageView level1;
    private ImageView level2;
    private ImageView level3;
    private ImageView level4;
    private ImageView level5;

    private ImageView []levels;

    private Level level;
    private ILevelBuilder levelBuilder;
    private final LevelDirector levelDirector = new LevelDirector();
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();

    @Override
    protected void onResume(){
        super.onResume();
        passedLevels = ConfigSingleton.getInstance().getLevelsPassed(this);

        audioFacadeInstance.resumeMusic();

        for(int i = 0; i < passedLevels; i++){
            levels[i].setImageBitmap(getBitmapFromAsset("Levels/level" + (i+1) + "passed.png", this));
        }

        imagesListeners();

        for(int i = 0; i < NUMBERLEVELS; i++) {
            if (i < passedLevels + 1) {
                levels[i].setImageAlpha(255);
                levels[i].setClickable(true);
            } else {
                levels[i].setImageAlpha(150);
                levels[i].setClickable(false);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        audioFacadeInstance.resumeMusic();

        passedLevels = ConfigSingleton.getInstance().getLevelsPassed(this);

        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        level4 = findViewById(R.id.level4);
        level5 = findViewById(R.id.level5);

        levels = new ImageView[]{level1, level2, level3, level4, level5};

        for(int i = 0; i < passedLevels; i++){
            levels[i].setImageBitmap(getBitmapFromAsset("Levels/level" + (i+1) + "passed.png", this));
        }

        imagesListeners();

        for(int i = 0; i < NUMBERLEVELS; i++) {
            if (i < passedLevels + 1) {
                levels[i].setImageAlpha(255);
                levels[i].setClickable(true);
            } else {
                levels[i].setImageAlpha(150);
                levels[i].setImageBitmap(getBitmapFromAsset("Levels/level" + (i+1) + "blocked.png", this));
                levels[i].setClickable(false);
            }
        }
    }

    private void imagesListeners(){
        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Board: 5x4
                //Different cards: 4
                //Game mode: Standard
                //Time: 120s
                levelBuilder = new Level1Builder();
                levelDirector.Construct(levelBuilder);
                level = levelBuilder.getLevel();

                Intent i = new Intent(LevelsActivity.this, GameActivity.class);
                i.putExtra("level", level);
                i.putExtra("levelNumber", 1);
                startActivity(i);
            }
        });

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Board: 5x4
                //Different cards: 4
                //Game mode: Standard
                //Time: 100s
                levelBuilder = new Level2Builder();
                levelDirector.Construct(levelBuilder);
                level = levelBuilder.getLevel();

                Intent i = new Intent(LevelsActivity.this, GameActivity.class);
                i.putExtra("level", level);
                i.putExtra("levelNumber", 2);
                startActivity(i);
            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Board: 5x4
                //Different cards: 6
                //Game mode: Standard
                //Time: 80s
                levelBuilder = new Level3Builder();
                levelDirector.Construct(levelBuilder);
                level = levelBuilder.getLevel();

                Intent i = new Intent(LevelsActivity.this, GameActivity.class);
                i.putExtra("level", level);
                i.putExtra("levelNumber", 3);
                startActivity(i);
            }
        });

        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Board: 6x4
                //Different cards: 6
                //Game mode: Standard
                //Time: 60s
                levelBuilder = new Level4Builder();
                levelDirector.Construct(levelBuilder);
                level = levelBuilder.getLevel();

                Intent i = new Intent(LevelsActivity.this, GameActivity.class);
                i.putExtra("level", level);
                i.putExtra("levelNumber", 4);
                startActivity(i);

            }
        });

        level5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Board: 6x4
                //Different cards: 8
                //Game mode: Standard
                //Time: 60s
                levelBuilder = new Level5Builder();
                levelDirector.Construct(levelBuilder);
                level = levelBuilder.getLevel();

                Intent i = new Intent(LevelsActivity.this, GameActivity.class);
                i.putExtra("level", level);
                i.putExtra("levelNumber", 5);
                startActivity(i);
            }
        });
    }

    private Bitmap getBitmapFromAsset(String path, Context ctx) {
        AssetManager assetManager = ctx.getAssets();
        InputStream is = null;
        try {
            is = assetManager.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is);
    }
}