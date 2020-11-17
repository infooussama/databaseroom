package com.example.relationbdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.relationbdd.adapter.BusListAdapter;
import com.example.relationbdd.adapter.DetailBusListAdapter;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneAndFullStationArriver;
import com.example.relationbdd.model.LigneDB;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;

public class DetailBusStation extends AppCompatActivity {
    TextView textView;
    MapView mapView;
    RecyclerView recyclerView;
    RoomDB database;
    DetailBusListAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    LigneDao ligneDao;
    List<LigneAndFullStationArriver> LigneAndFullStationArriver = new ArrayList<>();
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_detail_bus_station);
        textView = findViewById(R.id.station_name);
        back = findViewById(R.id.img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        String code = intent.getStringExtra("station_code");
        String name = intent.getStringExtra("station_name");
        double lat = intent.getDoubleExtra("station_lat",0);
        double lon = intent.getDoubleExtra("station_lon",0);
        textView.setText(name);
        database = RoomDB.getInstance(this);
        ligneDao = database.ligneDao();
        List<LigneDB> ligneDBS = ligneDao.getFullStationLignes(code);
        for(LigneDB distance : ligneDBS){
            LigneAndFullStationArriver.add(ligneDao.getLigneAndFullStationArrivers(distance.getLid()));
        }
        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DetailBusListAdapter(this,LigneAndFullStationArriver);

        recyclerView.setAdapter(adapter);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        Marker marker = mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lat, lon))
                                .title(name));
                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(lat,lon));
                        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
                        mapboxMap.moveCamera(center);
                        mapboxMap.animateCamera(zoom);
                        marker.showInfoWindow(mapboxMap,mapView);
                        mapboxMap.getUiSettings().setAllGesturesEnabled(false);
                    }
                });
            }
        });
    }

    /*@Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

    }*/

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}