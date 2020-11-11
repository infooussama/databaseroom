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
    double[] phormoneLevel;
    int i;boolean check;

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
        phormoneLevel = new double[lignes.size()];

        for(int i=0;i<phormoneLevel.length;i++){
            phormoneLevel[i] = 1;
        }
    }

    public void walk(){
        while(!(currentStations.getScode().equals(end.getScode()))){
            move();
        }
        for (LigneDB e : solutionLigne){
            Log.e("fetouhi",""+e.getLname());
        }
    }

    public void move(){
        List<LigneDB> adjacentLignes = ligneDao.getFullStationLignes(currentStations.getScode());
        List<LigneDB> possibleMoves = new ArrayList<>();
        for(LigneDB e : adjacentLignes){
            //if the finish is adjacent go there
            if(e.getId_arrive().equals(end.getScode())){
                currentStations = end;
                visitedLigne.add(e);
                solutionLigne.add(e);
                dropPheremone(solutionLigne);
                return;
            }
            /*for(int i = 0;i<visitedLigne.size();i++){
                if(!visitedLigne.get(i).getLname().equals(e.getLid())){
                }
            }*/
            /*i=0; check=false;
            while (i<visitedLigne.size()){
                if(visitedLigne.get(i).getLid().equals(e.getLid())){
                    check = true;
                }
            }
            if (check == false){
                possibleMoves.add(e);
            }*/
            if(!visitedLigne.contains(e)){
                possibleMoves.add(e);
            }
        }

        LigneDB next;

        if(possibleMoves.size() == 0){
            next = choosePath(adjacentLignes);
        }
        else{
            next = choosePath(possibleMoves);
        }

        String s = oppositeEnd(next,currentStations);
        currentStations = fullStationDao.getFullStations(s);
        visitedLigne.add(next);
        solutionLigne.add(next);
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
           int k = e.getPhormone_index();

           totalPheremone += phormoneLevel[k];
        }
        Random random = new Random();
        double max = totalPheremone * random.nextDouble();
        double sum = 0;
        int i = 0;
        while(sum < max){
            int m = lignes.get(i).getPhormone_index();
            sum += phormoneLevel[m];
            i++;
        }
        return lignes.get(i-1);
    }

    public void dropPheremone(List<LigneDB> path){
        for(LigneDB e : path){
            int l = e.getPhormone_index();
            phormoneLevel[l] += (Q_0 /path.size());
        }
    }

}

