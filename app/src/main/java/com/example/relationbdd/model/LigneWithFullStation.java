package com.example.relationbdd.model;

import com.example.relationbdd.JsonData.Ligne;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

public class LigneWithFullStation {

    @Embedded
    public FullStation fullStation;
    @Relation(
            parentColumn = "scode",
            entity = LigneDB.class,
            entityColumn = "lid",
            associateBy = @Junction(
                    value = LigneAndFullStation.class,
                    parentColumn = "fullstationscode",
                    entityColumn = "lignelid")
            )
   /* @Relation(
            parentColumn = "lid",
            entityColumn = "scode",
            associateBy = @Junction(value = LigneAndFullStation.class,parentColumn = "lid",entityColumn = "scode")
    )*/
    public List<LigneDB> ligneDBS;
}
