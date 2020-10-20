package com.example.relationbdd.model;

import com.example.relationbdd.JsonData.Ligne;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

public class FullStationWithLigne {
    @Embedded
    public FullStation fullStation;
    @Relation(parentColumn = "scode",
            entityColumn = "idLigne"
    )
    public List<LigneDB> ligneDBS;

}
