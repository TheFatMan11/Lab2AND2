package com.thuydev.lab2and2.DTO;

public class Todolist_DTO {
    private int id ;
    private String tiTle;
    private String conTent;
    private String daTe;
    private String tyPe;
    private int trangthai;




    public Todolist_DTO(int id, String tiTle, String conTent, String daTe, String tyPe, int trangthai) {
        this.id = id;
        this.tiTle = tiTle;
        this.conTent = conTent;
        this.daTe = daTe;
        this.tyPe = tyPe;
        this.trangthai = trangthai;
    }

    public Todolist_DTO(String tiTle, String conTent, String daTe, String tyPe) {

        this.tiTle = tiTle;
        this.conTent = conTent;
        this.daTe = daTe;
        this.tyPe = tyPe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getTiTle() {
        return tiTle;
    }

    public void setTiTle(String tiTle) {
        this.tiTle = tiTle;
    }

    public String getConTent() {
        return conTent;
    }

    public void setConTent(String conTent) {
        this.conTent = conTent;
    }

    public String getDaTe() {
        return daTe;
    }

    public void setDaTe(String daTe) {
        this.daTe = daTe;
    }

    public String getTyPe() {
        return tyPe;
    }

    public void setTyPe(String tyPe) {
        this.tyPe = tyPe;
    }
}
