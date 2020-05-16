package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sharpsoft.twins_clases.logic.FinalScore;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;

import java.util.Calendar;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

     TableLayout table;
     TextView gamesPlayed;
     TextView timePlayed;
     TextView pointsEarned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        table = findViewById(R.id.tableLayout);
        gamesPlayed = findViewById(R.id.gamesPlayed);
        timePlayed = findViewById(R.id.timePlayed);
        pointsEarned = findViewById(R.id.pointsEarned);

        actualValues();
    }

    private void actualValues(){

        ConfigSingleton config =  ConfigSingleton.getInstance();
        List<FinalScore> results = config.getFinalScores(RankingActivity.this);

        for(int i = 0; i < results.size(); i++){
            TableRow tr = new TableRow(this);
            String calendar = results.get(i).getDate().getTime().toString();

            TextView tvTipo = createTextView(results.get(i).getType());

            TextView tvPuntos = createTextView(String.valueOf(results.get(i).getPoints()));

            TextView tvFecha = createTextView(calendar.substring(4,10));

            TextView tvHora = createTextView(calendar.substring(11,16));

            TextView tvTiempo = createTextView(String.valueOf(results.get(i).getTime()));

            tr.addView(tvTipo);
            tr.addView(tvPuntos);
            tr.addView(tvFecha);
            tr.addView(tvHora);
            tr.addView(tvTiempo);
            table.addView(tr);

            gamesPlayed.setText(String.valueOf(results.size()));

            int time = Integer.parseInt(timePlayed.getText().toString());
            time = time + results.get(i).getTime();
            timePlayed.setText(String.valueOf(time));

            int points = Integer.parseInt(pointsEarned.getText().toString());
            points = points + results.get(i).getPoints();
            pointsEarned.setText(String.valueOf(points));
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
