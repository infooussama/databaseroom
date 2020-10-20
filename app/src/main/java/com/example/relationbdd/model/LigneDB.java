package com.example.relationbdd.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class LigneDB {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idLigne")
    private int idLigne;

   // @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "lid")
    @NonNull
    private String lid ;

    @ColumnInfo(name="ltype")
    private String ltype;

    @ColumnInfo(name="operator_id")
    private String operator_id;

    @ColumnInfo(name="lnum")
    private int lnum;

    @ColumnInfo(name="lname")
    private String lname;

    @ColumnInfo(name="nbs")
    private int nbs;

    @ColumnInfo(name="op_id")
    private String op_id;

    @ColumnInfo(name="op_name")
    private String op_name;

    @ColumnInfo(name="op_color")
    private String op_color;

    public LigneDB(@NonNull String lid, String ltype, String operator_id, int lnum, String lname, int nbs, String op_id, String op_name, String op_color) {
        this.lid = lid;
        this.ltype = ltype;
        this.operator_id = operator_id;
        this.lnum = lnum;
        this.lname = lname;
        this.nbs = nbs;
        this.op_id = op_id;
        this.op_name = op_name;
        this.op_color = op_color;
    }

    public int getIdLigne() {
        return idLigne;
    }

    public void setIdLigne(int idLigne) {
        this.idLigne = idLigne;
    }

    @NonNull
    public String getLid() {
        return lid;
    }

    public void setLid(@NonNull String lid) {
        this.lid = lid;
    }

    public String getLtype() {
        return ltype;
    }

    public void setLtype(String ltype) {
        this.ltype = ltype;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public int getLnum() {
        return lnum;
    }

    public void setLnum(int lnum) {
        this.lnum = lnum;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getNbs() {
        return nbs;
    }

    public void setNbs(int nbs) {
        this.nbs = nbs;
    }

    public String getOp_id() {
        return op_id;
    }

    public void setOp_id(String op_id) {
        this.op_id = op_id;
    }

    public String getOp_name() {
        return op_name;
    }

    public void setOp_name(String op_name) {
        this.op_name = op_name;
    }

    public String getOp_color() {
        return op_color;
    }

    public void setOp_color(String op_color) {
        this.op_color = op_color;
    }
}
