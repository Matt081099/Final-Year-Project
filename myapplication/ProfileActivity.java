package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    Button btnDashboard, btnSettings;
    BarChart barChart;
    TextView topExercisesTxt, totalHoursTxt, totalWorkoutsTxt;
    database DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DB = new database(this);

        // initialise elements on page
        btnDashboard = (Button) findViewById(R.id.btnDashboard);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        totalHoursTxt = (TextView) findViewById(R.id.totalHoursTxt);
        totalWorkoutsTxt = (TextView) findViewById(R.id.totalWorkoutsTxt);
        barChart = findViewById(R.id.bar_chart);
        topExercisesTxt = (TextView) findViewById(R.id.topExerciseTxt);

        // Top Exercise Stat
        String topExercise = ("Top Exercise: " + (DB.getTopExercise().toString()));
        topExercisesTxt.setText(topExercise);

        // Total Hours Stat
        DecimalFormat df = new DecimalFormat("#.000"); // 3 decimal places
        String hours = df.format(totalHours());
        //String hours = String.valueOf(totalHours());
        totalHoursTxt.setText(hours);

        // Total workout stats
        totalWorkoutsTxt.setText(String.valueOf(DB.workoutTotal()));

        // Initialise array list
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i=1; i<7; i++){
            float value = (float) (i*10.0); // convert to float
            BarEntry barEntry = new BarEntry(i, value); // Initialise bar chart entry
            // Add values in array list
            barEntries.add(barEntry);
        }
        // Initialise bar data set
        BarDataSet barDataSet = new BarDataSet(barEntries, "Monday - Sunday");
        // Set colours
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        // Hide draw value
        barDataSet.setDrawValues(false);
        // Set bar data
        barChart.setData(new BarData(barDataSet));
        // Set animation
        barChart.animateY(2500);
        // Set description text and colour
        barChart.getDescription().setText("Amount of Exercises each day");
        barChart.getDescription().setTextColor(Color.BLUE);



        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    // Formula to get total hours - 1 exercise = 3 seconds
    double totalHours(){
        double hours = 0;
        double amount = DB.totalExerciseAmount();
        hours = (amount * 3)/ 3600;
        return hours;
    }
}