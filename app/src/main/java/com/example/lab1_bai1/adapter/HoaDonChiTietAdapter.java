package com.example.lab1_bai1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab1_bai1.R;
import com.example.lab1_bai1.model.HoaDonChiTiet;

import java.util.List;

public class HoaDonChiTietAdapter extends ArrayAdapter {

    Context context;
    List<HoaDonChiTiet> list;
    int src;

    public HoaDonChiTietAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.src = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context)
                .inflate(R.layout.row_hdct,null,false);

        ViewHolder v = new ViewHolder();
        v.maHDCT = convertView.findViewById(R.id.maHDCT);
        v.tenSach = convertView.findViewById(R.id.tenSach);
        v.soLuong = convertView.findViewById(R.id.soLuong);
        v.donGia = convertView.findViewById(R.id.donGia);
        v.thanhTien = convertView.findViewById(R.id.thanhTien);

        HoaDonChiTiet a = list.get(position);

        v.maHDCT.setText(a.maHDCT);
        v.tenSach.setText(a.tenSach);
        v.soLuong.setText(a.soLuong+"");
        v.donGia.setText(a.giaTien+"");
        v.thanhTien.setText(a.tongTien+"");


        return convertView;
    }

    class ViewHolder {
        TextView maHDCT,tenSach,soLuong,donGia,thanhTien;
    }
}
