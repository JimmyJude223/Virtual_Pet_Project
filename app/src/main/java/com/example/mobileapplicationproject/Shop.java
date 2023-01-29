package com.example.mobileapplicationproject;
//See Shop XML for fix android:ID
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Shop extends Activity {
    String msg = "Android : ";
    ImageButton HomeButton;
    ImageButton WalkButton;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener stepchecker;
    private double oldmagnitude = 0;
    private Integer stepcount = 0;
    private TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        HomeButton = (ImageButton) findViewById(R.id.Home_Button);
        WalkButton = (ImageButton) findViewById(R.id.walks);
        textView = findViewById(R.id.shoptrackertextview);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            stepcount = extras.getInt("steps");
            Log.i("Shop Activity","stepcount = " + stepcount);
        }
        textView.setText(stepcount.toString());
        sensorFunction();

        HomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Shop.this,MainActivity.class);
                startActivity(intent);
            }
        });
        WalkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Shop.this,Walks.class);
                startActivity(intent);
            }
        });


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
                    textView.setText(stepcount.toString());
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