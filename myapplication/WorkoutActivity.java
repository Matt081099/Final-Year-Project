package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {
    Button btnBack, btnSave, btnSpeak;
    EditText exerciseName;
    EditText exerciseAmount;
    database DB;
    EditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnSave = (Button) findViewById(R.id.btnSave);
        exerciseName = (EditText) findViewById(R.id.exerciseName);
        exerciseAmount = (EditText) findViewById(R.id.exerciseAmount);
        notes = (EditText) findViewById(R.id.notes);

        btnSpeak = (Button) findViewById(R.id.btnSpeak);

        DB = new database(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                LocalDateTime date = LocalDateTime.now();

                String exName = exerciseName.getText().toString();
                int exAmount = Integer.parseInt(exerciseAmount.getText().toString());
                int points = (exAmount) * 5;

                if(exName.equals(""))//|| exAmount.equals(""))
                    Toast.makeText(WorkoutActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{

                    String email = DB.getEmail();
                    DB.insertDataExercise(email, date, exName, exAmount, points);
                    Toast.makeText(WorkoutActivity.this, "Workout added successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    public void Speak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
        startActivityForResult(intent, 100);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK){
            //textView3.setText((data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0)));
            String sentence = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            String arr[] = sentence.split(" ", 2);
            String firstWord = arr[0];
            String secondWord = arr[1];
            exerciseAmount.setText(firstWord);
            exerciseName.setText(secondWord);
            }
        }
    }

