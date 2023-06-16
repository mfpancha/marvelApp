package com.example.marvelapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HeroListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_list);

        TextView countTextView = findViewById(R.id.countTextView);
        TextView heroesTextView = findViewById(R.id.heroesTextView);

        int count = getIntent().getIntExtra("count", 0);
        String heroes = getIntent().getStringExtra("heroes");

        countTextView.setText("Resultados: " + count);
        heroesTextView.setText(heroes);
    }

    public void goBackToSearch(View view) {
        finish(); // Finaliza la actividad actual y vuelve a la actividad anterior (MainActivity)
    }
}
