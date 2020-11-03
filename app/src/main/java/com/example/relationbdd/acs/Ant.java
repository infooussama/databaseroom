package com.example.relationbdd.acs;

import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneDB;

import java.util.ArrayList;
import java.util.List;

public class Ant {
   /* private FullStation currentNode;

    private ArrayList<FullStation> visitedStation = new ArrayList<>();

    private ArrayList<LigneDB> visitedLigne = new ArrayList<>();

    private ArrayList<FullStation> alreadyChecked = new ArrayList<>();

    private ArrayList<FullStation> solutionStation = new ArrayList<>();

    private ArrayList<LigneDB> solutionLine = new ArrayList<>();

    private double tour_lenght;

    private double cost;

    RoomDB roomDB;

    FullStationDao fullStationDao;

    List<FullStation> fullStations;

    LigneDao ligneDao;

    List<LigneDB> ligneDBS;

    //creation et affectation pour chaque fourmi un depart
    public Ant(FullStation source) {
        currentNode = source;
        visitedStation.add(source);
        tour_lenght = 0;
        cost = -1;
    }
    //réinitialisation des parametre
    public void reset(FullStation source) {
        currentNode = source;
        visitedStation = new ArrayList<>();
        visitedLigne = new ArrayList<>();
        alreadyChecked = new ArrayList<>();
        visitedStation.add(source);
        tour_lenght = 0;
    }

    public void newSolution() {
        if (cost < 0 || tour_lenght < cost) {
            solutionLine = new ArrayList<>(visitedLigne);
            solutionStation = new ArrayList<>(solutionStation);
            cost = tour_lenght;
        }
    }

    public boolean hasVisitedLine(LigneDB ligne) {
        return visitedLigne.contains(ligne);
    }

    public boolean hasVisitedStation(FullStation fullStation) {
        return visitedStation.contains(fullStation);
    }

    public void addVisitedStation(FullStation fullStation) {
        visitedStation.add(fullStation);
    }

    public void addVisitedLine(LigneDB ligne) {
        visitedLigne.add(ligne);
    }

    public FullStation getCurrentNode() {
        return currentNode;
    }

    public ArrayList<FullStation> getVisitedStation() {
        return visitedStation;
    }

    public ArrayList<LigneDB> getVisitedLigne() {
        return visitedLigne;
    }

    public ArrayList<FullStation> getAlreadyChecked() {
        return alreadyChecked;
    }

    public ArrayList<FullStation> getSolutionStation() {
        return solutionStation;
    }

    public ArrayList<LigneDB> getSolutionLigne() {
        return solutionLine;
    }

    public double getTour_lenght() {
        return tour_lenght;
    }

    public double getCost() {
        return cost;
    }

    public void setCurrentNode(FullStation currentNode) {
        this.currentNode = currentNode;
    }

    public void setVisitedStation(ArrayList<FullStation> visitedStation) {
        this.visitedStation = visitedStation;
    }

    public void setVisitedLigne(ArrayList<LigneDB> visitedLigne) {
        this.visitedLigne = visitedLigne;
    }

    public void setTour_lenght(double tour_lenght) {
        this.tour_lenght = tour_lenght;
    }

    private FullStation getNextStation(Ant ant) {

        double prob = 0;
        fullStationDao = roomDB.fullStationDao();
        if (ant.getCurrentNode().getLigneDBS().size() == 0)
            return null;

        //List<Tuple<Double, Node>> probList = new ArrayList<Tuple<Double, Node>>();

        for (LigneDB neighbor : ant.getCurrentNode().getLigneDBS()) {
            int k = 0;
            String scode = ant.getCurrentNode().getScode();
                fullStations = fullStationDao.getLineFullstations(neighbor.getLid());
                for( int j = 0 ; j<fullStations.size() ; j++){
                    if(fullStations.get(j).getScode() == scode){
                        k=j+1;
                        break;
                    }
                }
                if (ant.hasVisitedStation(fullStations.get(k)))
                    continue;

            prob = compute_probabilitie(ant.getCurrentNode(), fullStations.get(k), ant);

            //probList.add(new Tuple<Double, Node>(prob, neighbor.getDestiny()));
        }

        return choice(probList);
    }

    private double compute_probabilitie(FullStation current, FullStation next, Ant k) {

        ligneDao = roomDB.ligneDao();

        ligneDBS = ligneDao.getFullStationLignes(current.getScode());

        if (k.getVisitedStation().contains(next))
            return 0;

        for (int i = 0; i<ligneDBS.size();i++){
            for( int j=0 ; j<ligneDBS.get(i).getFullStations().size(); j++){
                if(ligneDBS.get(i).getFullStations().get(j).getScode() == current.getScode()){
                    LigneDB edge = ligneDBS.get(i);
                }
            }
        }

        double pheromone = 0;
        double heuristic = 0;
        double sum = 0;

        for (LigneDB succ : current.getEdges().values()) {
            if (k.hasVisitedNode(succ.getDestiny()) || badpath.containsKey(succ.getDestiny().getName()))
                continue;
            sum += Math.pow(succ.getPheromone(), alpha) * Math.pow(1.0/succ.getWeight(weight), beta);
        }

        if (sum == 0)
            sum = 1;

        pheromone = Math.pow(edge.getPheromone(), alpha);
        heuristic = Math.pow(1.0/edge.getWeight(weight), beta);

        double prob = (pheromone * heuristic) / sum;

        return prob;
    }*/
   public static int DISTANCE_MAX;	//set in constructor so it is relative to graph size
    private static final double PHEROMONE_MAX = 10.0;
    private FullStation currentPos;
    private final int id = 0;
    private static int numAnts = 0;
    private ArrayList<LigneDB> path;
    FullStation end;

}
