package com.example.relationbdd.acs;

import android.os.Bundle;
import android.util.Log;

import com.example.relationbdd.CalculeItineraire;
import com.example.relationbdd.JsonData.Ligne;
import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneDB;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class Ant extends AppCompatActivity {
    private List<FullStation> stations,visitedStation;
    private List<LigneDB> lignes,solutionLigne,visitedLigne;
    private FullStation currentStations,end,start;
    private static final double Q_0 = 0.1;
    private final int id;
    private static int numAnts = 0;
    RoomDB roomDB;
    FullStationDao fullStationDao;
    LigneDao ligneDao;
    List<LigneDB> possibleMoves;
    List<LigneDB> dbs;
    public static double cout;
    double distance;
    public Ant(List<FullStation> stations, List<LigneDB> lignes, FullStation start, FullStation end){
        this.stations = stations;
        this.lignes = lignes;
        this.start = start;
        this.currentStations = start;
        this.end = end;
        roomDB = RoomDB.getInstance(this);
        fullStationDao = roomDB.fullStationDao();
        ligneDao = roomDB.ligneDao();
        solutionLigne = new ArrayList<>();
        visitedLigne = new ArrayList<>();
        visitedStation = new ArrayList<>();
        visitedStation.add(currentStations);
        id = numAnts;
        numAnts++;
        dbs = ligneDao.getFullStationLignes(end.getScode());
        cout = 0;
    }

    public void reset(FullStation source) {
        currentStations = source;
        solutionLigne = new ArrayList<>();
        visitedLigne = new ArrayList<>();
        visitedStation = new ArrayList<>();
        visitedStation.add(currentStations);
        cout = 0;
    }

    public void walk(){
        while(!(currentStations.getScode().equals(end.getScode()))){
            move();
        }
    }

    public int getId() {
        return id;
    }

    public void move(){
        List<LigneDB> adjacentLignes = ligneDao.getFullStationLignes(currentStations.getScode());
        possibleMoves = new ArrayList<>();
        for(LigneDB e : adjacentLignes){
            int i;
            for(i = 0;i<dbs.size();i++){
                if(dbs.get(i).getLid().equals(e.getLid()) && !e.getId_arrive().equals(currentStations.getScode())){
                    break;
                }
            }

            if(i!=dbs.size()){
                currentStations = end;
                visitedLigne.add(e);
                solutionLigne.add(e);
                visitedStation.add(fullStationDao.getFullStations(e.getId_arrive()));
                return;
            }

            int k;
            for(k = 0;k<visitedLigne.size();k++){
                if(visitedLigne.get(k).getLid().equals(e.getLid())){
                    break;
                }
            }

            int j;
            for(j = 0;j<visitedStation.size();j++){
                if(visitedStation.get(j).getScode().equals(e.getId_arrive())){
                    break;
                }
            }

            if(k==visitedLigne.size() && j == visitedStation.size() && !e.getId_arrive().equals(currentStations.getScode())){
                possibleMoves.add(e);
            }

        }

        LigneDB next = null;
        if(possibleMoves.size() == 0){
            reset(start);
            move();
        }
        else{
            List<Double> prob2 = compute_probabilitie(possibleMoves);
            next = choice(possibleMoves,prob2);
            String sa = next.getId_arrive();
            FullStation fullStation = fullStationDao.getFullStations(sa);
            visitedStation.add(fullStation);
            distance = 0;
            distance = CalculationByDistance(new LatLng(currentStations.getStop_lat(),
                            currentStations.getStop_lon()),
                    new LatLng(fullStation.getStop_lat(),
                            fullStation.getStop_lon()));
            cout = cout + distance;
            String s = oppositeEnd(next,currentStations);
            currentStations = fullStationDao.getFullStations(s);
            visitedLigne.add(next);
            solutionLigne.add(next);
        }
    }

    public List<FullStation> getVisitedStation() {
        return visitedStation;
    }

    public String oppositeEnd(LigneDB ligneDB, FullStation fullStation){

        if(ligneDB.getId_arrive() == fullStation.getScode()){
            return ligneDB.getId_depart();

        }else{

            return ligneDB.getId_arrive();
        }
    }

    private List<Double> compute_probabilitie(List<LigneDB> possiblemove) {

        List<Double> probList = new ArrayList<>();
        double pheromone = 0;
        double heuristic = 0;
        double sum = 0;
        double pourcentage = 0;
        for(LigneDB e : possiblemove){
            int k = e.getPhormone_index();
                if(e.getLtype().equals("M") || e.getLtype().equals("T")){
                    pourcentage = 0.03;
                }
                if(e.getLtype().equals("B")){
                    pourcentage = 0.01;
                }
                if(e.getLtype().equals("L")){
                    pourcentage = 0.03;
                }
                if(e.getLtype().equals("P")){
                    pourcentage = 0.9;
                }
                FullStation arrive = fullStationDao.getFullStations(e.getId_arrive());
                sum += Math.pow(Acs.phormoneLevel[k], 2.0040331845764) * Math.pow(1.0/ (CalculationByDistance(
                        new LatLng(end.getStop_lat(),end.getStop_lon()),
                        new LatLng(arrive.getStop_lat(),arrive.getStop_lon()))*pourcentage),2.5138228981984807
                );
            }
        if (sum == 0)
            sum = 1;
        double some = 0;
        for(LigneDB e : possiblemove){
            int k = e.getPhormone_index();
            if(e.getLtype().equals("M") || e.getLtype().equals("T")){
                pourcentage = 0.03;
            }
            if(e.getLtype().equals("B")){
                pourcentage = 0.01;
            }
            if(e.getLtype().equals("L")){
                pourcentage = 0.03;
            }
            if(e.getLtype().equals("P")){
                pourcentage = 0.9;
            }
            FullStation arrive = fullStationDao.getFullStations(e.getId_arrive());
            pheromone = Math.pow(Acs.phormoneLevel[k],2.0040331845764);
            heuristic = Math.pow(1.0/ (CalculationByDistance(
                    new LatLng(end.getStop_lat(),end.getStop_lon()),
                    new LatLng(arrive.getStop_lat(),arrive.getStop_lon()))*pourcentage),2.5138228981984807
            );
            Log.e("phormone puissance alpha",""+pheromone);

            Log.e("distance",""+1.0/ (CalculationByDistance(
                    new LatLng(end.getStop_lat(),end.getStop_lon()),
                    new LatLng(arrive.getStop_lat(),arrive.getStop_lon()))*pourcentage));
            Log.e("heuristic",""+heuristic);

            double prob = (pheromone * heuristic) / sum;
            //Log.e("prob",""+prob);
            some = prob + some;
            //Log.e("some",""+some);
            probList.add(prob);

        }
        Log.e("some",""+some);
        return probList;
    }

    private LigneDB choice(List<LigneDB> possiblemove, List<Double> probList) {
        Random rnd = new Random();
        double st = rnd.nextDouble();
        double r = rnd.nextDouble();

        if (probList.size() == 1)
            return possiblemove.get(0);

        if (st < Q_0) {
            double max = 0;
            int indiceMax = 0;
            for(int i = 0;i<probList.size();i++){
                if(max < probList.get(i)){
                    max = probList.get(i);
                    indiceMax=i;
                }
            }
            return possiblemove.get(indiceMax);
        }
        double total = 0;
        for (int j = 0; j < probList.size(); j++){
            total += probList.get(j);
            if (total >= r)
                return possiblemove.get(j);
        }

        return possiblemove.get(possiblemove.size()-1);
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
    public List<LigneDB> getSolutionLigne() {
        return solutionLigne;
    }
}

