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
import com.example.lab1_bai1.model.HoaDon;

import java.util.ArrayList;

public class HoaDonAdapter extends ArrayAdapter {

    Context context;
    int src;
    ArrayList<HoaDon> listHD;

    public HoaDonAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        this.context = context;
        this.src = resource;
        this.listHD = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(getContext())
                .inflate(R.layout.row_hoa_don,null);

        ViewHolder holder = new ViewHolder();
        holder.tvMaHD = convertView.findViewById(R.id.tvRowMaHoaDon);
        holder.tvNgayLapHD = convertView.findViewById(R.id.tvRowNgayLapHoaDon);

        HoaDon a = listHD.get(position);

        holder.tvMaHD.setText(a.maHD);
        holder.tvNgayLapHD.setText(a.ngayMua);

        return  convertView;

    }

    public class ViewHolder{
        TextView tvMaHD, tvNgayLapHD;
    }

}
