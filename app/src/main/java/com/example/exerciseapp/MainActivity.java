package com.example.exerciseapp;

import androidx.appcompat.app.AppCompatActivity;

// For database success and failure sounds
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private EditText usernameEdit, firstNameEdit, lastNameEdit, weightEdit, zipCodeEdit;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;


    Bundle extras = new Bundle();

    // Added these for fun as confirmation sounds
    MediaPlayer completeSound;
    MediaPlayer errorSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        completeSound = MediaPlayer.create(MainActivity.this, R.raw.success);
        errorSound = MediaPlayer.create(MainActivity.this, R.raw.wrong);

        usernameEdit = findViewById(R.id.idEditUserName);
        firstNameEdit = findViewById(R.id.idEditFirstName);
        lastNameEdit = findViewById(R.id.idEditLastName);
        weightEdit = findViewById(R.id.idEditWeight);
        zipCodeEdit = findViewById(R.id.idEditZipCode);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();

        Button sendBtn = findViewById(R.id.idBtnSendData);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEdit.getText().toString();
                String lastName = lastNameEdit.getText().toString();
                String weight = weightEdit.getText().toString();
                String zipCode = zipCodeEdit.getText().toString();
                String userName = usernameEdit.getText().toString();

                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                Short.parseShort(weight) <= 0 || Integer.parseInt(zipCode) <= 0 || TextUtils.isEmpty(userName) ||
                TextUtils.isEmpty(weight) || TextUtils.isEmpty(zipCode)){
                    Toast.makeText(MainActivity.this, "Invalid inputs. Please check" +
                            "your entries and try again", Toast.LENGTH_SHORT).show();
                }
                else {
                    UserInfo user = new UserInfo(firstName, lastName, weight, zipCode);
                    addUserToFireBase(user, userName);
                }
            }
        });
    }

    private void addUserToFireBase(UserInfo userInfo, String userName){

        DatabaseReference usernameRef = databaseReference.child("Users").child(userName);
        UserInfo updatedUserInfo = new UserInfo();
        Map<String, String> updatedUser = new HashMap<>();
        updatedUser.put("firstName", userInfo.getFirstName());
        updatedUser.put("lastName", userInfo.getLastName());
        updatedUser.put("weight", userInfo.getWeight());
        updatedUser.put("zipCode", userInfo.getZipCode());
        usernameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // if the user doesn't exist, creates a new user
                // It took me way longer than I thought to get this working
                // So I'm actually very proud I got it working properly
                if( !snapshot.exists()) {
                    databaseReference.child("Users").child(userName).setValue(updatedUser);
                    Toast.makeText(MainActivity.this, "User successfully added",
                            Toast.LENGTH_SHORT).show();
                    extras.putString("firstName", userInfo.getFirstName());
                    extras.putString("lastName", userInfo.getLastName());
                    extras.putDouble("weight", Double.parseDouble(userInfo.getWeight()));
                    extras.putString("zip", userInfo.getZipCode());
                }
                else {
                    UserInfo updatedUserInfo = snapshot.getValue(UserInfo.class);
                    extras.putString("firstName", updatedUserInfo.getFirstName());
                    extras.putString("lastName", updatedUserInfo.getLastName());
                    extras.putDouble("weight", Double.parseDouble(updatedUserInfo.getWeight()));
                    extras.putString("zip", updatedUserInfo.getZipCode());
                    Toast.makeText(MainActivity.this, "User login successful",
                            Toast.LENGTH_SHORT).show();
                }
                completeSound.start();
                Intent intent = new Intent(MainActivity.this, MainPage.class);
                intent.putExtras(extras);
                startActivity(intent);
            }

            @Override
            // if there's an error at any point
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to add user" + error,
                        Toast.LENGTH_SHORT).show();
                errorSound.start();
            }
        });
    }

}