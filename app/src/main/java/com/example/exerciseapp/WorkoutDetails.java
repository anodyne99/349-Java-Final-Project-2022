package com.example.exerciseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class WorkoutDetails extends AppCompatActivity {

    TextView exerciseName;
    TextView repsView;
    TextView haveFunMessage;

    StringBuffer buffer = new StringBuffer();


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_details);
        Button btn = findViewById(R.id.completeBtn);
        exerciseName = findViewById(R.id.workoutName);
        repsView = findViewById(R.id.reps);
        haveFunMessage = findViewById(R.id.outDoorFun);
        Intent prevIntent = getIntent();
        String name = prevIntent.getStringExtra("exerciseName");
        int reps = prevIntent.getIntExtra("reps", 0);
        boolean outdoor = prevIntent.getBooleanExtra("outside", false);
        boolean timed = prevIntent.getBooleanExtra("timed", false);

        exerciseName.setText(name);
        buffer.append(reps);
        if (timed){
            buffer.append(" seconds");
        }
        else {
            buffer.append(" reps");
        }
        repsView.setText(buffer);

        if (outdoor){
            haveFunMessage.setText("Enjoy the great outdoors! Be sure to drink plenty of water!");
        }

        btn.setOnClickListener(v-> {
            Intent completeExercise = new Intent(WorkoutDetails.this, Workouts.class);
            startActivity(completeExercise);
        });
    }
}
