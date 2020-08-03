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
import com.example.lab1_bai1.fragment.QuanLyNguoiDungFragment;
import com.example.lab1_bai1.model.NguoiDung;

import java.util.List;

public class NguoiDungAdapter extends ArrayAdapter {

    TextView tvTen,tvMatKhau,tvSDT;
    Context context;
    List<NguoiDung> listNguoiDung;
    QuanLyNguoiDungFragment nguoiDungFragment;

    public NguoiDungAdapter(@NonNull Context context,List<NguoiDung> listNguoiDung, QuanLyNguoiDungFragment frgNguoiDung) {
        super(context, 0,listNguoiDung);
        this.context = context;
        this.listNguoiDung = listNguoiDung;
        this.nguoiDungFragment = frgNguoiDung;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if(v == null){

            v = LayoutInflater.from(context)
                    .inflate(R.layout.row_nguoi_dung,parent,false);
        }

        final NguoiDung nd = listNguoiDung.get(position);

        if(nd != null){
            tvTen = v.findViewById(R.id.tvTen);
            tvMatKhau = v.findViewById(R.id.tvMatKhau);
            tvSDT = v.findViewById(R.id.tvSDT);

            tvTen.setText(nd.tenDangNhap+"");
            tvMatKhau.setText(nd.matKhau+"");
            tvSDT.setText(nd.sdt+"");
        }



        return v;
    }
}
