package com.example.jsonparsingexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    MovieAdapter movieAdapter;
    List<Movies> moviesList = new ArrayList<>();
    List<Movies> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            moviesList = getMovie().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        movieAdapter = new MovieAdapter(MainActivity.this, moviesList);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private Future<List<Movies>> getMovie() {
        Callable<List<Movies>> callable = new Callable<List<Movies>>() {
            @Override
            public List<Movies> call() throws Exception {


                        AssetManager assetManager = MainActivity.this.getAssets();
                        BufferedReader bufferedReader = null;
                        StringBuilder stringBuilder = new StringBuilder();
                        String json = "";
                        try {
                            bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open("movieJsonData.json")));
                            String mLine;
                            while ((mLine = bufferedReader.readLine()) != null) {
                                stringBuilder.append(mLine);
                            }
                            json = stringBuilder.toString();
                            Log.v("TAG", json);
                        } catch (IOException e) {
                            //Log
                        } finally {
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e) {
                                    //Log
                                }
                            }
                        }

                        movies = QueryUtils.extractFeaturesFromJson(json);
                        Log.v("LOGTAG", ""+movies.get(0).getTitle());
                return movies;
            }
        };
        return executorService.submit(callable);
    }
}
