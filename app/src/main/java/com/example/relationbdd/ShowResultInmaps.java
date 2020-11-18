package com.example.relationbdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.relationbdd.JsonData.Ligne;
import com.example.relationbdd.acs.Ant;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneDB;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
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
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowResultInmaps extends AppCompatActivity  {
    Ant ant;
    MapView mapView;
    MapboxMap mapboxMap;
    List<Point> points = null;
    List<FullStation> fullStations;
    private NavigationMapRoute navigationMapRoute;
    private static final String TAG = "DirectionsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_show_result_inmaps);
        Intent intent = this.getIntent();
        fullStations = (List<FullStation>) intent.getSerializableExtra("value");
        Log.e("",""+fullStations.size());

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
                        for (int k = 0;k<points.size();k++){
                            Marker marker = mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(points.get(k).latitude(), points.get(k).longitude())));
                            /**Marker marker2 = mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(points.get(points.size()-1).latitude(),(points.get(points.size()-1).longitude()))));*/
                        }
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
        for(int i = 0; i<fullStations.size();i++){
            points.add(Point.fromLngLat(fullStations.get(i).getStop_lon(), fullStations.get(i).getStop_lat()));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}