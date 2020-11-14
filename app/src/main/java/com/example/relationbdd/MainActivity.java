package com.example.relationbdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.relationbdd.JsonData.FullData;
import com.example.relationbdd.JsonData.StationData;
import com.example.relationbdd.acs.Acs;
import com.example.relationbdd.acs.Ant;
import com.example.relationbdd.acs.Graphf;
import com.example.relationbdd.dao.DistanceDao;
import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.dao.FullStationLigneDBCrossRefDao;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.dao.TransfertDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.fragment.FullStationFragment;
import com.example.relationbdd.fragment.ItineraireFragment;
import com.example.relationbdd.fragment.LigneFragment;
import com.example.relationbdd.model.Distance;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.FullStationLigneDBCrossRef;
import com.example.relationbdd.model.LigneAndFullStationArriver;
import com.example.relationbdd.model.LigneAndFullStationDepart;
import com.example.relationbdd.model.LigneDB;
import com.example.relationbdd.model.StationDB;
import com.example.relationbdd.model.TransfertAndFullStation;
import com.example.relationbdd.model.TransfertDB;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RoomDB roomDB;
    FullData fullData;
    StationData stationData;
    FullStationDao fullStationDao;
    TransfertDao transfertDao;
    LigneDao ligneDao;
    DistanceDao distanceDao;
    FullStationLigneDBCrossRefDao fullStationLigneDBCrossRefDao;
    boolean check = true;
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;
    List<LigneDB> ligneDBS,lignes;
    List<String> stringsArriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initFragments();
        if(fullStationDao.getCountFullstations() == 0 && transfertDao.getCountTransfertDb() == 0 && fullStationLigneDBCrossRefDao.getCountFullStationLigneDBCrossRef() == 0){
            fullData = fillWithStartingData(this);
            stationData = fillWithStartingData2(this);
            insertFullStation();
            insertLigneAndCrossRef();
            insertTransfert();
            //insertDistance();
        }
        /*stringsArriver = ligneDao.getLigneArrive();
        List<FullStation> stations = new ArrayList<>();
        for (int i =0;i<stringsArriver.size();i++){
            stations.add(fullStationDao.getFullStations(stringsArriver.get(i)));
        }
        FullStation a = fullStationDao.getFullStations("16ABNAGCB");
        FullStation b = fullStationDao.getFullStations("16BEBCG1B");
        lignes = ligneDao.getLigneDbs();
        Ant ant = new Ant(stations,lignes,a,b);
        ant.walk();*/
        Acs acs = new Acs();
       Ant result = acs.calcule();
       List<LigneDB> solu = result.getSolutionLigne();
       for (LigneDB ligneDB : solu){
           Log.e("feto",""+ligneDB.getLname());
       }
        Log.e("feto",""+acs.getBest());
        /*Graphf graphf = new Graphf();
        ArrayList<ArrayList<FullStation>> arrayLists = graphf.graph();
        Log.e("routsize",""+arrayLists.size());
        for (int i = 0; i < arrayLists.size(); i++) {
            System.out.println ("*****************");
            Log.e("feto",""+arrayLists.get(i).size());
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                Log.e("aaaaaaaaa",""+arrayLists.get(i).get(j).getStationDB().getSname());
                //System.out.println (arrayLists.get(i).get(j).getStationDB().getSname());
            }
        }*/


        /*ligneDBS = ligneDao.getFullStationLignes("16BEKMBTT");

        List<FullStation> fullStations = fullStationDao.getLineFullstations("E160099A");
        Log.e("aaaaaaaaa",""+ligneDBS.size());
        Log.e("aaaaaaaaa",""+ligneDBS.get(0).getLtype());
        Log.e("aaaaaaaaa",""+ligneDBS.get(1).getLid());
        Log.e("aaaaaaaaa",""+ligneDBS.get(1).getLtype());

        Log.e("LineFullstations",""+fullStations.size());

        Log.e("LineFullstations",""+fullStations.get(0).getStationDB().getSname());
        Log.e("LineFullstations",""+fullStations.get(1).getStationDB().getSname());
        Log.e("LineFullstations",""+fullStations.get(2).getStationDB().getSname());
        Log.e("LineFullstations",""+fullStations.get(3).getStationDB().getSname());
        Log.e("LineFullstations",""+fullStations.get(4).getStationDB().getSname());
        Log.e("LineFullstations",""+fullStations.get(5).getStationDB().getSname());
        Log.e("LineFullstations",""+fullStations.get(6).getStationDB().getSname());
        Log.e("LineFullstations",""+fullStations.get(30).getStationDB().getSname());*/

       // List<TransfertAndFullStation> kezai = fullStationDao.getStationWithTransfert();

        //List<Distance> distances = distanceDao.getDistances("16ABN138B");

        /*Log.e("kezaikebch",""+ distances.size());
        for(Distance distance : distances){
            Log.e("kezaikebch2",""+ distance.getScodeA());
        }*/

       /* List<LigneAndFullStationArriver> LigneAndFullStationArriver = ligneDao.getLigneAndFullStationArrivers("E160003A");
        for(LigneAndFullStationArriver distance : LigneAndFullStationArriver){
            Log.e("Hamada Kebcha",""+ distance.fullStations.get(0).getScode());
            break;
        }*/

        /*List<LigneAndFullStationDepart> ligneAndFullStationDeparts = ligneDao.getLigneAndFullStationDeparts();
        for(int i =0;i<ligneAndFullStationDeparts.size();i++){
            for (int j =0;j<ligneAndFullStationDeparts.get(i).fullStations.size();j++){
            Log.e("Hamada Kebcha",""+ ligneAndFullStationDeparts.get(i).fullStations.get(j).getStationDB().getSname());
            }
        }

        List<LigneAndFullStationArriver> ligneAndFullStationArrivers = ligneDao.getLigneAndFullStationArrivers();
        for(int i =0;i<ligneAndFullStationArrivers.size();i++){
            for (int j =0;j<ligneAndFullStationArrivers.get(i).fullStations.size();j++){
                Log.e("Hamada Kebcha",""+ ligneAndFullStationArrivers.get(i).fullStations.get(j).getStationDB().getSname());
            }
        }*/

        /*List<FullStation> fullStations1 = new ArrayList<>();
        List<FullStation> fullStations2 = new ArrayList<>();
        List<String> stringsArriver = ligneDao.getLigneArrive();
        List<String> stringsDepart = ligneDao.getLigneDepart();
        for (int i =0;i<stringsArriver.size();i++){
            fullStations2.add(fullStationDao.getFullStations(stringsDepart.get(i)));
            fullStations1.add(fullStationDao.getFullStations(stringsArriver.get(i)));
            Log.e("Striings", ""+stringsArriver.size());
        }
        Log.e("Station Depart size", ""+fullStations1.size());
        for (int i =0;i<fullStations1.size();i++){
            Log.e("Station Depart", ""+fullStations1.get(i).getStationDB().getSname());
        }
        for (int i =0;i<fullStations2.size();i++){
            Log.e("Station Aeeiver", ""+fullStations2.get(i).getStationDB().getSname());
        }
        */
        
    }

    public void init(){
        roomDB = RoomDB.getInstance(this);
        fullStationDao = roomDB.fullStationDao();
        transfertDao = roomDB.transfertDao();
        ligneDao = roomDB.ligneDao();
        distanceDao = roomDB.distanceDao();
        fullStationLigneDBCrossRefDao = roomDB.fullStationLigneDBCrossRefDao();
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

    public void insertDistance(){
        for (int i=0;i<fullData.getFull_stations().size();i++){

            ligneDBS = ligneDao.getFullStationLignes(fullData.getFull_stations().get(i).getScode());

            for (int j=0;j < ligneDBS.size();j++){
                List<FullStation> fullStations = fullStationDao.getLineFullstations(ligneDBS.get(j).getLid());

                for(int k =0 ; k < fullStations.size() ;k++){

                    /*ligneDBS = ligneDao.getFullStationLignes("16BEKMBTT");

                    List<FullStation> fullStations = fullStationDao.getLineFullstations("E160099A");
                    Log.e("aaaaaaaaa",""+ligneDBS.size());
                    Log.e("aaaaaaaaa",""+ligneDBS.get(0).getLtype());
                    Log.e("aaaaaaaaa",""+ligneDBS.get(1).getLid());
                    Log.e("aaaaaaaaa",""+ligneDBS.get(1).getLtype());

                    Log.e("LineFullstations",""+fullStations.size());

                    Log.e("LineFullstations",""+fullStations.get(0).getStationDB().getSname());
                    Log.e("LineFullstations",""+fullStations.get(1).getStationDB().getSname());*/

                    distanceDao.insert(new Distance(fullData.getFull_stations().get(i).getScode(),
                            fullStations.get(k).getScode(),CalculationByDistance(
                            new LatLng(fullData.getFull_stations().get(i).getStop_lat(),fullData.getFull_stations().get(i).getStop_lon()),
                            new LatLng(fullData.getFull_stations().get(k).getStop_lat(),fullData.getFull_stations().get(k).getStop_lon()))));

                }

            }

        }

    }

    public void insertLigneAndCrossRef(){
        //Log.e("Line", ""+fullData.getFull_stations().get(0).getLines().get(0).getTerminus().getScode());
        int k=0;
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
                        fullData.getFull_stations().get(i).getLines().get(j).getOp_color(),
                        fullData.getFull_stations().get(i).getLines().get(j).getTerminus().getScode()));
                fullStationLigneDBCrossRefDao.insert(new FullStationLigneDBCrossRef(fullData.getFull_stations().get(i).getScode(),
                        fullData.getFull_stations().get(i).getLines().get(j).getLid()));
            }
        }

        List<LigneDB> ligneDBS = ligneDao.getLigneDbs();
        for(LigneDB ligneDB : ligneDBS){
            ligneDao.updateindex(k,ligneDB.getLid());
            List<LigneDB> arrivaleAndBegining = ligneDao.getStartAndArrival(ligneDB.getLid().substring(0,ligneDB.getLid().length() - 1)+"%");
            arrivaleAndBegining.get(0).setId_depart(arrivaleAndBegining.get(1).getId_arrive());
            arrivaleAndBegining.get(1).setId_depart(arrivaleAndBegining.get(0).getId_arrive());
            ligneDao.update(arrivaleAndBegining.get(0));
            ligneDao.update(arrivaleAndBegining.get(1));
            k++;
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
            //Log.e("res",res);
        }catch (IOException exception){
            exception.printStackTrace();
           // Log.e("res","2");
        }
        try {
            Gson g = new Gson();
            p = g.fromJson(res, FullData.class);
           // Log.e("res",""+p.getFull_stations().get(0).getStop_lat());
           // Log.e("data",""+p.getFull_stations().get(0).getLines().get(0).getLid());
        }catch (Exception e){
            e.printStackTrace();
           // Log.e("res","4");
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
            //Log.e("resstationData",res);
        }catch (IOException exception){
            exception.printStackTrace();
           // Log.e("res","2");
        }
        try {
            Gson g = new Gson();
            p = g.fromJson(res, StationData.class);
           // Log.e("res",""+p.getStations().get(0).getCid());
        }catch (Exception e){
            e.printStackTrace();
            //Log.e("res","4");
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

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.getLatitude();
        double lat2 = EndP.getLatitude();
        double lon1 = StartP.getLongitude();
        double lon2 = EndP.getLongitude();
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        return Radius * c;
    }

}