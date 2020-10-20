package com.example.relationbdd.dao;

import com.example.relationbdd.model.LigneAndFullStation;
import com.example.relationbdd.model.LigneDB;
import com.example.relationbdd.model.LigneWithFullStation;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import static androidx.room.OnConflictStrategy.ABORT;
import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface LigneDao {

    @Insert(onConflict = IGNORE)
    void insert(LigneDB ligneDB);

    @Delete
    void delete(LigneDB ligneDB);

    @Query("SELECT * FROM LigneDB")
    List<LigneDB> getLigneDbs();
/*
    @Transaction
    @Query("SELECT * FROM LigneDB")
    public List<LigneWithFullStation> getLigneWithFullStations();

*/
}
