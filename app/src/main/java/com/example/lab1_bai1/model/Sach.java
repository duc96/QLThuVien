package com.example.lab1_bai1.model;

public class Sach {
    public String tenSach;
    public String maSach;
    public String maLoai;
    public String baoVe;
    public float giaBia;
    public int soLuong;

    public Sach() {
    }

    public Sach(String tenSach, String maSach, String maLoai, String baoVe, float giaBia, int soLuong) {
        this.tenSach = tenSach;
        this.maSach = maSach;
        this.maLoai = maLoai;
        this.baoVe = baoVe;
        this.giaBia = giaBia;
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "Sach{" +
                "tenSach='" + tenSach + '\'' +
                ", maSach='" + maSach + '\'' +
                ", maLoai='" + maLoai + '\'' +
                ", tacGia='" + baoVe + '\'' +
                ", giaBia=" + giaBia +
                ", soLuong=" + soLuong +
                '}';
    }
}
