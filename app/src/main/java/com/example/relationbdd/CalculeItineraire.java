package com.example.relationbdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.relationbdd.acs.Acs;
import com.example.relationbdd.acs.Ant;
import com.example.relationbdd.adapter.DetailMetroListAdapter;
import com.example.relationbdd.adapter.DetailResultCalculItenirair;
import com.example.relationbdd.model.LigneDB;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.text.DecimalFormat;
import java.util.List;

public class CalculeItineraire extends AppCompatActivity {
    RelativeLayout depart,destination;
    TextView departText, destionationText, time;
    Button button;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    DetailResultCalculItenirair adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcule_itineraire);

        depart = findViewById(R.id.depart);
        destination = findViewById(R.id.destination);
        destionationText = findViewById(R.id.destinationtext);
        departText = findViewById(R.id.departtext);
        button = findViewById(R.id.search);
        time = findViewById(R.id.time);

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
                intent.putExtra("code",1);
                CalculeItineraire.this.startActivityForResult(intent, 0);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Acs acs = new Acs();
                Ant ant = acs.calcule();
                time.setText(""+Acs.temps);
                List<LigneDB> ligneDBS = ant.getSolutionLigne();
                recyclerView = findViewById(R.id.recycler_view);
                linearLayoutManager = new LinearLayoutManager(v.getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter = new DetailResultCalculItenirair((Activity) v.getContext(),ligneDBS);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                String code = data.getStringExtra("station_code");
                String name = data.getStringExtra("station_name");
                double lat = data.getDoubleExtra("station_lat",0);
                double lon = data.getDoubleExtra("station_lon",0);
                departText.setText(name);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

}