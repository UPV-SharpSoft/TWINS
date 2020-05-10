package com.sharpsoft.twinsapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.sharpsoft.twinsapp.AndroidStudioLogic.ILevelBuilder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.LevelDirector;
import com.sharpsoft.twinsapp.AndroidStudioLogic.LevelFiveBuilder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.LevelFourBuilder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.LevelOneBuilder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.LevelThreeBuilder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.LevelTwoBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

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

    private String fileName = "levels.txt";

    private Level level;
    private ILevelBuilder levelBuilder;
    private final LevelDirector levelDirector = new LevelDirector();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        File root = new File(this.getFilesDir().getPath());
        File levelsPassed = new File(root, fileName);
        Log.d("info", this.getFilesDir().getPath() + "/levels.txt");
        if(levelsPassed.exists()){
            passedLevels = Integer.valueOf(readFromFile(levelsPassed));
        } else {
            writeToFile("0", levelsPassed);
            passedLevels = 0;
        }


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

        for(int i = passedLevels + 1; i < NUMBERLEVELS; i++){
            levels[i].setImageAlpha(150);
            levels[i].setClickable(false);
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
                levelBuilder = new LevelOneBuilder();
                levelDirector.Construct(levelBuilder);
            }
        });

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Board: 5x4
                //Different cards: 4
                //Game mode: Standard
                //Time: 100s
                levelBuilder = new LevelTwoBuilder();
                levelDirector.Construct(levelBuilder);
            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Board: 5x4
                //Different cards: 6
                //Game mode: Standard
                //Time: 80s
                levelBuilder = new LevelThreeBuilder();
                levelDirector.Construct(levelBuilder);
            }
        });

        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Board: 6x4
                //Different cards: 6
                //Game mode: Standard
                //Time: 60s
                levelBuilder = new LevelFourBuilder();
                levelDirector.Construct(levelBuilder);

            }
        });

        level5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Board: 6x4
                //Different cards: 8
                //Game mode: Standard
                //Time: 60s
                levelBuilder = new LevelFiveBuilder();
                levelDirector.Construct(levelBuilder);
            }
        });
    }

    private void writeToFile(String data, File file) {
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(data.getBytes());
            stream.close();
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(File file){
        try {
            byte[] bytes = new byte[(int) file.length()];
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            return new String(bytes);

        } catch (Exception e){
            Log.e("Exception", "File read failed: " + e.toString());
            return "error";
        }
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