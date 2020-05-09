package com.sharpsoft.twinsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterEditDeck extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Deck> decks;
    private Map<String, Bitmap> customDecks;

    public AdapterEditDeck (Context context , int layout, List<Deck> decks, Map<String, Bitmap> customDecks) {
        this.context = context;
        this.layout = layout;
        this.decks = decks;
        this.customDecks = customDecks;
    }

    @Override
    public int getCount() {
        return this.decks.size() + this.customDecks.size();
    }

    @Override
    public Object getItem(int position) {
        return this.decks.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View v = view;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(this.layout, null);

        if(position < decks.size()){
            String currentName = decks.get(position).getName();

            TextView textView = v.findViewById(R.id.textView);
            textView.setText(currentName);

            ImageView deckImageView = v.findViewById(R.id.DeckimageView);
            deckImageView.setImageBitmap(decks.get(position).getReverse());
        }else{
            Log.i("info", position + "");
            String name = new ArrayList<String>(customDecks.keySet()).get(position - decks.size());

            TextView textView = v.findViewById(R.id.textView);
            textView.setText(name);

            ImageView deckImageView = v.findViewById(R.id.DeckimageView);
            deckImageView.setImageBitmap(customDecks.get(name));
        }


        return v;
    }
}
