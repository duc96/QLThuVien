package com.example.lab1_bai1.model;

public class tenSoluong {
    public String ten;
    public int soluong;

    public tenSoluong(String ten, int soluong) {
        this.ten = ten;
        this.soluong = soluong;
    }

    @Override
    public String toString() {
        return "tenSoluong{" +
                "ten='" + ten + '\'' +
                ", soluong=" + soluong +
                '}';
    }
}
