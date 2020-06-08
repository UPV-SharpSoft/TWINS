package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twins_clases.logic.FinalScore;
import com.sharpsoft.twinsapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

public class ConfigSingleton extends Observable {
    private static ConfigSingleton instance = new ConfigSingleton();

    public static ConfigSingleton getInstance(){return instance;}

    private DeckFactory.Decks selectedDeck;
    private String customDeck;
    private boolean isCustomDeck;

    private int selectedMusic;
    private ArrayList<Bitmap> listaBitmapsCartas = new ArrayList<Bitmap>() {
    };

    private ConfigSingleton(){
        selectedMusic = R.raw.partida_default;

        isCustomDeck = false;
        selectedDeck = DeckFactory.Decks.minecraft;
    }

    public Level getLevelConfig(Context ctx){
        Level res;
        File level = new File(ctx.getFilesDir().getPath() + "/configLevel.txt");

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(level));

            res = (Level) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Level defaulLevel = new Level();
            Dimension d = new Dimension(4,6);
            defaulLevel.setType(Level.Type.standard);
            defaulLevel.setTotalTime(60*1000);
            defaulLevel.setDimension(d);
            defaulLevel.setFlipStartTime(2*1000);
            defaulLevel.setFlipTime(1000);
            defaulLevel.setTimePerTurn(5*1000);
            defaulLevel.setNumPairs(d.getTotal()/2);
            return defaulLevel;
        }

        return res;
    }

    public boolean setLevelConfig(Level level, Context ctx){
        File levelFile = new File(ctx.getFilesDir().getPath() + "/configLevel.txt");
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(levelFile));
            oos.writeObject(level);
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setMusicVolume(int volume, Context ctx){
        File musicVolumeFile = new File(ctx.getFilesDir().getPath() + "/musicVolume.txt");
        if(volume < 0 || volume > 100) return false;
        try {
            PrintWriter pw = new PrintWriter(musicVolumeFile);
            pw.println(volume);
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setFXVolume(int volume, Context ctx){
        File musicVolumeFile = new File(ctx.getFilesDir().getPath() + "/FXVolume.txt");
        try {
            PrintWriter pw = new PrintWriter(musicVolumeFile);
            pw.println(volume);
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getMusicVolume(Context ctx){
        File musicVolumeFile = new File(ctx.getFilesDir().getPath() + "/musicVolume.txt");

        try {
            Scanner sc = new Scanner(musicVolumeFile);
            return sc.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 100;
        }
    }

    public int getFXVolume(Context ctx){
        File musicVolumeFile = new File(ctx.getFilesDir().getPath() + "/FXVolume.txt");

        try {
            Scanner sc = new Scanner(musicVolumeFile);
            return sc.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 100;
        }
    }

    public boolean saveFinalScore(FinalScore finalScore, Context ctx){
        List<FinalScore> scores = getFinalScores(ctx);
        scores.add(0, finalScore);

        try {
            FileOutputStream  fos = new FileOutputStream(new File(ctx.getFilesDir().getPath() + "/scores.txt"));
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


    public Deck getSelectedDeck(Dimension d, int numCartas, Context ctx){
        if(isCustomDeck){
            return DeckManagerSingleton.getInstance().getDeck(d, customDeck, numCartas, ctx);
        }else{
            return DeckFactory.getDeck(selectedDeck, d, numCartas, ctx);
        }
    }

    public List<Bitmap> getEditDeck(Context ctx, String deckName){
        listaBitmapsCartas.add(DeckManagerSingleton.getInstance().getReverse(deckName, ctx));
        listaBitmapsCartas.addAll(DeckManagerSingleton.getInstance().getAllImages(deckName, ctx));
        return listaBitmapsCartas;

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
        setChanged();
        notifyObservers(deckName);
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

