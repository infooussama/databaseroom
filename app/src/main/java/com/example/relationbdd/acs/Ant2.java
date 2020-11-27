package com.example.relationbdd.acs;

import android.util.Log;

import com.example.relationbdd.CalculeItineraire;
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
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class Ant2 extends AppCompatActivity implements Serializable {
    private List<FullStation> stations,visitedStation;
    private List<LigneDB> solutionLigne,visitedLigne;
    private FullStation currentStations,end,start;
    public final static double Q_0 = 0.4;
    private final int id;
    private static int numAnts = 0;
    RoomDB roomDB;
    FullStationDao fullStationDao;
    LigneDao ligneDao;
    List<LigneDB> possibleMoves;
    List<LigneDB> dbs;
    public double cout;
    public double dis;
    public double prixTotal;
    double distance,distance2;
    int co=0;
    public static double alpha = Acs2.alpha;
    public static double beta  = Acs2.beta;
    double prix = 0;
    double rayon,rayonInitial;
    LatLng middel,middelInitial;
    public Ant2(double rayon,FullStation start, FullStation end, LatLng middel){
        this.start = start;
        this.currentStations = start;
        this.end = end;
        this.rayon = rayon + rayon/2;
        rayonInitial = rayon + rayon/2;
        this.middel = middel;
        middelInitial = middel;
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
        distance = 0;
        distance2 = 0;
        cout = 0;
        dis = 0;
        prixTotal=0;
    }

    public void reset(FullStation source) {
        currentStations = source;
        solutionLigne = new ArrayList<>();
        visitedLigne = new ArrayList<>();
        visitedStation = new ArrayList<>();
        visitedStation.add(currentStations);
        distance = 0;
        distance2 = 0;
        prix=0;
        cout = 0;
        dis = 0;
        prixTotal=0;
        rayon = rayonInitial;
        middel = middelInitial;
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
        /**Log.e("middel long -1","" +middel.getLongitude());
        Log.e("middel lat -1","" +middel.getLatitude());
        Log.e("rayon -1","" +rayon);*/
        possibleMoves = new ArrayList<>();
        double pourcentage = 0;
        double sum = 0;
        double sum2 = 0;
        List<Double> problist = new ArrayList<>();
        List<Double> problist2 = new ArrayList<>();
        int indiceMaxProba = 0;
        for(LigneDB e : adjacentLignes){
            int i;
            for(i = 0;i<dbs.size();i++){
                if(dbs.get(i).getLid().equals(e.getLid()) && !e.getId_arrive().equals(currentStations.getScode())){
                    break;
                }
            }

            if(i!=dbs.size()){
                distance = CalculationByDistance(new LatLng(currentStations.getStop_lat(),
                                currentStations.getStop_lon()),
                        new LatLng(end.getStop_lat(),
                                end.getStop_lon()));
                if(dbs.get(i).getLtype().equals("T")){
                    prix = 40.00;
                }
                if(dbs.get(i).getLtype().equals("M")){
                    prix = 50.00;
                }
                if(dbs.get(i).getLtype().equals("B")){
                    if(distance < 5){
                        prix = 20.00;
                    }
                    if(distance > 5 && distance < 7){
                        prix = 30.00;
                    }
                    if(distance > 7 && distance < 10){
                        prix = 40.00;
                    }
                    if(distance > 10){
                        prix = 50.00;
                    }
                }
                if(dbs.get(i).getLtype().equals("p")){
                    prix = 00.00;
                }
                cout = cout + (distance * 1) + 2 +(prix * 0.2);
                prixTotal = prixTotal + prix;
                dis = dis + distance;
                currentStations = end;
                visitedLigne.add(e);
                solutionLigne.add(e);
                visitedStation.add(end);
                return;
            }

            int k;
            for(k = 0;k<visitedLigne.size();k++){
                if(visitedLigne.get(k).getLid().equals(e.getLid()) || visitedStation.get(k).getScode().equals(e.getId_arrive())){
                    break;
                }
            }

            /*int j;
            for(j = 0;j<visitedStation.size();j++){
                if(visitedStation.get(j).getScode().equals(e.getId_arrive())){
                    break;
                }
            }*/
            //Log.e("MEGA", "  "+visitedLigne.size()+ " "+ visitedStation.size());
            double proba = 0;

            if(k == visitedLigne.size() && !visitedStation.get(visitedStation.size() - 1).getScode().equals(e.getId_arrive()) && !e.getId_arrive().equals(currentStations.getScode())){
                FullStation arriverLigne = fullStationDao.getFullStations(e.getId_arrive());
                double calculedistance =CalculationByDistance(new LatLng(arriverLigne.getStop_lat(),arriverLigne.getStop_lon()),
                        new LatLng(middel.getLatitude(),middel.getLongitude()));
                if(calculedistance<=rayon) {
                    possibleMoves.add(e);

                    int j = e.getPhormone_index();
                    if (e.getLtype().equals("M") || e.getLtype().equals("T")) {
                        pourcentage = 0.15;
                    }
                    if (e.getLtype().equals("B")) {
                        pourcentage = 0.25;
                    }
                    if (e.getLtype().equals("L")) {
                        pourcentage = 0.15;
                    }
                    if (e.getLtype().equals("P")) {
                        pourcentage = 0.3;
                    }
                   // FullStation arrive = fullStationDao.getFullStations(e.getId_arrive());
                    proba = Math.pow(Acs2.phormoneLevel[k], alpha) * Math.pow(1.0 / (CalculationByDistance(
                            new LatLng(end.getStop_lat(), end.getStop_lon()),
                            new LatLng(arriverLigne.getStop_lat(), arriverLigne.getStop_lon())) * pourcentage), beta
                    );
                    sum += proba;
                    problist.add(proba);
                    if (proba >= problist.get(indiceMaxProba)) {
                        indiceMaxProba = problist.size() - 1;
                    }
                }
            }
            //possibleMoves.get(possibleMoves.size() - 1).setProbability(proba);

        }

        LigneDB next = null;

        if(possibleMoves.size() == 0){
            reset(start);
            //walk();
            //Log.e("resetttttttttttttttttttttttttttttt",""+co);
            //co++;
        }
        else{
            //List<Double> prob2 = compute_probabilitie(possibleMoves, sum);
            next = choice(possibleMoves, problist, sum, indiceMaxProba);
            double proba2 = 0;
            indiceMaxProba = 0;
            if(next.getLtype().equals("M") || next.getLtype().equals("T")){
                List<FullStation> fullstationsWithTransfers = fullStationDao.getLineFullstationsWithTransfers(next.getLid());
                for(FullStation fullStation : fullstationsWithTransfers){
                    proba2 = Math.pow(Acs2.phormoneLevel[next.getPhormone_index()], alpha) * Math.pow(1.0/ (CalculationByDistance(
                            new LatLng(end.getStop_lat(),end.getStop_lon()),
                            new LatLng(fullStation.getStop_lat(),fullStation.getStop_lon()))*0.15),beta
                    );
                    /**Log.e("partie 1 proba",""+Acs.phormoneLevel[next.getPhormone_index()]+"-----"+(1.0/ (CalculationByDistance(
                            new LatLng(end.getStop_lat(),end.getStop_lon()),
                            new LatLng(fullStation.getStop_lat(),fullStation.getStop_lon())))));

                    Log.e("partie 2 proba",""+Math.pow(Acs.phormoneLevel[next.getPhormone_index()], alpha) +"----"
                            +Math.pow(1.0/ (CalculationByDistance(
                            new LatLng(end.getStop_lat(),end.getStop_lon()),
                            new LatLng(fullStation.getStop_lat(),fullStation.getStop_lon()))),beta
                    ));*/
                    sum2 += proba2;
                    problist2.add(proba2);
                    if(proba2 >= problist2.get(indiceMaxProba)){
                        indiceMaxProba = problist2.size()-1;
                    }
                }
                FullStation fullStationTransfert = choiceFullStation(fullstationsWithTransfers,problist2,sum2,indiceMaxProba);
                visitedStation.add(fullStationTransfert);
                //distance = 0;
                    if(next.getLtype().equals("T")){
                        prix = 40.00;
                    }
                    if(next.getLtype().equals("M")){
                        prix = 50.00;
                    }
                distance = CalculationByDistance(new LatLng(end.getStop_lat(),
                                end.getStop_lon()),
                        new LatLng(fullStationTransfert.getStop_lat(),
                                fullStationTransfert.getStop_lon()));
                distance2 = CalculationByDistance(new LatLng(currentStations.getStop_lat(),
                                currentStations.getStop_lon()),
                        new LatLng(fullStationTransfert.getStop_lat(),
                                fullStationTransfert.getStop_lon()));
                dis = dis + distance2;
                prixTotal = prixTotal + prix;
                cout = cout + (distance * 1) +2+ (prix * 0.2);
                currentStations = fullStationTransfert;
                middel = midPoint(currentStations.getStop_lat(),currentStations.getStop_lon(),end.getStop_lat(),end.getStop_lon());
                rayon = (CalculationByDistance(new LatLng(currentStations.getStop_lat(),currentStations.getStop_lon())
                        ,new LatLng(end.getStop_lat(),end.getStop_lon())));
                rayon= rayon + rayon/2;
                /**Log.e("middel long 1","" +middel.getLongitude());
                Log.e("middel lat 1","" +middel.getLatitude());
                Log.e("rayon 1","" +rayon);*/
            }else {
                String sa = next.getId_arrive();
                FullStation fullStation = fullStationDao.getFullStations(sa);
                visitedStation.add(fullStation);
               // distance = 0;
                distance = CalculationByDistance(new LatLng(end.getStop_lat(),
                                end.getStop_lon()),
                        new LatLng(fullStation.getStop_lat(),
                                fullStation.getStop_lon()));
                distance2 = CalculationByDistance(new LatLng(currentStations.getStop_lat(),
                                currentStations.getStop_lon()),
                        new LatLng(fullStation.getStop_lat(),
                                fullStation.getStop_lon()));
                if(next.getLtype().equals("T")){
                    prix = 40.00;
                }
                if(next.getLtype().equals("M")){
                    prix = 50.00;
                }
                if(next.getLtype().equals("B")){
                    //prix = distance * 1.5;
                    if(distance < 5){
                        prix = 20.00;
                    }
                    if(distance > 5 && distance < 7){
                        prix = 30.00;
                    }
                    if(distance > 7 && distance < 10){
                        prix = 40.00;
                    }
                    if(distance > 10){
                        prix = 50.00;
                    }
                }
                if(next.getLtype().equals("P")){
                    prix = 00.00;
                }
                dis = dis + distance2;
                prixTotal = prixTotal + prix;
                cout = cout + (distance * 1) +2 + (prix * 0.2);
                String s = oppositeEnd(next,currentStations);
                currentStations = fullStationDao.getFullStations(s);
                middel = midPoint(currentStations.getStop_lat(),currentStations.getStop_lon(),end.getStop_lat(),end.getStop_lon());
                rayon = (CalculationByDistance(new LatLng(currentStations.getStop_lat(),currentStations.getStop_lon())
                        ,new LatLng(end.getStop_lat(),end.getStop_lon())));
                rayon= rayon + rayon/2;
                /**Log.e("middel long","" +middel.getLongitude());
                Log.e("middel lat","" +middel.getLatitude());
                Log.e("rayon","" +rayon);*/
            }
            Acs2.phormoneLevel[next.getPhormone_index()] = Acs2.phormoneLevel[next.getPhormone_index()] * (1 - Acs2.evaporation) + Acs2.phormoneinitial * Acs2.evaporation;
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

    private LigneDB choice(List<LigneDB> possiblemove, List<Double> probList, double sum, int indiceMax) {
        Random rnd = new Random();
        double st = rnd.nextDouble();
        double r = rnd.nextDouble();

        if (probList.size() == 1)
            return possiblemove.get(0);

        if (st < Q_0) {
            return possiblemove.get(indiceMax);
            /**double max = 0;
            int indiceMax = 0;
            for(int i = 0;i<probList.size();i++){
                if(max < probList.get(i)){
                    max = probList.get(i);
                    indiceMax=i;
                }
            }
            return possiblemove.get(indiceMax);*/
        }
        double total = 0;
        for (int j = 0; j < probList.size() - 1; j++){
            total += probList.get(j)/sum;
            if (total >= r)
                return possiblemove.get(j);
        }

        return possiblemove.get(possiblemove.size()-1);
    }

    private FullStation choiceFullStation(List<FullStation> possiblemove, List<Double> probList2, double sum2, int indiceMax) {
        Random rnd = new Random();
        double st = rnd.nextDouble();
        double r = rnd.nextDouble();

        if (probList2.size() == 1)
            return possiblemove.get(0);

        if (st < Q_0) {
            return possiblemove.get(indiceMax);
            /**double max = 0;
            int indiceMax = 0;
            for(int i = 0;i<probList2.size();i++){
                if(max < probList2.get(i)){
                    max = probList2.get(i);
                    indiceMax=i;
                }
            }
            return possiblemove.get(indiceMax);*/
        }
        double total = 0;
        for (int j = 0; j < probList2.size() - 1; j++){
            total += probList2.get(j)/sum2;
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
        //int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        //int meterInDec = Integer.valueOf(newFormat.format(meter));
        return meter ;
    }
    public List<LigneDB> getSolutionLigne() {
        return solutionLigne;
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

