package com.example.relationbdd.dao;

import com.example.relationbdd.model.Distance;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneDB;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DistanceDao {

    @Insert(onConflict = REPLACE)
    void insert(Distance distance);

    @Delete
    void delete(Distance distance);

    @Query("SELECT DISTINCT  * FROM [Distance] WHERE scodeD =:scode ORDER BY distance")
    public List<Distance> getDistances(String scode);

}
