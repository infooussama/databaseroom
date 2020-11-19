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

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Acs extends AppCompatActivity{

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
    private static final double MIN_PHEROMONE = 0.001;
    FullStation a,b;
    public static long ACO_TOTAL_TIME = 0;
    public static double temps = 0;
    public static double evaporation;
    public static double phormoneinitial;
    public static double alpha;
    public static double beta;
    public Ant calcule(String depart,String arriver,String alpha,String beta, String phormonelvl,String evap,String iteration,String numant){
            this.phormoneinitial = Double.parseDouble(phormonelvl);
            this.evaporation = Double.parseDouble(evap);
            this.alpha=Double.parseDouble(alpha);
            this.beta=Double.parseDouble(beta);
            roomDB = RoomDB.getInstance(this);
            int it = Integer.parseInt(iteration);
            int num = Integer.parseInt(numant);
            fullStationDao = roomDB.fullStationDao();
            ligneDao = roomDB.ligneDao();
            lignes = ligneDao.getLigneDbs();
            phormoneLevel = new double[lignes.size()];
            for(int i=0;i<phormoneLevel.length;i++) {
                phormoneLevel[i] = phormoneinitial;
            }
            a= fullStationDao.getFullStations(depart);
            b = fullStationDao.getFullStations(arriver);
            stringsArriver = ligneDao.getLigneArrive();
            stations = new ArrayList<>();
            for (int i =0;i<stringsArriver.size();i++){
                stations.add(fullStationDao.getFullStations(stringsArriver.get(i)));
            }

            ACO_TOTAL_TIME = System.currentTimeMillis();
            bestAnt = null;
            best = 10000;
            for (int i = 0; i < it; i++) {
                Ant bestItAnt = null;
                double bestItAntCout = 999;
                ants = new ArrayList<>();
                for (int j = 0; j < num; j++) {
                    ants.add(new Ant(stations,a,b));
                    ants.get(j).walk();
                    if (bestItAntCout > ants.get(j).cout) {
                        bestItAnt = ants.get(j);
                        bestItAntCout = ants.get(j).cout;
                        List<LigneDB> solu = bestItAnt.getSolutionLigne();
                        for (LigneDB ligneDB : solu){
                            Log.e("hamada",""+ligneDB.getLname()+"       "+ligneDB.getLtype());
                        }
                        Log.e("hamada_value",""+bestItAntCout);
                    }
                }
                if (best > bestItAnt.cout) {
                    bestAnt = bestItAnt;
                    best = bestItAnt.cout;
                }
                updatePheromone(bestItAnt,lignes);
            }
            temps = System.currentTimeMillis()-ACO_TOTAL_TIME;
            Log.e("temps_d'execution",""+temps);
            List<LigneDB> ligneDBS = bestAnt.getSolutionLigne();
            for(LigneDB ligneDB : ligneDBS){
                int i = ligneDB.getPhormone_index();
                Log.e("phormone_lvl puissance alpha",""+Math.pow(phormoneLevel[i],Ant.alpha));
                Log.e("phormone_lvl puissance beta",""+Math.pow(phormoneLevel[i],Ant.beta));
            }

            return bestAnt;
    }
    public void updatePheromone(Ant bestAnt,List<LigneDB> ligneDBS) {
        List<LigneDB> solutionLigne = null;

        if (bestAnt != null) {
            solutionLigne = bestAnt.getSolutionLigne();
            for (LigneDB edge : ligneDBS) {
                double delta = 0;
                int l = edge.getPhormone_index();
                double pheromone = phormoneLevel[l];
                // Log.e("phormone",""+pheromone);

                int i;
                for (i = 0; i < solutionLigne.size(); i++) {
                    if (solutionLigne.get(i).getLid().equals(edge.getLid())) {
                        delta = 1.0 / solutionLigne.size();
                        break;
                    }
                }

                //evaporation = 0.9325204351890948
                pheromone = (1.0 - evaporation) * pheromone + evaporation * delta;
                // Log.e("phormone + evaporation",""+pheromone);
                if (pheromone < MIN_PHEROMONE)
                    pheromone = MIN_PHEROMONE;

                if (pheromone > MAX_PHEROMONE)
                    pheromone = MAX_PHEROMONE;

                phormoneLevel[l] = pheromone;
                // Log.e("phormone level final",""+phormoneLevel[l]);
            }
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
        return valueResult ;
    }

}
