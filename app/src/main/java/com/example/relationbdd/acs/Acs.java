package com.example.relationbdd.acs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneDB;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Acs extends AppCompatActivity {

    List<Ant> ants;
    List<FullStation> stations;
    RoomDB roomDB;
    FullStationDao fullStationDao;
    LigneDao ligneDao;
    List<LigneDB> lignes;
    List<String> stringsArriver;
    Ant bestAnt;
    double best;
    public static double[] phormoneLevel;
    private static final double MAX_PHEROMONE = 1;
    private static final double MIN_PHEROMONE = 0.0001;
    FullStation a,b;
    public static long ACO_TOTAL_TIME = 0;

    public Ant calcule(){
            roomDB = RoomDB.getInstance(this);
            fullStationDao = roomDB.fullStationDao();
            ligneDao = roomDB.ligneDao();
            lignes = ligneDao.getLigneDbs();
            phormoneLevel = new double[lignes.size()];
            for(int i=0;i<phormoneLevel.length;i++) {
                phormoneLevel[i] = 0.00861821851041004;
            }
            a= fullStationDao.getFullStations("16ABNAGCB");
            b = fullStationDao.getFullStations("16BEKDRST");
            stringsArriver = ligneDao.getLigneArrive();
            stations = new ArrayList<>();
            for (int i =0;i<stringsArriver.size();i++){
                stations.add(fullStationDao.getFullStations(stringsArriver.get(i)));
            }

            ACO_TOTAL_TIME = System.currentTimeMillis();
            bestAnt = null;
            best = 999;
            for (int i = 0; i < 3; i++) {

                ants = new ArrayList<>();
                for (int j = 0; j < 10; j++) {
                    ants.add(new Ant(stations,lignes,a,b));
                }
                for (Ant ant : ants) {
                   Log.e("ant_value",""+ant.getId());
                   ant.walk();
                   if(ant != null){
                       List<FullStation> visited = ant.getVisitedStation();
                       /*double some=0.0,distance;
                       for(int j = 0;j<visited.size();j++){
                           if(j<visited.size()-1){
                               distance = CalculationByDistance(new LatLng(visited.get(j).getStop_lat(),
                                               visited.get(j).getStop_lon()),
                                       new LatLng(visited.get(j+1).getStop_lat(),
                                               visited.get(j+1).getStop_lon()));
                               some = distance + some;
                           }
                       }*/
                        if (best > Ant.cout) {
                            bestAnt = ant;
                            best = Ant.cout;
                        }
                        List<LigneDB> solu = bestAnt.getSolutionLigne();
                        for (LigneDB ligneDB : solu){
                            Log.e("hamada",""+ligneDB.getLname()+"       "+ligneDB.getLtype());
                        }
                        Log.e("hamada_value",""+best);
                    }
                }
                updatePheromone(bestAnt,lignes);
        }
       double temps = System.currentTimeMillis()-ACO_TOTAL_TIME;
       Log.e("temps_d'execution",""+temps);
       return bestAnt;
    }
    public void updatePheromone(Ant bestAnt,List<LigneDB> ligneDBS) {
        List<LigneDB> solutionLigne = null;

        for (LigneDB edge : ligneDBS) {
            double delta = 0;
            int l = edge.getPhormone_index();
            double pheromone = phormoneLevel[l];
           // Log.e("phormone",""+pheromone);
            if (bestAnt != null)
                solutionLigne =  bestAnt.getSolutionLigne();

            int i;
            for(i = 0;i<solutionLigne.size();i++){
                if(solutionLigne.get(i).getLid().equals(edge.getLid())){
                    break;
                }
            }
            if (i!=solutionLigne.size()){
                delta = 1.0 / solutionLigne.size();
            }

            //evaporation = 0.9325204351890948
            pheromone = (1.0 - 0.842637634868853) * pheromone + 0.842637634868853 * delta;
           // Log.e("phormone + evaporation",""+pheromone);
            if (pheromone < MIN_PHEROMONE)
                pheromone = MIN_PHEROMONE;

            if (pheromone > MAX_PHEROMONE)
                pheromone = MAX_PHEROMONE;

            phormoneLevel[l] = pheromone;
           // Log.e("phormone level final",""+phormoneLevel[l]);
        }
    }

    public double getBest() {
        return best;
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
        return kmInDec;
    }
}
