package com.example.relationbdd.acs;

import android.content.Context;
import android.util.Log;

import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneDB;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class Graph extends AppCompatActivity {

     ArrayList<FullStation> proximite;
    ArrayList<FullStation> chosenStation,unused;
    private int nbrNodes;
    private int nbrLigne;
    private int min;
    private int max;
    int ligneLenght,lenght;
    FullStationDao fullStationDao;
    LigneDao ligneDao;
    RoomDB roomDB;
    List<FullStation> fullStations;
    ArrayList<FullStation> list1,list2;
    List<LigneDB> ligneDBS;
    List<FullStation> ligneAndStation;
    List<ArrayList<FullStation>> route = new ArrayList<ArrayList<FullStation>>();

    public ArrayList<LigneDB> generateGraph(){
        roomDB = RoomDB.getInstance(this);
        fullStationDao = roomDB.fullStationDao();
        ligneDao = roomDB.ligneDao();
        fullStations = fullStationDao.getAllStations();
        chosenStation = new ArrayList<>();

        for(int i = 1 ;i <= nbrLigne; i++){
            Random r = new Random();
            ligneLenght = r.nextInt(min-max) + min;
            //premier ligne
            if(i==1){
                int index = r.nextInt(fullStations.size());
                FullStation item = fullStations.get(index);
                list1 = new ArrayList<FullStation>();
                list1.add(item);
                route.add(1,list1);
            } else {
                int index = r.nextInt(chosenStation.size());
                FullStation item = chosenStation.get(index);
                chosenStation.add(item);
                list2.add(item);
                route.add(i,list2);
            }
            lenght = 1;
            ligneDBS = ligneDao.getFullStationLignes(route.get(i).get(route.get(i).size()-1).getScode());
            for(int j = 0; i<ligneDBS.size();j++){
                ligneAndStation = fullStationDao.getLineFullstations(ligneDBS.get(j).getLid());
                proximite.add(ligneAndStation.get(0));
            }

            while (lenght<ligneLenght && proximite!=null){
                unused = proximite;
                for(int k = 0;k<route.size();k++){
                    for(int m =0;m<route.get(k).size();m++){
                        unused.remove(route.get(k).get(m));
                    }
                }
                if(unused != null){
                    lenght++;
                    int index = r.nextInt(unused.size());
                    FullStation item = unused.get(index);

                }

            }

        }
        return null;
    }
}
