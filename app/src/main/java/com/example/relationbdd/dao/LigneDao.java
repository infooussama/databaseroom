package com.example.relationbdd.dao;

import com.example.relationbdd.model.LigneDB;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface LigneDao {

    @Insert(onConflict = IGNORE)
    void insert(LigneDB ligneDB);

    @Delete
    void delete(LigneDB ligneDB);

    @Query("SELECT * FROM LigneDB")
    List<LigneDB> getLigneDbs();

    @Query("SELECT * FROM LigneDB INNER JOIN FullStationLigneDBCrossRef ON LigneDB.lid = FullStationLigneDBCrossRef.lid WHERE FullStationLigneDBCrossRef.scode=:scode")
    public List<LigneDB> getFullStationLignes(String scode);

}
