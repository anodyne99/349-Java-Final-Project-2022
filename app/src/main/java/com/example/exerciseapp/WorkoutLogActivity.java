package com.example.exerciseapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WorkoutLogActivity extends AppCompatActivity {


    // Tedious and not the most elegant, but it gets the job done and makes it easy to add more
    // I really need to find out how to make arrays of objects somehow in the res folder to avoid
    // cluttering up my java files
    Exercise[] legExercises =
            {new Exercise("squats", 10, false),
            new Exercise("lunges", 20, false),
            new Exercise("Glute bridges", 20, false)};
    Exercise[] armExercises =
            {new Exercise("Push-ups", 30, false),
            new Exercise("Pull-ups", 10, false),
            new Exercise("curls", 30, false)};
    Exercise[] cardioExercises =
            {new Exercise("Jogging", 30, true),
            new Exercise("Leg lifts", 2, false),
            new Exercise("Jump rope", 5, true)};
    Exercise[] abExercises =
            {new Exercise("Crunches", 30, false),
            new Exercise("Planks", 30, false),
            new Exercise("Knee lifts", 15, true)};
    Exercise[] chestExercises = {
            new Exercise("Chest fly", 20, false),
            new Exercise("Chest dips", 10, true),
            new Exercise("Landmine Press", 10, false)};
    Exercise[] sportsExercises = {
            new Exercise("Soccer", 30, true),
            new Exercise("Basketball", 30, true),
            new Exercise("Running", 30, true)};

    // Nested list for holding them all
    Exercise[][] exercises =
            {legExercises, armExercises, cardioExercises, abExercises, chestExercises, sportsExercises};


    TextView currentTemp, tempMin, tempMax, windSpeed;
    String APIKEY = "45fed4f1d32e71dd18b79a4aed3ac3e9";

    String name;
    int zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);

        Intent prevIntent = getIntent();
        name = prevIntent.getStringExtra("firstName");
        zip = Integer.parseInt(prevIntent.getStringExtra("zipCode"));

    }

    public void checkWeather(String api) {
        // TODO: Fill in Weather Checking Here
    }


}
