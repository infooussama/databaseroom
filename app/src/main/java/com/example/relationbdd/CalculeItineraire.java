package com.example.relationbdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.relationbdd.acs.Acs;
import com.example.relationbdd.acs.Ant;
import com.example.relationbdd.adapter.DetailMetroListAdapter;
import com.example.relationbdd.adapter.DetailResultCalculItenirair;
import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneDB;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

public class CalculeItineraire extends AppCompatActivity{
    RelativeLayout depart,destination;
    EditText alpha,beta,phormonelvl,evaporation,iteration,numeroant;
    TextView departText, destionationText, time;
    Button calcule,show_route;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    DetailResultCalculItenirair adapter;
    Ant ant;
    String codea,coded;
    ImageButton swap;
    String name,namedes;
    RoomDB roomDB;
    FullStationDao fullStationDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcule_itineraire);
        roomDB = RoomDB.getInstance(this);
        fullStationDao = roomDB.fullStationDao();
        depart = findViewById(R.id.depart);
        destination = findViewById(R.id.destination);
        destionationText = findViewById(R.id.destinationtext);
        alpha = findViewById(R.id.alphac);
        beta = findViewById(R.id.beta);
        phormonelvl = findViewById(R.id.phormonelvl);
        evaporation = findViewById(R.id.evaporation);
        iteration = findViewById(R.id.iteration);
        numeroant = findViewById(R.id.numeroant);
        departText = findViewById(R.id.departtext);
        calcule = findViewById(R.id.search);
        show_route = findViewById(R.id.show_route);
        swap = findViewById(R.id.swap);
        time = findViewById(R.id.time);
        final LoadingDialog loadingDialog = new LoadingDialog(CalculeItineraire.this);
        show_route.setEnabled(false);
        show_route.setBackgroundColor(Color.parseColor("#EEEEEE"));
        calcule.setEnabled(false);
        calcule.setBackgroundColor(Color.parseColor("#656565"));

        Intent intent = getIntent();
        coded = intent.getStringExtra("station_code");
        namedes = intent.getStringExtra("station_name");
        double lat = intent.getDoubleExtra("station_lat",0);
        double lon = intent.getDoubleExtra("station_lon",0);
        destionationText.setText(namedes);
        depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculeItineraire.this, ListStation.class);
                intent.putExtra("code",1);
                CalculeItineraire.this.startActivityForResult(intent, 0);
                calcule.setBackgroundColor(Color.parseColor("#E75748"));
                calcule.setEnabled(true);
            }
        });

        calcule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                String s = alpha.getText().toString();
                String s1 =beta.getText().toString();
                String s2 = phormonelvl.getText().toString();
                String s3 = evaporation.getText().toString();
                String s4 = iteration.getText().toString();
                String s5 = numeroant.getText().toString();
                Acs acs = new Acs();
                ant = acs.calcule(codea,coded,s,s1,s2,s3,s4,s5);
                if(ant!=null){
                    loadingDialog.dismissDialog();
                    show_route.setBackgroundColor(Color.parseColor("#E75748"));
                    show_route.setEnabled(true);
                }
                time.setText(""+Acs.temps+"--------"+ant.getId()+"-----distance-----"+ant.dis+"------prixTotal-----"+ant.prixTotal);
                List<LigneDB> ligneDBS = ant.getSolutionLigne();
                recyclerView = findViewById(R.id.recycler_view);
                linearLayoutManager = new LinearLayoutManager(v.getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter = new DetailResultCalculItenirair((Activity) v.getContext(),ligneDBS);
                recyclerView.setAdapter(adapter);
            }
        });

        show_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(),ShowResultInmaps.class);
                List<FullStation> fullStations = ant.getVisitedStation();
                intent1.putExtra("value", (Serializable) fullStations);
                startActivity(intent1);
            }
        });

        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = coded;
                coded = codea;
                codea = tmp;
                destionationText.setText(namedes);
                departText.setText(name);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                codea = data.getStringExtra("station_code");
                name = data.getStringExtra("station_name");
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