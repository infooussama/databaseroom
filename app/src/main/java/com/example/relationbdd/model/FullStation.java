package com.example.relationbdd.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity
public class FullStation implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "scode")
    @NonNull
    private String scode ;

    @ColumnInfo(name="stop_lat")
    private double stop_lat;

    @ColumnInfo(name="stop_lon")
    private double stop_lon;

    @ColumnInfo(name="post_name")
    private String post_name;

    @ColumnInfo(name="hasTrransfer")
    private boolean hasTrransfer;

    @Embedded
    private StationDB stationDB;

    @Ignore
    private List<LigneDB> ligneDBS;

    public FullStation(@NonNull String scode, double stop_lat, double stop_lon, String post_name, StationDB stationDB) {
        this.scode = scode;
        this.stop_lat = stop_lat;
        this.stop_lon = stop_lon;
        this.post_name = post_name;
        this.stationDB = stationDB;
    }

    public boolean isHasTrransfer() {
        return hasTrransfer;
    }

    public void setHasTrransfer(boolean hasTrransfer) {
        this.hasTrransfer = hasTrransfer;
    }

    @NonNull
    public String getScode() {
        return scode;
    }

    public void setScode(@NonNull String scode) {
        this.scode = scode;
    }

    public double getStop_lat() {
        return stop_lat;
    }

    public void setStop_lat(double stop_lat) {
        this.stop_lat = stop_lat;
    }

    public double getStop_lon() {
        return stop_lon;
    }

    public void setStop_lon(double stop_lon) {
        this.stop_lon = stop_lon;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public StationDB getStationDB() {
        return stationDB;
    }

    public void setStationDB(StationDB stationDB) {
        this.stationDB = stationDB;
    }

    public List<LigneDB> getLigneDBS() {
        return ligneDBS;
    }

    public void setLigneDBS(List<LigneDB> ligneDBS) {
        this.ligneDBS = ligneDBS;
    }
}
