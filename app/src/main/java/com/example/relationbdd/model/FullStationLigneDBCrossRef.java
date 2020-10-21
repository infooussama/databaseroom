package com.example.relationbdd.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(primaryKeys = {"scode", "lid"},
        foreignKeys={
            @ForeignKey(
                    entity=FullStation.class,
                    parentColumns="scode",
                    childColumns="scode"),
            @ForeignKey(
                    entity=LigneDB.class,
                    parentColumns="lid",
                    childColumns="lid")},
        indices={
                @Index(value="scode"),
                @Index(value="lid")})
public class FullStationLigneDBCrossRef {

    @NonNull
    public String scode;
    @NonNull
    public String lid;

    public FullStationLigneDBCrossRef(@NonNull String scode,@NonNull String lid) {
        this.scode = scode;
        this.lid = lid;
    }

    @NonNull
    public String getScode() {
        return scode;
    }

    public void setScode(@NonNull String scode) {
        this.scode = scode;
    }

    @NonNull
    public String getLid() {
        return lid;
    }

    public void setLid(@NonNull String lid) {
        this.lid = lid;
    }
}
