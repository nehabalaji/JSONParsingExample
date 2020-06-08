package com.example.jsonparsingexample;

import android.text.TextUtils;
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
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QueryUtils {

    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("LOG", "Problem building the URL ", e);
        }
        return url;
    }

    private static String MakeHTTPRequest (URL url) throws IOException {
        String jsonResponse = "";
        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream =urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e("LOG", "Error Response Code " + urlConnection.getResponseCode() );
            }

        } catch (IOException e) {
            Log.e("LOG", "Problem retrieving the JSON results", e);
        }
        finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private QueryUtils(){

    }

    public static List<Movies> extractFeaturesFromJson(String movieJson) {

        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }
        List<Movies> movies = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(movieJson);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String imageUrl = jsonObject.getString("image");
                Movies movies1 = new Movies(title, imageUrl);
                movies.add(movies1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public static List<Movies> fetchMovieData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = MakeHTTPRequest(url);

        } catch (Exception e) {
            Log.e("LOG","Problem making the HTTP request",e);

        }
        List<Movies> movies = extractFeaturesFromJson(jsonResponse);
        return movies;
    }
    }
