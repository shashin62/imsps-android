package com.proschoolonline.application;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.proschoolonline.model.CategoriesData;
import com.proschoolonline.model.NewsData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    private static SharedPreference instance = null;
    private SharedPreference() {
        // Exists only to defeat instantiation.
    }
    public static SharedPreference getInstance() {
        if(instance == null) {
            instance = new SharedPreference();
        }
        return instance;
    }

    public void writeToFile(Context context, List favorites) {
        try {
            Gson gson = new Gson();
            String jsonFavorites = gson.toJson(favorites);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("BookmarkData.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(jsonFavorites);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public ArrayList<NewsData> readFromFile(Context context) {

        String ret = "";
        List<NewsData> favorites = null;
        try {
            InputStream inputStream = context.openFileInput("BookmarkData.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                Gson gson = new Gson();
                NewsData[] favoriteItems = gson.fromJson(ret, NewsData[].class);
                if (favoriteItems != null && favoriteItems.length > 0){
                    favorites = Arrays.asList(favoriteItems);
                    favorites = new ArrayList<>(favorites);
                }
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return (ArrayList) favorites;
    }

    public void writeToFileCategory(Context context, List favorites) {
        try {
            Gson gson = new Gson();
            String jsonFavorites = gson.toJson(favorites);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("Category.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(jsonFavorites);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public ArrayList<CategoriesData> readFromFileCategory(Context context) {

        String ret = "";
        List<CategoriesData> favorites = null;
        try {
            InputStream inputStream = context.openFileInput("Category.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                Gson gson = new Gson();
                CategoriesData[] favoriteItems = gson.fromJson(ret, CategoriesData[].class);
                if (favoriteItems != null && favoriteItems.length > 0) {
                    favorites = Arrays.asList(favoriteItems);
                    favorites = new ArrayList<>(favorites);
                }
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return (ArrayList) favorites;
    }

/*
    public static final String PREFS_NAME = "Proschool_App";
    public static final String FAVORITES = "Bookmark";

    public void storeFavorites(Context context, List favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);
        editor.commit();
    }

    public ArrayList<NewsData> loadFavorites(Context context) {
        SharedPreferences settings;
        List<NewsData> favorites;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            NewsData[] favoriteItems = gson.fromJson(jsonFavorites, NewsData[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else
            return null;
        return (ArrayList) favorites;
    }

*/


}
