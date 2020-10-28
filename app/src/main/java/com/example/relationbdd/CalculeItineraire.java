package com.example.relationbdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalculeItineraire extends AppCompatActivity {
    RelativeLayout depart,destination;
    TextView departText, destionationText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcule_itineraire);

        depart = findViewById(R.id.depart);
        destination = findViewById(R.id.destination);
        destionationText = findViewById(R.id.destinationtext);
        departText = findViewById(R.id.departtext);

        Intent intent = getIntent();
        String code = intent.getStringExtra("station_code");
        String name = intent.getStringExtra("station_name");
        double lat = intent.getDoubleExtra("station_lat",0);
        double lon = intent.getDoubleExtra("station_lon",0);
        destionationText.setText(name);

        depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculeItineraire.this, ListStation.class);
                CalculeItineraire.this.startActivity(intent);
            }
        });



    }
}