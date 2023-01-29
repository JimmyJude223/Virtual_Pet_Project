package com.example.mobileapplicationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Walks extends Activity {
    String msg = "Android : ";
    ImageButton HomeButton;
    ImageButton ShopButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walks);
        HomeButton = (ImageButton) findViewById(R.id.Home_Button);
        ShopButton = (ImageButton) findViewById(R.id.Shop);

        HomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Walks.this,MainActivity.class);
                startActivity(intent);
            }
        });
        ShopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Walks.this,Shop.class);
                startActivity(intent);
            }
        });
    }
}