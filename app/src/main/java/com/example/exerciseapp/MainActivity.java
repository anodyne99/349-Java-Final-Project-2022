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


public class MainActivity extends AppCompatActivity {

    private EditText usernameEdit, firstNameEdit, lastNameEdit, weightEdit, zipCodeEdit;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    UserInfo userInfo;

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
                    addUserToFireBase(userName, firstName, lastName, weight, zipCode);
                }
            }
        });
    }

    private void addUserToFireBase(String userName, String firstName, String lastName, String weight, String zipCode){

        UserInfo user = new UserInfo(firstName, lastName, weight, zipCode);
        DatabaseReference usernameRef = databaseReference.child("Users").child(userName);
        // Only works for one user at a time. Putting in a new username will overwrite previous
        // Needs retooling, outside of assignment requirements and what we covered in class
        usernameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // if the user doesn't exist, creates a new user
                if( !snapshot.exists()) {
                    databaseReference.child("Users").setValue(userName);
                    usernameRef.child("firstName").setValue(firstName);
                    usernameRef.child("lastName").setValue(lastName);
                    usernameRef.child("weight").setValue(weight);
                    usernameRef.child("zipCode").setValue(zipCode);
                    Toast.makeText(MainActivity.this, "User successfully added",
                            Toast.LENGTH_SHORT).show();
                    completeSound.start();
                }
                else {
                    // if does exist , pulls existing user info from database
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserInfo user = snapshot.getValue(UserInfo.class);
                            completeSound.start();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            errorSound.start();
                        }
                    };

                }
                extras.putString("firstName", user.getFirstName());
                extras.putString("lastName", user.getLastName());
                extras.putDouble("weight", Double.parseDouble(user.getWeight()));
                extras.putInt("zip", Integer.parseInt(user.getZipCode()));
                Intent intent = new Intent(MainActivity.this, WorkoutLogActivity.class);
                intent.putExtras(extras);
                completeSound.start();
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