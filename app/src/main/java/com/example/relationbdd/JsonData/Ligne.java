package com.example.relationbdd.JsonData;

import java.util.List;

public class Ligne {

    private String  lid;
    private String  ltype;
    private String  operator_id;
    private String  lnum;
    private String  lname;
    private int  nbs;
    private String  op_id;
    private String  op_name;
    private String  op_color;
    private Station terminus;

    public Station getStation() {
        return terminus;
    }

    public void setStation(Station terminus) {
        this.terminus = terminus;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
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
}
