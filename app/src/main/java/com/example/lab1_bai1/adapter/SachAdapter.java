package com.example.lab1_bai1.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab1_bai1.R;
import com.example.lab1_bai1.model.LoaiSach;
import com.example.lab1_bai1.model.Sach;

import java.util.ArrayList;

import static com.example.lab1_bai1.fragment.QuanLyLoaiSachFragment.ls;

public class SachAdapter extends ArrayAdapter {

    Context context;
    int src;
    ArrayList<Sach> listSach;

    public SachAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        this.context = context;
        this.src = resource;
        this.listSach = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context)
                .inflate(R.layout.row_sach, parent, false);

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.tvTenSach = convertView.findViewById(R.id.tvTenSach);
        viewHolder.tvMaSach = convertView.findViewById(R.id.tvMaSach);
        viewHolder.tvMaLoai = convertView.findViewById(R.id.tvMaLoai);
        viewHolder.tvGiaBan = convertView.findViewById(R.id.tvGiaBan);
        viewHolder.tvSoLuong = convertView.findViewById(R.id.tvSoLuong);
        viewHolder.tvTacGia = convertView.findViewById(R.id.tvTacGia);

        Sach a = listSach.get(position);

        viewHolder.tvTenSach.setText(a.tenSach);
        viewHolder.tvMaSach.setText("Ma sach: " + a.maSach);
        viewHolder.tvMaLoai.setText("Ma loai: " + a.maLoai);
        viewHolder.tvGiaBan.setText("Gia ban: " + a.giaBia + "");
        viewHolder.tvSoLuong.setText("So luong: " + a.soLuong + "");
        viewHolder.tvTacGia.setText("Bao ve: " + a.baoVe);

        return convertView;
    }

    public class ViewHolder {
        TextView tvTenSach, tvMaSach, tvMaLoai, tvGiaBan, tvSoLuong, tvTacGia;
    }
}
