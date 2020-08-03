package com.example.lab1_bai1.model;

public class HoaDon{
     public String maHD;
     public String ngayMua;

    public HoaDon(){
    }

    public HoaDon(String maHD, String ngayMua) {
        this.maHD = maHD;
        this.ngayMua = ngayMua;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHD='" + maHD + '\'' +
                ", ngayMua='" + ngayMua + '\'' +
                '}';
    }
}
