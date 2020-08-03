package com.example.lab1_bai1.model;

public class HoaDonChiTiet {

    public String maHDCT;
    public String tenSach;
    public int soLuong;
    public float giaTien;
    public  float tongTien;

    public HoaDonChiTiet(){

    }

    public HoaDonChiTiet(String maHDCT, String tenSach, int soLuong, float giaTien, float tongTien) {
        this.maHDCT = maHDCT;
        this.tenSach = tenSach;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.tongTien = tongTien;
    }

    @Override
    public String toString() {
        return "HoaDonChiTiet{" +
                "maHDCT='" + maHDCT + '\'' +
                ", tenSach='" + tenSach + '\'' +
                ", soLuong=" + soLuong +
                ", giaTien=" + giaTien +
                ", tongTien=" + tongTien +
                '}';
    }
}
