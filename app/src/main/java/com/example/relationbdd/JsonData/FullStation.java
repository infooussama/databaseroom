package com.example.relationbdd.JsonData;

import java.util.List;

public class FullStation {

    private String  scode;
    private String  sname;
    private double  stop_lat;
    private double  stop_lon;
    private int  nbl;
    private String  post_name;
    private List<Ligne> lines;
    private List<Transfert> transfers;

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

    public int getNbl() {
        return nbl;
    }

    public void setNbl(int nbl) {
        this.nbl = nbl;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public List<Ligne> getLines() {
        return lines;
    }

    public void setLines(List<Ligne> lines) {
        this.lines = lines;
    }

    public List<Transfert> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfert> transfers) {
        this.transfers = transfers;
    }
}
