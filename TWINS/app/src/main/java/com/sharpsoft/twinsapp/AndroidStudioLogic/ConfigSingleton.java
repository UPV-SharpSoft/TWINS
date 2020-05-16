package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.util.Log;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twins_clases.logic.FinalScore;
import com.sharpsoft.twinsapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ConfigSingleton {
    private static ConfigSingleton instance = new ConfigSingleton();

    private DeckFactory.Decks selectedDeck;
    private String customDeck;
    private boolean isCustomDeck;

    private int selectedMusic;

    public ConfigSingleton(){
        selectedMusic = R.raw.partida_default;

        isCustomDeck = false;
        selectedDeck = DeckFactory.Decks.minecraft;
    }

    public boolean saveFinalScore(FinalScore finalScore, Context ctx){
        List<FinalScore> scores = getFinalScores(ctx);
        scores.add(0, finalScore);

        try {
            FileOutputStream  fos = new FileOutputStream("t.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(scores);
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<FinalScore> getFinalScores(Context ctx){
        List<FinalScore> res = new ArrayList<>();
        File finalScores = new File(ctx.getFilesDir().getPath() + "/scores.txt");
        if(!finalScores.exists()) return res;

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(finalScores));

            res = (List<FinalScore>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return res;
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

    public void removeCustomDeck(String deckName, Context ctx) {
        File deckPath = new File(ctx.getFilesDir().getPath() + "/customDecks/" + deckName + "/");
        String[] files = deckPath.list();
        for(String s : files){
            new File(deckPath, s).delete();
        }
        deckPath.delete();
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

