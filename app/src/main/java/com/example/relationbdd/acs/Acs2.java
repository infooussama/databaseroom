
package com.example.relationbdd.acs;

import android.graphics.Point;
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

public class Acs2 extends AppCompatActivity{

    List<Ant2> ants;
    List<FullStation> stations;
    RoomDB roomDB;
    FullStationDao fullStationDao;
    LigneDao ligneDao;
    List<LigneDB> lignes;
    List<String> stringsArriver;
    Ant2 bestAnt;
    double best;
    public static double[] phormoneLevel;
    private static final double MAX_PHEROMONE = 1;
    private static double MIN_PHEROMONE = 0.001;
    FullStation a,b;
    public static long ACO_TOTAL_TIME = 0;
    public static double temps = 0;
    public static double evaporation = 0.85;
    public static double phormoneinitial = 0.08;
    public final static double alpha = 0.4;
    public final static double beta = 0.8;
    double rayon;
    LatLng middel;
    public Ant2 calcule(String depart,String arriver){
            roomDB = RoomDB.getInstance(this);
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
            rayon = CalculationByDistance(new LatLng(a.getStop_lat(),a.getStop_lon()),
                    new LatLng(b.getStop_lat(),b.getStop_lon()));
            ACO_TOTAL_TIME = System.currentTimeMillis();
            middel = midPoint(a.getStop_lat(),a.getStop_lon(),b.getStop_lat(),b.getStop_lon());
            bestAnt = null;
            best = 10000;
            for (int i = 0; i < 10; i++) {
                Ant2 bestItAnt = null;
                double bestItAntCout = 999;
                ants = new ArrayList<>();
                for (int j = 0; j < 6; j++) {
                    ants.add(new Ant2(rayon,a,b,middel));
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
            //Log.e("temps_d'execution",""+temps);
            /**List<LigneDB> ligneDBS = bestAnt.getSolutionLigne();
            for(LigneDB ligneDB : ligneDBS){
                int i = ligneDB.getPhormone_index();
                //Log.e("phormone_lvl puissance alpha",""+Math.pow(phormoneLevel[i],Ant.alpha));
            }*/

            return bestAnt;
    }
    public void updatePheromone(Ant2 bestAnt,List<LigneDB> ligneDBS) {
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
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        //int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        //int meterInDec = Integer.valueOf(newFormat.format(meter));
        return meter ;
    }

    public static LatLng midPoint(double lat1, double lon1, double lat2, double lon2){

        double dLon = Math.toRadians(lon2 - lon1);

        //convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

        LatLng latLng = new LatLng(Math.toDegrees(lat3),Math.toDegrees(lon3));

        return latLng;
    }

}
