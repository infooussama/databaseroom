package com.example.relationbdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Mylocation extends AppCompatActivity  implements PermissionsListener, OnMapReadyCallback {
    PermissionsManager permissionsManager;
    LocationComponent locationComponent;
    MapView mapView;
    MapboxMap mapboxMap;
    List<Point> points = null;
    RoomDB roomDB;
    FullStationDao fullStationDao;
    List<FullStation> fullStations;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_mylocation);
        roomDB = RoomDB.getInstance(this);
        fullStationDao = roomDB.fullStationDao();
        fullStations = fullStationDao.getAllStations();
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap=mapboxMap;

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        initRouteCoordinates();
                        for(int m = 0; m < points.size(); m++){
                            marker = mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(points.get(m).latitude(), points.get(m).longitude()))
                                    .title(fullStations.get(m).getScode())
                                    .snippet(fullStations.get(m).getStationDB().getSname()));

                        }
                        /** Marker marker = mapboxMap.addMarker(new MarkerOptions()
                         .position(new LatLng(points.get(k).latitude(), points.get(k).longitude())));*/
                        /**Marker marker2 = mapboxMap.addMarker(new MarkerOptions()
                         .position(new LatLng(points.get(points.size()-1).latitude(),(points.get(points.size()-1).longitude()))));*/
                        enableLocationComponent(style);
                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(locationComponent.getLastKnownLocation().getLatitude(),
                                locationComponent.getLastKnownLocation().getLongitude()));
                        CameraUpdate zoom=CameraUpdateFactory.zoomTo(14);
                        mapboxMap.moveCamera(center);
                        mapboxMap.animateCamera(zoom);
                        mapboxMap.getUiSettings().setAllGesturesEnabled(false);
                    }
                });
            }
        });

        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Intent intent = new Intent(Mylocation.this,CalculeItineraire.class);
                intent.putExtra("marker",marker.getTitle());
                startActivity(intent);
                Toast.makeText(Mylocation.this, marker.getSnippet(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    private void initRouteCoordinates() {
        points = new ArrayList<>();
        for(int i = 0; i<fullStations.size();i++){
            points.add(Point.fromLngLat(fullStations.get(i).getStop_lon(), fullStations.get(i).getStop_lat()));
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "onExplanationNeeded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, "location permission not granted", Toast.LENGTH_LONG).show();
            this.finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        //mapView.invalidate();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
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
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}