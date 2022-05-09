package com.example.exerciseapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;

import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class WorkoutLogActivity extends AppCompatActivity {


    // Tedious and not the most elegant, but it gets the job done and makes it easy to add more
    // I really need to find out how to make arrays of objects somehow in the res folder to avoid
    // cluttering up my java files
    Exercise[] upperBody = {new Exercise("Push-ups", 30, false),
            new Exercise("Pull-ups", 10, false),
            new Exercise("curls", 30, false),
            new Exercise("Chest fly", 20, false),
            new Exercise("Chest dips", 10, true),
            new Exercise("Landmine Press", 10, false)};
    Exercise[] lowerBody =
            {new Exercise("squats", 10, false),
            new Exercise("lunges", 20, false),
            new Exercise("Glute bridges", 20, false), new Exercise("Crunches", 30, false),
            new Exercise("Planks", 30, false),
            new Exercise("Knee lifts", 15, true)};
    Exercise[] cardioExercises =
            {new Exercise("Jogging", 30, true),
            new Exercise("Leg lifts", 2, false),
            new Exercise("Jump rope", 5, true),
            new Exercise("Soccer", 30, true),
            new Exercise("Basketball", 30, true),
            new Exercise("Running", 30, true)};


    // Nested list for holding them all
    Exercise[][] exercises = {upperBody, lowerBody, cardioExercises};


    TextView currentTempDisplay, UVIndexDisplay;

    // Using weatherbit.io as it works with zipcode search
    String APIKEY = "f8c601883afb465695c3bb9550a151a9";
    String name;
    String zip;
    String cityName;
    int currTemp, airQuality;
    double windSpeed, uvIndex, precipitation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);
        findViewById(R.id.Loading).setVisibility(View.VISIBLE);
        findViewById(R.id.GoodWeather).setVisibility(View.GONE);
        findViewById(R.id.UVGood).setVisibility(View.GONE);
        Intent prevIntent = getIntent();
        name = prevIntent.getStringExtra("firstName");
        zip = prevIntent.getStringExtra("zip");
        new weatherTask().execute();
    }

    public boolean niceWeather(double temp, double wind, double rain) {
        return temp > 70 && temp < 95 && wind < 20;
    }

    public boolean niceAtmosphere(double quality) {
        return quality <= 100;
    }

    public String uvMessage(double index){
        if (index <= 2) {
            return "No special considerations for the UV index needed. Enjoy!";
        }
        else if (index <= 5){
            return "Minor UV index warning. Put on some sunscreen and be sure to bring your water!";
        }
        else {
            return "Today's UV index is high. Be careful and bring plenty of water and sunscreen!";
        }
    }

    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String[] args) {
            return HttpRequest.executeGet("https://api.weatherbit.io/v2.0/current?postal_code="
                    + zip + "&country=US&units=I&key=" + APIKEY);
        }
        @Override
        protected void onPostExecute(String result){
            currentTempDisplay = findViewById(R.id.GoodWeather);
            UVIndexDisplay = findViewById(R.id.UVGood);
            try{
                JSONObject jsonOBJ = new JSONObject(result);
                // God i hate how this api nests the json responses. I get why, but I still hate it.
                JSONArray dataArray = jsonOBJ.getJSONArray("data");
                JSONObject dataObj = dataArray.getJSONObject(0);
                currTemp = dataObj.getInt("temp");
                windSpeed = dataObj.getDouble("wind_spd");
                uvIndex = dataObj.getDouble("uv");
                airQuality = dataObj.getInt("aqi");
                precipitation = dataObj.getDouble("precip");
                cityName = dataObj.getString("city_name");
                if ((niceWeather(currTemp, windSpeed, precipitation)) && (niceAtmosphere(airQuality))) {
                    currentTempDisplay.setText("It's a gorgeous day today in " + cityName + ", why not take advantage?\n");
                }
                else {
                    currentTempDisplay.setText("The weather isn't great for outdoor exercise. Why not stay inside today?");
                }
                UVIndexDisplay.setText(uvMessage(uvIndex));
                findViewById(R.id.Loading).setVisibility(View.GONE);
                findViewById(R.id.GoodWeather).setVisibility(View.VISIBLE);
                findViewById(R.id.UVGood).setVisibility(View.VISIBLE);
            }

            catch(Exception e){

            }
        }
    }



}
