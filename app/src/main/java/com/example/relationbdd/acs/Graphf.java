package com.example.relationbdd.acs;

import android.util.Log;

import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneDB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class Graphf extends AppCompatActivity {

    List<FullStation> stations;
    List<FullStation> chosenStation,unused,unused2;
    int nbrRoute;int min;int max;
    int ligneLenght,lenght=0;
    FullStationDao fullStationDao;
    LigneDao ligneDao;
    RoomDB roomDB;
    ArrayList<FullStation> nodes,nodes2;
    ArrayList<ArrayList<FullStation>> routes;
    Random r = new Random();
    List<LigneDB> ligneDBS;
    FullStation item,item2;
    int index,reverse,count;

    List<String> stringsArriver;

    public ArrayList<ArrayList<FullStation>> graph(){
        roomDB = RoomDB.getInstance(this);
        fullStationDao = roomDB.fullStationDao();
        ligneDao = roomDB.ligneDao();
        stations = new ArrayList<>();
        stringsArriver = ligneDao.getLigneArrive();
        for (int i =0;i<stringsArriver.size();i++){
            stations.add(fullStationDao.getFullStations(stringsArriver.get(i)));
        }
        chosenStation = new ArrayList<>();
        routes = new ArrayList<ArrayList<FullStation>>(10);
        for(count = 0; count<10 ;count++){
            lenght = 0;
            nodes = new ArrayList<FullStation>();
            ligneLenght = r.nextInt(11 - 7) + 7;
            if(count == 0){
                index = r.nextInt(stations.size());
                item = stations.get(index);
                nodes.add(item);
                routes.add(nodes);
            }else {
                nodes2 = new ArrayList<>();
                index = r.nextInt(stations.size());
                item = stations.get(index);
                chosenStation.add(item);
                nodes2.add(item);
                routes.add(nodes2);
            }
            lenght++;
            reverse = 1;
            while (lenght<ligneLenght && reverse > 0){
                unused = new ArrayList<>();
                ligneDBS = ligneDao.getFullStationLignes(item.getScode());
                for(int k =0; k<ligneDBS.size();k++){
                    if(!fullStationDao.getFullStations(ligneDBS.get(k).getId_arrive()).getScode().equals(item.getScode())){
                        unused.add(fullStationDao.getFullStations(ligneDBS.get(k).getId_arrive()));
                    }
                }
                unused2 = new ArrayList<>();
                for(int z=0;z<unused.size();z++){
                    for(int l=0;l<routes.get(count).size();l++){
                        if(!unused.get(z).getScode().equals(routes.get(count).get(l).getScode())){
                            unused2.add(unused.get(z));
                        }
                    }
                }

                if(unused2 != null){
                    lenght++;
                    index = r.nextInt(unused2.size());
                    item2 = unused2.get(index);
                    routes.get(count).add(item2);
                    item = item2;
                    chosenStation.add(item2);
                }else {
                    item = routes.get(count).get(0);
                    reverse--;
                }
            }
        }
        return routes;
    }
}
