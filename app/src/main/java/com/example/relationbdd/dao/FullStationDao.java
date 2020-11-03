package com.example.relationbdd.dao;

import com.example.relationbdd.model.FullStation;
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

    @Query("SELECT * FROM [FullStation] WHERE stype =:bus")
    public List<FullStation> getFullStationsBus(String bus);

    @Transaction
    @Query("SELECT * FROM FullStation")
    public List<TransfertAndFullStation> getStationWithTransfert();

    @Query("SELECT * FROM FullStation INNER JOIN FullStationLigneDBCrossRef ON FullStation.scode = FullStationLigneDBCrossRef.scode WHERE FullStationLigneDBCrossRef.lid=:lid")
    public List<FullStation> getLineFullstations(String lid);

    @Query("SELECT COUNT(*) FROM FullStation")
    public int getCountFullstations();

}
