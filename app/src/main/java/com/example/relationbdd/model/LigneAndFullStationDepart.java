package com.example.relationbdd.model;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class LigneAndFullStationDepart {
    @Embedded
    public LigneDB ligneDB;
    @Relation(
            parentColumn = "lid",
            entityColumn = "id_depart"
    )
    public FullStation fullStation;
}
