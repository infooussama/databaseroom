package com.example.relationbdd.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StationDB {

    @PrimaryKey(autoGenerate = true)
    private int idStation;

    @ColumnInfo(name="sname")
    private String sname;

    @ColumnInfo(name="nbl")
    private int nbl;

    @ColumnInfo(name="stype")
    private String stype;

    @ColumnInfo(name="cid")
    private int cid;

    @ColumnInfo(name="cname")
    private String cname;

    public StationDB(String sname, int nbl, String stype, int cid, String cname) {
        this.sname = sname;
        this.nbl = nbl;
        this.stype = stype;
        this.cid = cid;
        this.cname = cname;
    }

    public int getIdStation() {
        return idStation;
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }

    public String getSname() {
        return sname;
    }

    public int getNbl() {
        return nbl;
    }

    public String getStype() {
        return stype;
    }

    public int getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setNbl(int nbl) {
        this.nbl = nbl;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
