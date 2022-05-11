package com.example.exerciseapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    final String[] options = new String[] {
            "Workout",
            "Motivation"
    };

    TextView currentTempDisplay, UVIndexDisplay;

    // Using weatherbit.io as it works with zipcode search
    String APIKEY = "f8c601883afb465695c3bb9550a151a9";
    String name;
    String zip;
    String cityName;
    int currTemp, airQuality;
    double windSpeed, uvIndex, precipitation;
    private ArrayList<String> optionNames;
    private ListView listView;
    String username;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        listView = findViewById(R.id.optionsList);
        findViewById(R.id.Loading).setVisibility(View.VISIBLE);
        findViewById(R.id.GoodWeather).setVisibility(View.GONE);
        findViewById(R.id.UVGood).setVisibility(View.GONE);
        findViewById(R.id.optionsList).setVisibility(View.GONE);

        Intent prevIntent = getIntent();
        name = prevIntent.getStringExtra("firstName");
        zip = prevIntent.getStringExtra("zip");
        new weatherTask().execute();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, options);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            String data = (String) adapterView.getItemAtPosition(position);
            String strippedData = data.replaceAll(" ", "").toLowerCase();
            if (strippedData.contentEquals("workout")){
                Intent intent = new Intent(MainPage.this, Workouts.class);
                startActivity(intent);
            }
            else if (strippedData.contentEquals("motivation")){
                Intent intent = new Intent(MainPage.this, Motivation.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Error, please try selecting again", Toast.LENGTH_SHORT).show();
            }
        });
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
                findViewById(R.id.optionsList).setVisibility(View.VISIBLE);
            }

            catch(Exception e){

            }
        }
    }


}
