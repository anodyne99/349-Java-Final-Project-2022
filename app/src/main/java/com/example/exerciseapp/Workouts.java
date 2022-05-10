package com.example.exerciseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class Workouts extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{

    private RecyclerViewAdapter adapter;
    private ArrayList<String> workoutNames;

    private RecyclerView recyclerView;
    Vector<String> names = new Vector<>();

    // Tedious and not the most elegant, but it gets the job done and makes it easy to add more
    // I really need to find out how to make arrays of objects somehow in the res folder to avoid
    // cluttering up my java files
    Exercise[] exercises = {
            new Exercise("Push-ups", 30, false, false),
            new Exercise("Pull-ups", 10, false, false),
            new Exercise("Curls", 30, false, false),
            new Exercise("Chest fly", 20, false, false),
            new Exercise("Chest dips", 10, true, false),
            new Exercise("Landmine Press", 10, false, false),
            new Exercise("squats", 10, false, false),
            new Exercise("Lunges", 20, false, false),
            new Exercise("Glute bridges", 20, false, true),
            new Exercise("Crunches", 30, false, false),
            new Exercise("Planks", 30, false, true),
            new Exercise("Knee lifts", 15, true, false),
            new Exercise("Jogging", 30, true, true),
            new Exercise("Leg lifts", 2, false, true),
            new Exercise("Jump rope", 10, true, true),
            new Exercise("Soccer", 30, true, true),
            new Exercise("Basketball", 30, true, true),
            new Exercise("Running", 30, true, true)
    };

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        for (Exercise exercise : exercises) {
            names.add(exercise.getName());
        }
        setContentView(R.layout.workout_list);
        recyclerView = findViewById(R.id.recycler);

        // Sets up recyclerview prerequisites
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        workoutNames = new ArrayList<>();
        workoutNames.addAll(names);
        adapter = new RecyclerViewAdapter(this, workoutNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onRecyclerItemClick(View view, int position) {
        // For dynamic display of click data
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + (position + 1), Toast.LENGTH_SHORT).show();
        String data = adapter.getItem(position);
        Intent intent = new Intent(Workouts.this, WorkoutDetails.class);

        Bundle extras = new Bundle();
        Exercise chosenExercise = exercises[position];
        extras.putString("exerciseName", chosenExercise.getName());
        extras.putInt("reps", chosenExercise.getReps());
        extras.putBoolean("outside", chosenExercise.getOutdoor());
        extras.putBoolean("timed", chosenExercise.getTimed());
        intent.putExtras(extras);
        startActivity(intent);
    }
}