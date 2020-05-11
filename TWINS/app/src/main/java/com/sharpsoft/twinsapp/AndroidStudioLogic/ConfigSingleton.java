package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.util.Log;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ConfigSingleton {
    private static ConfigSingleton instance = new ConfigSingleton();

    private DeckFactory.Decks selectedDeck = DeckFactory.Decks.minecraft;
    private String customDeck;
    private boolean isCustomDeck;

    private int selectedMusic;

    public ConfigSingleton(){
        selectedMusic = R.raw.partida_default;

        isCustomDeck = false;
        selectedDeck = DeckFactory.Decks.minecraft;
    }

    public static ConfigSingleton getInstance(){return instance;}

    public Deck getSelectedDeck(Dimension d, int numCartas, Context ctx){
        if(isCustomDeck){
            return DeckManagerSingleton.getInstance().getDeck(d, customDeck, numCartas, ctx);
        }else{
            return DeckFactory.getDeck(selectedDeck, d, numCartas, ctx);
        }
    }

    public void setSelectedDeck(DeckFactory.Decks decks){
        this.isCustomDeck = false;
        this.selectedDeck = decks;
    }

    public void setSelectedDeck(String customDeckName){
        this.isCustomDeck = true;
        this.customDeck = customDeckName;
    }

    public void removeCustomDeck(String deckname, Context ctx){

    }

    public void setSelectedMusic(int selectedMusic){
        this.selectedMusic = selectedMusic;
    }

    public int getSelectedMusic(){
        return this.selectedMusic;
    }

    public int getLevelsPassed(Context context){
        File root = new File(context.getFilesDir().getPath());
        File levelsPassed = new File(root, "levels.txt");
        if(levelsPassed.exists()){
            return Integer.valueOf(readFromFile(levelsPassed));
        } else {
            writeToFile(0, levelsPassed);
            return 0;
        }
    }

    public void setLevelsPassed(int number, Context context) {
        File root = new File(context.getFilesDir().getPath());
        File levelsPassed = new File(root, "levels.txt");
        if (number > readFromFile(levelsPassed))
            writeToFile(number, levelsPassed);
    }

    private void writeToFile(int data, File file) {
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(String.valueOf(data).getBytes());
            stream.close();
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private int readFromFile(File file){
        try {
            byte[] bytes = new byte[(int) file.length()];
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            return Integer.valueOf(new String(bytes));

        } catch (Exception e){
            Log.e("Exception", "File read failed: " + e.toString());
            return -1;
        }
    }
}

