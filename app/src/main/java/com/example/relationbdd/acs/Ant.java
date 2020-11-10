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

public class Ant extends AppCompatActivity {
    private List<FullStation> stations;
    private List<LigneDB> lignes,solutionLigne,visitedLigne;
    private FullStation currentStations,end;
    private static final double Q_0 = 10;
    private final int id;
    private static int numAnts = 0;
    RoomDB roomDB;
    FullStationDao fullStationDao;
    LigneDao ligneDao;

    public Ant(List<FullStation> stations,List<LigneDB> lignes, FullStation start, FullStation end){
        this.stations = stations;
        this.lignes = lignes;
        this.currentStations = start;
        this.end = end;
        solutionLigne = new ArrayList<>();
        visitedLigne = new ArrayList<>();
        id = numAnts;
        numAnts++;
        roomDB = RoomDB.getInstance(this);
        fullStationDao = roomDB.fullStationDao();
        ligneDao = roomDB.ligneDao();
    }

    public void walk(){
        while(currentStations != end){
            move();
        }
    }

    public void move(){
        List<LigneDB> adjacentLignes = ligneDao.getFullStationLignes(currentStations.getScode());
        List<LigneDB> possibleMoves = new ArrayList<>();

        for(LigneDB e : adjacentLignes){
            //if the finish is adjacent go there
            if(e.getId_arrive() == end.getScode()){
                currentStations = end;
                visitedLigne.add(e);
                solutionLigne.add(e);
                dropPheremone(solutionLigne);
            }
            if(!visitedLigne.contains(e)){
                possibleMoves.add(e);
            }
        }

        LigneDB next;

        if(possibleMoves.size() == 0){
            next = choosePath(adjacentLignes);
        }
        //otherwise choose one of the yet to be traveled edges
        else{
            next = choosePath(possibleMoves);
        }

        String s = oppositeEnd(next,currentStations);
        currentStations = fullStationDao.getFullStations(s);
        visitedLigne.add(next);
        solutionLigne.add(next);
        Log.e("solution","B:"+next.getId_arrive());
    }

    public String oppositeEnd(LigneDB ligneDB,FullStation fullStation){
        /*if(ligneDB.getId_arrive() != fullStation.getScode() && ligneDB.getId_depart() != fullStation.getScode()){
            throw new IllegalArgumentException("Edge " + this + " did not contain node " + fullStation);
        }*/

        if(ligneDB.getId_arrive() == fullStation.getScode()){
            return ligneDB.getId_depart();

        }else{

            return ligneDB.getId_arrive();
        }
    }

    public LigneDB choosePath(List<LigneDB> lignes){
        double totalPheremone = 0;
        for(LigneDB e : lignes){
            totalPheremone += e.getPhormoneLevel();
        }
        Random random = new Random();
        double max = totalPheremone * random.nextDouble();
        double sum = 0;
        int i = 0;
        while(sum < max){
            sum += lignes.get(i).getPhormoneLevel();
            i++;
        }
        return lignes.get(i-1);
    }

    public void dropPheremone(List<LigneDB> path){
        for(LigneDB e : path){
            e.setPhormoneLevel(Q_0 / path.size());
        }
    }
}

