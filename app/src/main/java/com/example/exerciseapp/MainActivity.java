package com.example.exerciseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {

    private EditText firstNameEdit, lastNameEdit, weightEdit, zipCodeEdit;
    private Button sendBtn;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameEdit = findViewById(R.id.idEditFirstName);
        lastNameEdit = findViewById(R.id.idEditLastName);
        weightEdit = findViewById(R.id.idEditWeight);
        zipCodeEdit = findViewById(R.id.idEditZipCode);
    }

    private void addUserToFireBase(String firstName, String lastName, short weight, int zipCode){
    }

}