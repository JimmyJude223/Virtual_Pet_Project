package com.example.mobileapplicationproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton ShopButton;
    ImageButton WalkButton;
    ImageButton CustomizeButton;
    Menu optionsMenu;
    private double oldmagnitude = 0;
    private static Integer stepcount = 0;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener stepchecker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //textView = findViewById(R.id.textView);
        sensorFunction();
        sensorManager.registerListener(stepchecker,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        ShopButton = (ImageButton) findViewById(R.id.Shop);
        WalkButton = (ImageButton) findViewById(R.id.walks);
        CustomizeButton = (ImageButton) findViewById(R.id.Customize);

        ShopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Shop.class);
                intent.putExtra("steps", stepcount);
                startActivity(intent);
            }
        });
        WalkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Walks.class);
                startActivity(intent);
            }
        });
        CustomizeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Customize.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Optionsmenu = getMenuInflater();
        Optionsmenu.inflate(R.menu.optionsmenu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.signout_option:
                switchActivites(this,Database_Auth.class);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void sensorFunction(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        stepchecker = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent != null){
                    float x_acceleration = sensorEvent.values[0];
                    float y_acceleration = sensorEvent.values[1];
                    float z_acceleration = sensorEvent.values[2];

                    double magnitude = Math.sqrt(x_acceleration*x_acceleration+y_acceleration*y_acceleration+z_acceleration*z_acceleration);
                    double magnitudeDelta = magnitude - oldmagnitude;
                    oldmagnitude = magnitude;

                    if (magnitudeDelta > 6){
                        stepcount++;
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(stepchecker,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    private void switchActivites(Context source, Class destination) {
        Intent intent = new Intent(source,destination);
        if (Database_Auth.class != destination) {

            intent.putExtra("steps", stepcount);
        }
        startActivity(intent);
    }

}
