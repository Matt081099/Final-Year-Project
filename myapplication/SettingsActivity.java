package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {
    Button btnBack1;
    private View parentView;
    private SwitchMaterial themeSwitch;
    TextView themeTV, mobileTxt, emailTxt;

    private UserSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        settings = (UserSettings) getApplication();
        initWidgets();
        loadSharedPreferences();
        initSwitchListener();

        btnBack1 = (Button) findViewById(R.id.btnBack1);
        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initWidgets()
    {
        // settings
        themeTV = findViewById(R.id.themeTV);
        themeSwitch = findViewById(R.id.mobileSwitch);
        parentView = findViewById(R.id.parentView);
        mobileTxt = findViewById(R.id.mobileTxt);
        emailTxt = findViewById(R.id.emailtxt);
    }

    private void loadSharedPreferences()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOM_THEME, UserSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);
        updateView();
    }

    private void initSwitchListener()
    {
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked)
            {
                if(checked)
                    settings.setCustomTheme(UserSettings.DARK_THEME);
                else
                    settings.setCustomTheme(UserSettings.LIGHT_THEME);

                SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(UserSettings.CUSTOM_THEME, settings.getCustomTheme());
                editor.apply();
                updateView();
            }
        });
    }

    private void updateView()
    {
        final int black = ContextCompat.getColor(this, R.color.black);
        final int white = ContextCompat.getColor(this, R.color.white);

        if(settings.getCustomTheme().equals(UserSettings.DARK_THEME))
        {
            themeTV.setTextColor(white);
            themeTV.setText("Dark");
            parentView.setBackgroundColor(black);
            themeSwitch.setChecked(true);
            mobileTxt.setTextColor(white);
            emailTxt.setTextColor(white);
        }
        else
        {
            themeTV.setTextColor(black);
            themeTV.setText("Light");
            parentView.setBackgroundColor(white);
            themeSwitch.setChecked(false);
            mobileTxt.setTextColor(black);
            emailTxt.setTextColor(black);
        }

    }

}