package com.sharpsoft.twinsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterEditDeck extends BaseAdapter {

    private Context context;
    private int layout;
    private List<String> deck;

    public AdapterEditDeck (Context context , int layout, List<String> notes) {
        this.context = context;
        this.layout = layout;
        this.deck = notes;
    }

    @Override
    public int getCount() {
        return this.deck.size();
    }

    @Override
    public Object getItem(int position) {
        return this.deck.get(position);
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

        String currentName = deck.get(position);

        TextView textView = v.findViewById(R.id.textView);
        textView.setText(currentName);

        return v;
    }
}
