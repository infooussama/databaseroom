package com.example.relationbdd.dao;

import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.FullStationWithLigne;
import com.example.relationbdd.model.LigneAndFullStation;
import com.example.relationbdd.model.LigneWithFullStation;
import com.example.relationbdd.model.TransfertAndFullStation;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import static androidx.room.OnConflictStrategy.REPLACE;
@Dao
public interface LigneAndFullStationDao {

    @Insert(onConflict = REPLACE)
    void insert(LigneAndFullStation ligneAndFullStation);

    @Delete
    void delete(LigneAndFullStation ligneAndFullStation);
/*
    @Query("SELECT * FROM LigneAndFullStation")
    List<LigneAndFullStation> getLingAndFullStations();

    @Query("SELECT * FROM FullStation")
    List<LigneWithFullStation> getLigneWithFullStations();
*/
    @Transaction
    @Query("SELECT * FROM FullStation")
    public List<FullStationWithLigne> getFullStationWithLignes();


}
