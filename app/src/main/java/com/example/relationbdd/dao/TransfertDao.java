package com.example.relationbdd.dao;

import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.TransfertDB;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TransfertDao {
    @Insert(onConflict = REPLACE)
    void insert(TransfertDB transfertDB);

    @Delete
    void delete(TransfertDB transfertDB);

    @Query("SELECT * FROM FullStation")
    List<FullStation> getAllFullStation();
}
