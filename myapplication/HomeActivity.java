package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    Button btnProfle, btnWorkout, btnLogout;
    database DB;

    TextView pointsTxt, welcomeTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DB = new database(this);

        btnProfle = (Button) findViewById(R.id.btnProfile);
        btnWorkout = (Button) findViewById(R.id.btnWorkout);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        pointsTxt = (TextView) findViewById(R.id.pointsTxt);
        welcomeTxt = (TextView) findViewById(R.id.welcomeTxt);

        String p = String.valueOf(DB.totalPoints());
        pointsTxt.setText(String.valueOf(p));

        welcomeTxt.setText(getGreeting());


        btnProfle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        btnWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public String getGreeting(){
        String greeting = "";
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            greeting = "Good Morning";
        }
        else if(timeOfDay >= 12 && timeOfDay < 18){
            greeting = "Good Afternoon";
        }else if(timeOfDay >= 18 && timeOfDay <= 24){
            greeting = "Good Evening";
        }

        return greeting;
    }
}