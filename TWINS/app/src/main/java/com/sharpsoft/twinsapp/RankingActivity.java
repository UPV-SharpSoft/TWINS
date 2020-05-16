package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

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

        Bundle data = getIntent().getExtras();
        //results = data.getParcelableArrayList("results");

        for(int i = 0; i < 100; i++){
            TableRow tr = new TableRow(this);

            TextView tvTipo = createTextView("Tipo de partida " + i);

            TextView tvPuntos = createTextView("Puntos " + i);

            TextView tvFecha = createTextView("fecha " + i);

            TextView tvHora = createTextView("hora " + i);

            TextView tvTiempo = createTextView("tiempo " + i);

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
