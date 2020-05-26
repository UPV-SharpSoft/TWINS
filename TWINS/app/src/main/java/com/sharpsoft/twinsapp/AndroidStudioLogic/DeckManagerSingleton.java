package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.sharpsoft.twins_clases.logic.Dimension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeckManagerSingleton {
    private static DeckManagerSingleton instance = new DeckManagerSingleton();

    public static DeckManagerSingleton getInstance(){
        return instance;
    }

    public void saveDeck(String name, List<Bitmap> images, Bitmap reverse, Context ctx) throws FileNotFoundException {
        File path = new File(ctx.getFilesDir().getPath() + "/customDecks/" + name + "/");
        if(path.exists()){
            ConfigSingleton.getInstance().removeCustomDeck(name, ctx);
        }else{
            path.mkdirs();
        }
        int count = 0;
        for(Bitmap bitmap : images){
            FileOutputStream out = new FileOutputStream(new File(path.getPath() + "/" + count));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            count++;
        }
        FileOutputStream out = new FileOutputStream(new File(path.getPath() + "/reverse"));
        reverse.compress(Bitmap.CompressFormat.PNG, 100, out);
    }

    public Map<String, Bitmap> getAllDecks(Context ctx){
        File path = new File(ctx.getFilesDir().getPath() + "/customDecks/");
        String[] directories = path.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        Map<String, Bitmap> res = new HashMap<>();
        if(directories == null) return res;
        for(String dir : directories){
            Bitmap bitmap = BitmapFactory.decodeFile(ctx.getFilesDir().getPath() + "/customDecks/" + dir + "/reverse");
            res.put(dir, bitmap);
        }
        return res;
    }

    public Deck getDeck(Dimension d, String deckName, int numCartas,Context ctx){
        List<Bitmap> allImages = getAllImages(deckName, ctx);
        List<Bitmap> cartas = new ArrayList<>();
        Bitmap reverse = getReverse(deckName, ctx);
        for(int i = 0; i < d.getTotal()/2; i++){
            Bitmap img = allImages.get( (i % allImages.size()) % numCartas);
            cartas.add(img); cartas.add(img);
        }
        return new Deck(cartas, reverse, deckName);
    }

    public List<Bitmap> getAllImages(String deckName, Context ctx){
        List<Bitmap> allImages = new ArrayList<>();
        int cont = 0;
        String filePath = ctx.getFilesDir().getPath() + "/customDecks/" + deckName + "/" + cont;
        while(new File(filePath).exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            allImages.add(bitmap);

            cont++;
            filePath = ctx.getFilesDir().getPath() + "/customDecks/" + deckName + "/" + cont;
        }
        return allImages;
    }

    public Bitmap getReverse(String deckName, Context ctx){
        return BitmapFactory.decodeFile(ctx.getFilesDir().getPath() + "/customDecks/" + deckName + "/reverse");
    }
}
