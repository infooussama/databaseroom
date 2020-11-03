package com.example.relationbdd.dao;

import com.example.relationbdd.model.LigneAndFullStationArriver;
import com.example.relationbdd.model.LigneAndFullStationDepart;
import com.example.relationbdd.model.LigneDB;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import static androidx.room.OnConflictStrategy.IGNORE;

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

    @Transaction
    @Query("SELECT * FROM LigneDB WHERE LigneDB.lid=:lid")
    public List<LigneAndFullStationDepart> getLigneAndFullStationDeparts(String lid);

    @Transaction
    @Query("SELECT * FROM LigneDB WHERE LigneDB.lid=:lid")
    public List<LigneAndFullStationArriver> getLigneAndFullStationArrivers(String lid);

}
