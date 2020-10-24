package com.example.relationbdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.relationbdd.JsonData.FullData;
import com.example.relationbdd.JsonData.StationData;
import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.dao.FullStationLigneDBCrossRefDao;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.dao.TransfertDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.fragment.FullStationFragment;
import com.example.relationbdd.fragment.ItineraireFragment;
import com.example.relationbdd.fragment.LigneFragment;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.FullStationLigneDBCrossRef;
import com.example.relationbdd.model.LigneDB;
import com.example.relationbdd.model.StationDB;
import com.example.relationbdd.model.TransfertAndFullStation;
import com.example.relationbdd.model.TransfertDB;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RoomDB roomDB;
    FullData fullData;
    StationData stationData;
    FullStationDao fullStationDao;
    TransfertDao transfertDao;
    LigneDao ligneDao;
    FullStationLigneDBCrossRefDao fullStationLigneDBCrossRefDao;
    boolean check = true;
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initFragments();
        if(check){
            insertFullStation();
            //insertLigneAndCrossRef();
            insertTransfert();
            check = false;
        }


        /*List<LigneDB> ligneDBS = ligneDao.getFullStationLignes("16ABN138B");
        List<FullStation> fullStations = fullStationDao.getLineFullstations("E160099A");

        Log.e("FullStationLignes",""+ligneDBS.size());
        Log.e("LineFullstations",""+fullStations.size());

        List<TransfertAndFullStation> kezai = fullStationDao.getStationWithTransfert();

        Log.e("kezaikebch",""+ kezai.get(0).transfertDBList.size());*/


    }

    public void init(){
        roomDB = RoomDB.getInstance(this);
        fullStationDao = roomDB.fullStationDao();
        transfertDao = roomDB.transfertDao();
        ligneDao = roomDB.ligneDao();
        fullStationLigneDBCrossRefDao = roomDB.fullStationLigneDBCrossRefDao();
        fullData = fillWithStartingData(this);
        stationData = fillWithStartingData2(this);
        chipNavigationBar = findViewById(R.id.chipNavigation);
        chipNavigationBar.setItemSelected(R.id.itineraires,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ItineraireFragment()).commit();
    }

    public void initFragments(){

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.itineraires:
                        fragment = new ItineraireFragment();
                        break;
                    case R.id.stations:
                        fragment = new FullStationFragment();
                        break;
                    case R.id.lignes:
                        fragment = new LigneFragment();
                        break;
                }
                if(fragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                }
            }
        });

    }

    public void insertFullStation(){
        for (int i=0;i<fullData.getFull_stations().size();i++){
            fullStationDao.insert(new FullStation(
                    fullData.getFull_stations().get(i).getScode(),
                    fullData.getFull_stations().get(i).getStop_lat(),
                    fullData.getFull_stations().get(i).getStop_lon(),
                    fullData.getFull_stations().get(i).getPost_name(),
                    new StationDB(stationData.getStations().get(i).getSname(),
                            Integer.parseInt(stationData.getStations().get(i).getNbl()),
                            stationData.getStations().get(i).getStype(),
                            Integer.parseInt(stationData.getStations().get(i).getCid()),
                            stationData.getStations().get(i).getCname())));
        }
    }

    public void insertLigneAndCrossRef(){
        for (int i=0;i<fullData.getFull_stations().size();i++){
            for (int j=0;j<fullData.getFull_stations().get(i).getLines().size();j++){
                ligneDao.insert(new LigneDB(fullData.getFull_stations().get(i).getLines().get(j).getLid(),
                        fullData.getFull_stations().get(i).getLines().get(j).getLtype(),
                        fullData.getFull_stations().get(i).getLines().get(j).getOperator_id(),
                        fullData.getFull_stations().get(i).getLines().get(j).getLnum(),
                        fullData.getFull_stations().get(i).getLines().get(j).getLname(),
                        fullData.getFull_stations().get(i).getLines().get(j).getNbs(),
                        fullData.getFull_stations().get(i).getLines().get(j).getOp_id(),
                        fullData.getFull_stations().get(i).getLines().get(j).getOp_name(),
                        fullData.getFull_stations().get(i).getLines().get(j).getOp_color()));
                fullStationLigneDBCrossRefDao.insert(new FullStationLigneDBCrossRef(fullData.getFull_stations().get(i).getScode(),
                        fullData.getFull_stations().get(i).getLines().get(j).getLid()));
            }
        }
    }

    public void insertTransfert(){
        for (int i=0;i<fullData.getFull_stations().size();i++){
            for (int j=0;j<fullData.getFull_stations().get(i).getTransfers().size();j++){
                transfertDao.insert(new TransfertDB(fullData.getFull_stations().get(i).getTransfers().get(j).getScode(),
                        fullData.getFull_stations().get(i).getTransfers().get(j).getDist()));
            }
        }
    }

    private static FullData fillWithStartingData(Context context){

        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.full_station);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String res = "";
        String line;
        FullData p = null;
        try {
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
            res = builder.toString();
            Log.e("res",res);
        }catch (IOException exception){
            exception.printStackTrace();
            Log.e("res","2");
        }
        try {
            Gson g = new Gson();
            p = g.fromJson(res, FullData.class);
            Log.e("res",""+p.getFull_stations().get(0).getStop_lat());
            Log.e("data",""+p.getFull_stations().get(0).getLines().get(0).getLid());
        }catch (Exception e){
            e.printStackTrace();
            Log.e("res","4");
        }
        return p;

    }
    private static StationData fillWithStartingData2(Context context){
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.station_list);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String res = "";
        String line;
        StationData p = null;
        try {
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
            res = builder.toString();
            Log.e("resstationData",res);
        }catch (IOException exception){
            exception.printStackTrace();
            Log.e("res","2");
        }
        try {
            Gson g = new Gson();
            p = g.fromJson(res, StationData.class);
            Log.e("res",""+p.getStations().get(0).getCid());
        }catch (Exception e){
            e.printStackTrace();
            Log.e("res","4");
        }
        return p;
    }

    private static JSONArray loadJSONArray(Context context){
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.full_station);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        try {
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("full_stations");
        }catch (IOException | JSONException exception){
            exception.printStackTrace();

        }
        return null;
    }
}