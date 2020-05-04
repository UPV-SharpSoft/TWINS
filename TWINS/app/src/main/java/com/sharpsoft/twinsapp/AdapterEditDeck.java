package com.sharpsoft.twinsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;

import java.util.List;

public class AdapterEditDeck extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Deck> decks;

    public AdapterEditDeck (Context context , int layout, List<Deck> decks) {
        this.context = context;
        this.layout = layout;
        this.decks = decks;
    }

    @Override
    public int getCount() {
        return this.decks.size();
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

        String currentName = decks.get(position).getName();

        TextView textView = v.findViewById(R.id.textView);
        textView.setText(currentName);

        ImageView deckImageView = v.findViewById(R.id.DeckimageView);
        deckImageView.setImageBitmap(decks.get(position).getReverse());

        return v;
    }
}
