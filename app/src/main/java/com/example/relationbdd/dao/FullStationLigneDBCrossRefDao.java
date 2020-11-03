package com.example.relationbdd.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.relationbdd.model.FullStationLigneDBCrossRef;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface FullStationLigneDBCrossRefDao {
    @Insert(onConflict = IGNORE)
    void insert(FullStationLigneDBCrossRef FullStationLigneDBCrossRef);

    @Delete
    void delete(FullStationLigneDBCrossRef FullStationLigneDBCrossRef);

    @Query("SELECT * FROM FullStationLigneDBCrossRef")
    List<FullStationLigneDBCrossRef> getAllFullStationLigneDBCrossRef();

    @Query("SELECT COUNT(*) FROM FullStationLigneDBCrossRef")
    public int getCountFullStationLigneDBCrossRef();

}
