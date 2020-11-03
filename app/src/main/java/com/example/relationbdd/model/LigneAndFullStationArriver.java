package com.example.relationbdd.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class LigneAndFullStationArriver {
    @Embedded
    public LigneDB ligneDB;
    @Relation(
            parentColumn = "id_arrive",
            entityColumn = "scode"
    )
    public List<FullStation> fullStations;
}
