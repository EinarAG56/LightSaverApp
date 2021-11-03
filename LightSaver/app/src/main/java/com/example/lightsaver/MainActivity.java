package com.example.lightsaver;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;


public class MainActivity extends AppCompatActivity {
    int isOn = 0;
    double bright = 2.0;
    String color = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        Switch notifLight = (Switch) findViewById(R.id.switch1);
        Slider slider = findViewById(R.id.discreteSlider);
        Button red = findViewById(R.id.redButton);
        Button blue = findViewById(R.id.blueButton);
        Button green = findViewById(R.id.greenButton);
        Button yellow = findViewById(R.id.yellowButton);
        Button purple = findViewById(R.id.purpleButton);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isOn = 1;
                }else{
                    isOn = 0;
                }
            }
        });

        notifLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String statusNotif;
                if(isChecked){
                    statusNotif = notifLight.getTextOn().toString();
                }else{
                    statusNotif = notifLight.getTextOff().toString();
                }
                Toast.makeText(getApplicationContext(),"Notifications: " + statusNotif, Toast.LENGTH_LONG).show();
            }
        });

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                bright = slider.getValue();
            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "Red";
                Toast.makeText(getApplicationContext(),"Color: " + color, Toast.LENGTH_LONG).show();
            }

        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "Blue";
                Toast.makeText(getApplicationContext(),"Color: " + color, Toast.LENGTH_LONG).show();
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "Green";
                Toast.makeText(getApplicationContext(),"Color: " + color, Toast.LENGTH_LONG).show();
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "yellow";
                Toast.makeText(getApplicationContext(),"Color: " + color, Toast.LENGTH_LONG).show();
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "Purple";
                Toast.makeText(getApplicationContext(),"Color: " + color, Toast.LENGTH_LONG).show();
            }

        });
    }

}