package com.example.relationbdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.relationbdd.adapter.DetailBusListAdapter;
import com.example.relationbdd.adapter.DetailLigneListAdapter;
import com.example.relationbdd.adapter.DetailMetroListAdapter;
import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.dao.StationDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneAndFullStationArriver;
import com.example.relationbdd.model.LigneAndFullStationDepart;
import com.example.relationbdd.model.LigneDB;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
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
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;

public class DetailLigne extends AppCompatActivity {

    TextView textView;
    MapView mapView;
    RoomDB database;
    LigneDao ligneDao;
    StationDao stationDao;
    FullStationDao fullStationDao;
    List<LigneAndFullStationArriver> LigneAndFullStationArriver = new ArrayList<>();
    List<Point> points = null;
    FullStation station1,station2;
    RecyclerView recyclerView;
    DetailLigneListAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    List<FullStation> fullStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_detail_ligne);
        textView = findViewById(R.id.station_name);

        Intent intent = getIntent();
        String stationD = intent.getStringExtra("station_depart");
        String stationA = intent.getStringExtra("station_arrive");
        String name = intent.getStringExtra("ligne_name");
        String lid = intent.getStringExtra("lid");
        textView.setText(name);
        database = RoomDB.getInstance(this);
        ligneDao = database.ligneDao();
        stationDao = database.stationDao();
        fullStationDao = database.fullStationDao();
        station1 = stationDao.getFullStations(stationD);
        station2 = stationDao.getFullStations(stationA);
        fullStations = fullStationDao.getLineFullstations(lid);

        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DetailLigneListAdapter(this,fullStations);

        recyclerView.setAdapter(adapter);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        initRouteCoordinates();
                        LineString lineString = LineString.fromLngLats(points);
                        Feature feature = Feature.fromGeometry(lineString);
                        FeatureCollection featureCollection = FeatureCollection.fromFeature(feature);
                        GeoJsonSource geoJsonSource = new GeoJsonSource("line-source", featureCollection);
                        style.addSource(geoJsonSource);

                        style.addLayer(new LineLayer("linelayer", "line-source").withProperties(
                                PropertyFactory.lineDasharray(new Float[] {0.01f, 0.01f}),
                                PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                                PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                                PropertyFactory.lineWidth(10f),
                                PropertyFactory.lineColor(Color.parseColor("#e55e5e"))
                        ));
                        Marker marker = mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(points.get(0).latitude(), points.get(0).longitude())));
                        Marker marker2 = mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(points.get(1).latitude(),points.get(1).longitude())));
                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(points.get(0).latitude(),points.get(0).longitude()));
                        CameraUpdate zoom=CameraUpdateFactory.zoomTo(9);
                        mapboxMap.moveCamera(center);
                        mapboxMap.animateCamera(zoom);
                    }
                });
            }
        });
    }

    private void initRouteCoordinates() {
        points = new ArrayList<>();
        points.add(Point.fromLngLat(station1.getStop_lon(),station1.getStop_lat()));
        points.add(Point.fromLngLat(station2.getStop_lon(),station2.getStop_lat()));
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