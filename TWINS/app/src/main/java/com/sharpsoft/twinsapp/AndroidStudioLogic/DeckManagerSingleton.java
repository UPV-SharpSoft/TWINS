package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

public class DeckManagerSingleton {
    private static DeckManagerSingleton instance = new DeckManagerSingleton();

    public static DeckManagerSingleton getInstance(){
        return instance;
    }

    public void saveDeck(Deck deck, Context ctx) throws FileNotFoundException {
        File path = new File(ctx.getFilesDir().getPath() + "/customDecks/" + deck.getName() + "/");
        path.mkdirs();
        int count = 0;
        while(deck.quedanCartas()){
            FileOutputStream out = new FileOutputStream(new File(path.getPath() + count));
            deck.sacarCarta().compress(Bitmap.CompressFormat.PNG, 100, out);
            count++;
        }
        FileOutputStream out = new FileOutputStream(new File(path.getPath() + "reverse"));
        deck.getReverse().compress(Bitmap.CompressFormat.PNG, 100, out);
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
        for(String dir : directories){
            Log.i("info", dir);
            Bitmap bitmap = BitmapFactory.decodeFile(dir + "/reverse");
            res.put(dir, bitmap);
        }
        return res;
    }
}
