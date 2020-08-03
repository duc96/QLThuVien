package com.example.lab1_bai1.model;


public class LoaiSach {

    public String maLoaiSach;
    public String tenLoaiSach;
    public String moTa;
    public int vitri;

    public LoaiSach(){

    }

    public LoaiSach(String maLoaiSach, String tenLoaiSach, String moTa, int vitri) {
        this.maLoaiSach = maLoaiSach;
        this.tenLoaiSach = tenLoaiSach;
        this.moTa = moTa;
        this.vitri = vitri;
    }

    @Override
    public String toString() {
        return "LoaiSach{" +
                "maLoaiSach='" + maLoaiSach + '\'' +
                ", tenLoaiSach='" + tenLoaiSach + '\'' +
                ", moTa='" + moTa + '\'' +
                ", vitri=" + vitri +
                '}';
    }
}
