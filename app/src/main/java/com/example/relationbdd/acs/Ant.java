package com.example.relationbdd.acs;

import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneDB;

import java.util.ArrayList;
import java.util.List;

public class Ant {
   public static int DISTANCE_MAX;	//set in constructor so it is relative to graph size
    private static final double PHEROMONE_MAX = 10.0;
    private FullStation currentPos;
    private int id = 0;
    private static int numAnts = 0;
    private ArrayList<LigneDB> traveledTo,path;
    FullStation start,end;
    public Ant(Graphf graph, FullStation start,FullStation end){
        //if(terminus.contains(start)&&terminus.contains(end))
        DISTANCE_MAX = graph.graph().size();//nbr de station dans le graph(terminus 500-->200)
        //else if(terminus.contains(start))
        DISTANCE_MAX = graph.graph().size()+1;
        //else if(terminus.contains(end))
        DISTANCE_MAX = graph.graph().size()+1;
        //else
        DISTANCE_MAX = graph.graph().size()+2;

        this.start = start;
        this.end = end;
        id = numAnts;
        numAnts++;
        currentPos = start;
        traveledTo = new ArrayList<>();
        path = new ArrayList<>();
    }

    public int walk(){
        int status = move();
        while(status == 0){
            status = move();
        }
        return status;
    }

    public int move(){
        return 0;
    }
}
