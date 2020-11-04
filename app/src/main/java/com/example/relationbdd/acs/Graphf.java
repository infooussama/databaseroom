package com.example.relationbdd.acs;

import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class Graphf extends AppCompatActivity {

    List<FullStation> stations;
    List<FullStation> chosenStation,unused;
    private int nbrNodes;
    private int nbrRoute;
    private int min;
    private int max;
    int ligneLenght,lenght=0;
    FullStationDao fullStationDao;
    LigneDao ligneDao;
    RoomDB roomDB;
    ArrayList<FullStation> nodes;
    ArrayList<ArrayList<FullStation>> routes;
    Random r = new Random();
    List<LigneDB> ligneDBS;
    FullStation item,item2;
    int index,reverse;

    public ArrayList<ArrayList<FullStation>> graph(){
        stations = fullStationDao.getAllStations();
        chosenStation = new ArrayList<>();
        routes = new ArrayList<ArrayList<FullStation>>(nbrRoute);
        for(int count = 1; count<=nbrRoute ;count++){
            nodes = new ArrayList<FullStation>();
            ligneLenght = r.nextInt(min-max) + min;
            if(count == 1){
                index = r.nextInt(stations.size());
                item = stations.get(index);
                nodes.add(item);
                routes.add(nodes);
            }else {
                index = r.nextInt(stations.size());
                item = stations.get(index);
                chosenStation.add(item);
                routes.get(count).add(item);
            }
            lenght++;
            reverse = 1;
            while (lenght<ligneLenght && reverse > 0){
                ligneDBS = ligneDao.getFullStationLignes(item.getScode());
                /*function treje3 stationsArrivres fetouhi yahsel biha return list1*/
                /* inused = remove from list1 (les station li rahom f routes.get(count))*/
                if(unused != null){
                    lenght++;
                    index = r.nextInt(unused.size());
                    item2 = unused.get(index);
                    routes.get(count).add(item2);
                    item = item2;
                    chosenStation.add(item2);
                }else {
                    reverse--;
                }
            }
        }
        return routes;
    }
}
