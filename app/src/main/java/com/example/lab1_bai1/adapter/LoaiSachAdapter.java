package com.example.lab1_bai1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab1_bai1.R;

import com.example.lab1_bai1.SachActivity;
import com.example.lab1_bai1.dao.LoaiSachDAO;
import com.example.lab1_bai1.dao.SachDAO;
import com.example.lab1_bai1.fragment.QuanLyLoaiSachFragment;
import com.example.lab1_bai1.model.LoaiSach;
import com.example.lab1_bai1.model.Sach;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {

    static Context context;
    ArrayList<LoaiSach> listLoaiSach;
    QuanLyLoaiSachFragment quanLyLoaiSachFragment;
    LoaiSachDAO loaiSachDAO;
    SachDAO sachDAO;
    DatabaseReference mData;
    ArrayList<Sach> listSachThu = new ArrayList<>();

    public LoaiSachAdapter(Context c, ArrayList<LoaiSach> listLoaiSach, QuanLyLoaiSachFragment quanLyLoaiSachFragment) {
        this.context = c;
        this.listLoaiSach = listLoaiSach;
        this.quanLyLoaiSachFragment = quanLyLoaiSachFragment;
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView tvTenLoai;

        public ViewHolder(View itemView){
            super(itemView);

            tvTenLoai = itemView.findViewById(R.id.tvRowLoaiSach);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.cardview_loai_sach,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final LoaiSach loaiSach = listLoaiSach.get(position);

        mData = FirebaseDatabase.getInstance().getReference("Sach");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSachThu.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Sach item = data.getValue(Sach.class);
                    listSachThu.add(item);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.tvTenLoai.setText(loaiSach.tenLoaiSach);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SachActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("tenloai",holder.tvTenLoai.getText().toString());
                bundle.putString("maloai",loaiSach.maLoaiSach);

                intent.putExtras(bundle);
                ((Activity)context).startActivity(intent);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                loaiSachDAO = new LoaiSachDAO(context);
                sachDAO = new SachDAO(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa không ?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                for (int j = 0 ; j < listSachThu.size(); j ++){
                                    if (listSachThu.get(j).maLoai.equals(loaiSach.maLoaiSach)){
                                        sachDAO.delete(listSachThu.get(j));
                                    }
                                }

                                loaiSachDAO.delete(listLoaiSach.get(position));
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listLoaiSach.size();
    }
}
