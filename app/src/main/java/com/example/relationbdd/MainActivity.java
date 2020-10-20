package com.example.relationbdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.relationbdd.JsonData.FullData;
import com.example.relationbdd.JsonData.Transfert;
import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.dao.LigneAndFullStationDao;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.dao.TransfertDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.FullStationWithLigne;
import com.example.relationbdd.model.LigneAndFullStation;
import com.example.relationbdd.model.LigneDB;
import com.example.relationbdd.model.LigneWithFullStation;
import com.example.relationbdd.model.StationDB;
import com.example.relationbdd.model.TransfertAndFullStation;
import com.example.relationbdd.model.TransfertDB;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillWithStartingData(this);

        FullStationDao dao = RoomDB.getInstance(this).fullStationDao();
        TransfertDao dao1 = RoomDB.getInstance(this).transfertDao();
        LigneDao dao2 = RoomDB.getInstance(this).ligneDao();
        LigneAndFullStationDao dao3 = RoomDB.getInstance(this).ligneAndFullStationDao();
        dao2.insert(new LigneDB("E160707B","B","E",707,"Chevalley - Plateau - Ain Benian",
                11,"E","ETUSA","skyblue"));
        dao2.insert(new LigneDB("E160707C","B","E",708,"Chevalley - Plateau - Ain Benian",
                11,"E","ETUSA","skyblue"));
        dao2.insert(new LigneDB("E160707D","B","E",709,"Chevalley - Plateau - Ain Benian",
                11,"E","ETUSA","skyblue"));
        dao.insert(new FullStation("16ABN138B",36.802540,2.924149,"cite-138-logts-b-1644",
                new StationDB("Cité 138 Logts",2,"bus",1644,"Aïn Benian")));
        dao.insert(new FullStation("16ABNAGCB",36.802540,2.924149,"cite-138-logts-b-1644",
                new StationDB("baraki",2,"bus",1644,"Aïn Benian")));
        dao1.insert(new TransfertDB("16ABN138B",69));
        dao1.insert(new TransfertDB("16ABN138B",106));
        dao1.insert(new TransfertDB("16ABN138B",200));

        List<TransfertAndFullStation> kezai = dao.getStationWithTransfert();

        List<FullStationWithLigne> a = dao.getFullStationWithLignes();


       // Log.e("kezaikebch",""+kezai.size());
        Log.e("kezaikebch",""+kezai.get(0).transfertDBList.size());
        Log.e("kezaikebchBzfffffffff"," "+a.get(0).ligneDBS.size());

    }

    private static void fillWithStartingData(Context context){

        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.full_station);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String res = "";
        String line;
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
            FullData p = g.fromJson(res, FullData.class);
            Log.e("res",""+p.getFull_stations().get(0).getStop_lat());
        }catch (Exception e){
            e.printStackTrace();
            Log.e("res","4");
        }

        /*JSONArray array = loadJSONArray(context);

        try {
            for(int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                String scode = object.getString("scode");
                String sname = object.getString("sname");
                int nbl = object.getInt("nbl");
                String stype = object.getString("stype");
                int cid = object.getInt("cid");
                String cname = object.getString("cname");
                Transfert transfert = object.getJSONArray("");

            }
        }catch (JSONException e){

        }*/
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