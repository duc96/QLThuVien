package com.example.lab1_bai1.model;

import java.io.Serializable;

public class NguoiDung implements Serializable {
    public String tenDangNhap;
    public String matKhau;
    public int sdt;

    public NguoiDung(String tenDangNhap, String matKhau, int sdt) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.sdt = sdt;
    }

    public NguoiDung() {
    }

    public NguoiDung(String tenDangNhap, String matKhau) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    @Override
    public String toString() {
        return "NguoiDung{" +
                "tenDangNhap='" + tenDangNhap + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", sdt=" + sdt +
                '}';
    }

}
