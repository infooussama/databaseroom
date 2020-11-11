package com.example.relationbdd.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class LigneDB {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "lid")
    private String lid ;

    @ColumnInfo(name="ltype")
    private String ltype;

    @ColumnInfo(name="operator_id")
    private String operator_id;

    @ColumnInfo(name="lnum")
    private String lnum;

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

    @ColumnInfo(name="id_depart")
    private String id_depart;

    @ColumnInfo(name="id_arrive")
    private String id_arrive;

    @ColumnInfo(name="phormone_index")
    private int phormone_index;

    public LigneDB(@NonNull String lid, String ltype, String operator_id, String lnum, String lname, int nbs, String op_id, String op_name, String op_color, String id_arrive) {
        this.lid = lid;
        this.ltype = ltype;
        this.operator_id = operator_id;
        this.lnum = lnum;
        this.lname = lname;
        this.nbs = nbs;
        this.op_id = op_id;
        this.op_name = op_name;
        this.op_color = op_color;
        this.id_arrive = id_arrive;
    }

    public String getId_depart() {
        return id_depart;
    }

    public void setId_depart(String id_depart) {
        this.id_depart = id_depart;
    }

    public String getId_arrive() {
        return id_arrive;
    }

    public void setId_arrive(String id_arrive) {
        this.id_arrive = id_arrive;
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

    public String getLnum() {
        return lnum;
    }

    public void setLnum(String lnum) {
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

    public int getPhormone_index() {
        return phormone_index;
    }

    public void setPhormone_index(int phormone_index) {
        this.phormone_index = phormone_index;
    }

}
