package com.example.relationbdd.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TransfertDB {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdTransfer")
    private int IdTransfer;

    @ColumnInfo(name = "tscode")
    @NonNull
    private String tscode ;

    @ColumnInfo(name="dist")
    private int dist;

    public int getIdTransfer() {
        return IdTransfer;
    }

    public void setIdTransfer(int idTransfer) {
        IdTransfer = idTransfer;
    }

    public TransfertDB(@NonNull String tscode, int dist) {
        this.tscode = tscode;
        this.dist = dist;
    }

    @NonNull
    public String getTscode() {
        return tscode;
    }

    public void setTscode(@NonNull String tscode) {
        this.tscode = tscode;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

}
