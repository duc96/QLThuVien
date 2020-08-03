package com.example.lab1_bai1.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lab1_bai1.R;
import com.example.lab1_bai1.adapter.LoaiSachAdapter;
import com.example.lab1_bai1.dao.LoaiSachDAO;
import com.example.lab1_bai1.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanLyLoaiSachFragment extends Fragment {

    FloatingActionButton floatButton;
    public static List<LoaiSach> ls = new ArrayList<>();
    LoaiSachAdapter adapter;
    LinearLayoutManager mLayoutManager;
    DatabaseReference mData;

    LoaiSachDAO dao;
    RecyclerView rc;

    public QuanLyLoaiSachFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_loai_sach, container, false);

        floatButton = view.findViewById(R.id.floatButton);
        rc = view.findViewById(R.id.rcvLoaiSach);

        dao = new LoaiSachDAO(getActivity(),this);

        mLayoutManager = new LinearLayoutManager(getActivity());
        rc.setLayoutManager(mLayoutManager);
        adapter = new LoaiSachAdapter(getActivity(), (ArrayList<LoaiSach>) ls, this);
        rc.setAdapter(adapter);

        mData = FirebaseDatabase.getInstance().getReference("LoaiSach");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ls.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    LoaiSach item = data.getValue(LoaiSach.class);
                    ls.add(item);
                }
                capnhatLV();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View v = inflater.inflate(R.layout.them_loai_sach_layout, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Thêm Loại Sách")
                        .setView(v)
                        .setPositiveButton("THÊM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText edtMaLoai = v.findViewById(R.id.edtMaLoai);
                                EditText edtTenLoaiSach = v.findViewById(R.id.edtTenLoaiSach);
                                EditText edtMoTa = v.findViewById(R.id.edtMoTa);
                                EditText edtViTri = v.findViewById(R.id.edtViTri);

                                LoaiSach a1 = new LoaiSach(edtMaLoai.getText().toString(),
                                        edtTenLoaiSach.getText().toString(),
                                        edtMoTa.getText().toString(),
                                        Integer.parseInt(edtViTri.getText().toString()));

                                dao.insert(a1);
                            }
                        })
                        .setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();
            }
        });

        return view;
    }

    public void capnhatLV() {
        adapter.notifyItemInserted(ls.size());
        adapter.notifyDataSetChanged();
    }



}
