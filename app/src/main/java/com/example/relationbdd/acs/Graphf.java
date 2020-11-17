package com.example.relationbdd.acs;

import android.util.Log;

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
    List<FullStation> chosenStation,unused,unused2;
    List<LigneDB> results = new ArrayList<>();
    int ligneLenght,lenght=0;
    FullStationDao fullStationDao;
    LigneDao ligneDao;
    RoomDB roomDB;
    ArrayList<FullStation> nodes;
    ArrayList<ArrayList<FullStation>> routes;
    Random r = new Random();
    List<LigneDB> ligneDBS;
    FullStation item,item2;
    int index,reverse,count,c = 0;

    List<String> stringsArriver;

    public List<LigneDB> graph(){
        roomDB = RoomDB.getInstance(this);
        fullStationDao = roomDB.fullStationDao();
        ligneDao = roomDB.ligneDao();
        stations = new ArrayList<>();
        stringsArriver = ligneDao.getLigneArrive();
        for (int i =0;i<stringsArriver.size();i++){
            stations.add(fullStationDao.getFullStations(stringsArriver.get(i)));
        }
        chosenStation = new ArrayList<>();
        routes = new ArrayList<ArrayList<FullStation>>();
        for(count = 0; count<92 ;count++){
            lenght = 0;
            nodes = new ArrayList<FullStation>();
            //ligneLenght = r.nextInt(11 - 7) + 7;
            ligneLenght = 2;
            if(count == 0){
                index = r.nextInt(stations.size());
                item = stations.get(index);
                nodes.add(item);
                routes.add(nodes);
                c++;
            }else {
                index = r.nextInt(stations.size());
                item = stations.get(index);
                chosenStation.add(item);
                nodes.add(item);
                routes.add(nodes);
            }
            lenght++;
            reverse = 1;
            while (lenght<ligneLenght && reverse > 0){
                unused = new ArrayList<>();
                ligneDBS = ligneDao.getFullStationLignes(item.getScode());
                for(int k =0; k<ligneDBS.size();k++){
                    if(!fullStationDao.getFullStations(ligneDBS.get(k).getId_arrive()).getScode().equals(item.getScode())){
                        int b;
                        for (b=0;b<unused.size();b++){
                            if(unused.get(b).getScode().equals(ligneDBS.get(k).getId_arrive())){
                                b=-1;
                                break;
                            }
                        }
                        if(b>=0){
                            unused.add(fullStationDao.getFullStations(ligneDBS.get(k).getId_arrive()));
                        }
                    }
                }
                unused = unused;
                unused2 = new ArrayList<>();
                for(int z=0;z<unused.size();z++){
                    int l;
                    for(l=0;l<routes.get(count).size();l++){
                        if(unused.get(z).getScode().equals(routes.get(count).get(l).getScode())){
                            l = -1;
                            break;
                        }
                    }
                    if(l >= 0){
                        unused2.add(unused.get(z));
                    }
                }
                if(!unused2.isEmpty()){
                    lenght++;
                    index = r.nextInt(unused2.size());
                    item2 = unused2.get(index);
                    routes.get(count).add(item2);
                    c++;
                    List<LigneDB> list = ligneDao.getFullStationLignes(item.getScode());
                    for (int i=0 ; i<list.size() ; i++){
                        if(list.get(i).getId_arrive().equals(item2.getScode())){
                            results.add(list.get(i));
                            break;
                        }
                    }
                    item = item2;
                    chosenStation.add(item2);
                }else {
                    routes.remove(count);
                    count--;
                    break;
                }
            }
        }
        Log.e("count station",""+c);
        return results;
    }
}
