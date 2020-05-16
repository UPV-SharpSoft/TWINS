package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sharpsoft.twins_clases.logic.FinalScore;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;

import java.util.List;

public class RankingActivity extends AppCompatActivity {
    TableLayout table;
    TextView partidasJugadasTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        table = findViewById(R.id.tableLayout);
        partidasJugadasTextView = findViewById(R.id.partidasJugadasTextView);

        fillTable();
        partidasJugadasTextView.setText(String.valueOf(table.getChildCount() - 1));

    }

    private void fillTable(){

        ConfigSingleton config =  ConfigSingleton.getInstance();
        List<FinalScore> results = config.getFinalScores(this);

        for(int i = 0; i < results.size(); i++){
            TableRow tr = new TableRow(this);

            TextView tvTipo = createTextView(results.get(i).getType());

            TextView tvPuntos = createTextView(String.valueOf(results.get(i).getPoints()));

            TextView tvFecha = createTextView("Doctor dame una fecha que me duele el tobillo");

            TextView tvHora = createTextView("Tienes hora nena? Pues introducela aqui");

            TextView tvTiempo = createTextView(String.valueOf(results.get(i).getTime()));

            tr.addView(tvTipo);
            tr.addView(tvPuntos);
            tr.addView(tvFecha);
            tr.addView(tvHora);
            tr.addView(tvTiempo);
            table.addView(tr);
        }
    }

    private TextView createTextView(String text){
        TextView res = new TextView(this);

        res.setText(text);
        res.setTextColor(Color.BLACK);
        res.setBackgroundResource(R.drawable.square_button);
        res.setHeight(67);

        return res;
    }
}
