package com.example.relationbdd.model;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class LigneAndFullStationDepart {
    @Embedded
    public LigneDB ligneDB;
    @Relation(
            parentColumn = "id_depart",
            entityColumn = "scode"
    )
    public List<FullStation> fullStations;
}
