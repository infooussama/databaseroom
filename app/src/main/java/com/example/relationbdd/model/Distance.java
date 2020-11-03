package com.example.relationbdd.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Distance {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idDistace")
    @NonNull
    private int idDistace ;

    @ColumnInfo(name="scodeD")
    private String scodeD;

    @ColumnInfo(name="scodeA")
    private String scodeA;

    @ColumnInfo(name="distance")
    @NonNull
    private double distance;

    public Distance(String scodeD, String scodeA, double distance) {
        this.scodeD = scodeD;
        this.scodeA = scodeA;
        this.distance = distance;
    }

    public int getIdDistace() {
        return idDistace;
    }

    public void setIdDistace(int idDistace) {
        this.idDistace = idDistace;
    }

    public String getScodeD() {
        return scodeD;
    }

    public void setScodeD(String scodeD) {
        this.scodeD = scodeD;
    }

    public String getScodeA() {
        return scodeA;
    }

    public void setScodeA(String scodeA) {
        this.scodeA = scodeA;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
