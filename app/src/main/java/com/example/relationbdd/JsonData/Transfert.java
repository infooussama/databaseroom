package com.example.relationbdd.JsonData;

import java.util.List;

public class Transfert {
    private String scode ;
    private String sname;
    private int nbl;
    private int dist;
    List<Ligne> lines;

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getNbl() {
        return nbl;
    }

    public void setNbl(int nbl) {
        this.nbl = nbl;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public List<Ligne> getLines() {
        return lines;
    }

    public void setLines(List<Ligne> lines) {
        this.lines = lines;
    }
}
