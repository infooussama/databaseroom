package com.example.relationbdd.dao;

import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.FullStationWithLigne;
import com.example.relationbdd.model.TransfertAndFullStation;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FullStationDao {
    @Insert(onConflict = REPLACE)
    void insert(FullStation fullStation);

    @Delete
    void delete(FullStation fullStation);

    @Query("SELECT * FROM FullStation")
    List<FullStation> getAllStations();

    @Transaction
    @Query("SELECT * FROM FullStation")
    public List<TransfertAndFullStation> getStationWithTransfert();

    @Transaction
    @Query("SELECT * FROM FullStation")
    public List<FullStationWithLigne> getFullStationWithLignes();


}
