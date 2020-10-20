package com.example.relationbdd.model;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

public class TransfertAndFullStation {
    @Embedded
    public FullStation fullStation;
    @Relation(
            parentColumn = "scode",
            entityColumn = "tscode"
    )
    public List<TransfertDB> transfertDBList;
}
