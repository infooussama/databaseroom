package com.example.relationbdd.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class LigneAndFullStationArriver {
    @Embedded
    public LigneDB ligneDB;
    @Relation(
            parentColumn = "lid",
            entityColumn = "id_arrive"
    )
    public FullStation fullStation;
}
