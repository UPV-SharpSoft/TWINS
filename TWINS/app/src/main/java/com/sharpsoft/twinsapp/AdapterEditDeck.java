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

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdapterEditDeck extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Deck> decks;
    private Map<String, Bitmap> customDecks;
    private List<View> views;
    ImageView deleteDeckButton;
    ImageView modifyDeckButton;

    public AdapterEditDeck (Context context , int layout, List<Deck> decks, Map<String, Bitmap> customDecks) {
        this.context = context;
        this.layout = layout;
        this.decks = decks;
        this.customDecks = customDecks;
        this.views = new ArrayList<>();
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
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View v = layoutInflater.inflate(this.layout, null);


        views.add(position, v);
        Log.i("asd", position + "");

        if(position < decks.size()){
            Deck deck =  decks.get(position);
            String currentName = decks.get(position).getName();

            TextView textView = v.findViewById(R.id.textView);
            textView.setText(currentName);

            ImageView deckImageView = v.findViewById(R.id.DeckimageView);
            deckImageView.setImageBitmap(decks.get(position).getReverse());

        }else{
            String name = new ArrayList<String>(customDecks.keySet()).get(position - decks.size());

            TextView textView = v.findViewById(R.id.textView);
            textView.setText(name);

            ImageView deckImageView = v.findViewById(R.id.DeckimageView);
            deckImageView.setImageBitmap(customDecks.get(name));

            deleteDeckButton = v.findViewById(R.id.deleteDeck);
            modifyDeckButton = v.findViewById(R.id.modifyDeck);
            deleteDeckButton.setVisibility(View.VISIBLE);
            modifyDeckButton.setVisibility(View.VISIBLE);

            if(ConfigSingleton.getInstance().getSelectedDeck(new Dimension(2,2),1, context).getName().equals(name)){
                ImageView imageSelected = v.findViewById(R.id.modifyDeck);
                imageSelected.setVisibility(View.VISIBLE);
            }
        }

        /*deleteDeckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
        return v;
    }


    /*
    private View.OnClickListener delete(final int position){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfigSingleton.getInstance().setSelectedDeck(DeckFactory.Decks.values()[position]);
                setSelected(position);
            }
        };
    }

    private View.OnClickListener selectCustomDeck(final String name, final int position){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfigSingleton.getInstance().setSelectedDeck(name);
                setSelected(position);
            }
        };
    }

    private void setSelected(int position){
        for(int i = 0; i < views.size(); i++){
            View v = views.get(i);
            ImageView imageSelected = v.findViewById(R.id.modifyDeck);
            Log.i("position", position + " " + views.size() + " " + i);
            if(i == position){
                imageSelected.setVisibility(View.VISIBLE);
            }else{
                imageSelected.setVisibility(View.INVISIBLE);
            }
        }
    }*/
}
