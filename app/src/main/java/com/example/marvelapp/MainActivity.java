package com.example.marvelapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
    }

    public void buscarHeroe(View view) {
        String searchQuery = searchEditText.getText().toString().trim();
        if (searchQuery.isEmpty()) {
            Toast.makeText(this, "Ingrese un nombre o ID para buscar", Toast.LENGTH_SHORT).show();
            return;
        }

        String baseUrl = "https://www.superheroapi.com/api.php/";
        String token = "1692099454564193";
        String apiUrl;

        if (searchQuery.matches("\\d+")) {
            // Búsqueda por ID
            apiUrl = baseUrl + token + "/" + searchQuery;
        } else {
            // Búsqueda por nombre
            apiUrl = baseUrl + token + "/search/" + searchQuery;
        }

        // Realizar la solicitud GET a la API superheroapi
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("response") && response.getString("response").equals("success")) {
                                if (response.has("results")) {
                                    JSONArray results = response.getJSONArray("results");
                                    int resultsCount = results.length();
                                    StringBuilder heroes = new StringBuilder();
                                    for (int i = 0; i < resultsCount; i++) {
                                        JSONObject hero = results.getJSONObject(i);
                                        String heroName = hero.getString("name");
                                        heroes.append(heroName).append("\n");
                                    }
                                    showSearchResults(resultsCount, heroes.toString());
                                }
                            } else if (response.has("error")) {
                                String error = response.getString("error");
                                Toast.makeText(MainActivity.this, "Error en la búsqueda: " + error, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error en la búsqueda", Toast.LENGTH_SHORT).show();
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void showSearchResults(int count, String heroes) {
        Intent intent = new Intent(this, HeroListActivity.class);
        intent.putExtra("count", count);
        intent.putExtra("heroes", heroes);
        startActivity(intent);
    }
}