package com.example.jsonparsingexample;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

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

    }
