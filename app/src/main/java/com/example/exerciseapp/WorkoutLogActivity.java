package com.example.exerciseapp;

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
import java.util.Locale;

public class WorkoutLogActivity extends AppCompatActivity {

    TextView currentTemp, tempMin, tempMax, windSpeed;
    String APIKEY = "45fed4f1d32e71dd18b79a4aed3ac3e9";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);
    }


}
