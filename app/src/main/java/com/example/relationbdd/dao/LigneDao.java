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
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface LigneDao {

    @Insert(onConflict = IGNORE)
    void insert(LigneDB ligneDB);

    @Delete
    void delete(LigneDB ligneDB);

    @Update
    void update(LigneDB ligneDB);

    @Query("SELECT * FROM LigneDB")
    List<LigneDB> getLigneDbs();

    @Query("DELETE FROM LigneDB WHERE ltype=:type")
    void removeLigneTelepherique(String type);

    @Query("SELECT * FROM LigneDB WHERE ltype NOT LIKE :p")
    List<LigneDB> getLigneDbsWithoutP(String p);

    @Query("SELECT * FROM LigneDB INNER JOIN FullStationLigneDBCrossRef ON LigneDB.lid = FullStationLigneDBCrossRef.lid WHERE FullStationLigneDBCrossRef.scode=:scode")
    public List<LigneDB> getFullStationLignes(String scode);

    @Query("SELECT * FROM LigneDB WHERE LigneDB.lid LIKE :lid")
    public List<LigneDB> getStartAndArrival(String lid);

    @Transaction
    @Query("SELECT DISTINCT * FROM LigneDB")
    public List<LigneAndFullStationDepart> getLigneAndFullStationDeparts();

    @Transaction
    @Query("SELECT DISTINCT id_arrive FROM LigneDB")
    public List<String> getLigneArrive();

    @Transaction
    @Query("SELECT DISTINCT id_depart FROM LigneDB")
    public List<String> getLigneDepart();

    @Transaction
    @Query("SELECT * FROM LigneDB WHERE LigneDB.lid=:lid")
    public LigneAndFullStationArriver getLigneAndFullStationArrivers(String lid);

    @Query("UPDATE LigneDB SET phormone_index=:index WHERE lid = :id")
    void updateindex(int index, String id);

}
