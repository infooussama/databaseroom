package com.example.relationbdd.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"scode", "idLigne"})
public class LigneAndFullStation {
  @NonNull
  public String scode;
  @NonNull
  public String idLigne;

    public LigneAndFullStation(@NonNull String scode, @NonNull String idLigne) {
        this.scode = scode;
        this.idLigne = idLigne;
    }
}
