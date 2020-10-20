package com.example.relationbdd.database;

import android.content.Context;

import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.dao.LigneAndFullStationDao;
import com.example.relationbdd.dao.LigneDao;
import com.example.relationbdd.dao.StationDao;
import com.example.relationbdd.dao.TransfertDao;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneAndFullStation;
import com.example.relationbdd.model.LigneDB;
import com.example.relationbdd.model.StationDB;
import com.example.relationbdd.model.TransfertDB;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {StationDB.class, FullStation.class, TransfertDB.class, LigneDB.class, LigneAndFullStation.class},version = 4)
public abstract class RoomDB extends RoomDatabase {

    public abstract StationDao stationDao();
    public abstract FullStationDao fullStationDao();
    public abstract TransfertDao transfertDao();
    public abstract LigneDao ligneDao();
    public abstract LigneAndFullStationDao ligneAndFullStationDao();


    private static RoomDB database;
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDB getInstance(Context context){

        if(database == null){

            database = Room.databaseBuilder(context.getApplicationContext()
                    ,RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

}
