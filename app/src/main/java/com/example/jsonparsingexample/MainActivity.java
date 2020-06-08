package com.example.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private static final String USGS_REQUEST_URL = "http://velmm.com/apis/volley_array.json";
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    MovieAdapter movieAdapter;
    List<Movies> moviesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Uri baseUri = Uri.parse(USGS_REQUEST_URL);
                Uri.Builder uriBuilder = baseUri.buildUpon();
                Log.e("TAG", ""+uriBuilder);
                moviesList = QueryUtils.fetchMovieData(uriBuilder.toString());
            }
        });

        movieAdapter = new MovieAdapter(MainActivity.this, moviesList);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setHasFixedSize(true);
    }

}
