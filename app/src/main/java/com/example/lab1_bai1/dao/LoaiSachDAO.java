package com.example.lab1_bai1.dao;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lab1_bai1.NonUI;
import com.example.lab1_bai1.fragment.QuanLyLoaiSachFragment;
import com.example.lab1_bai1.model.LoaiSach;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {

    DatabaseReference databaseReference;

    NonUI nonUI;
    Context context;
    String key;
    QuanLyLoaiSachFragment quanLyLoaiSachFragment;

    public LoaiSachDAO(Context context) {
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("LoaiSach");
        this.nonUI = new NonUI(context);
    }

    public LoaiSachDAO(Context context, QuanLyLoaiSachFragment quanLyLoaiSachFragment) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("LoaiSach");
        this.nonUI = new NonUI(context);
        this.context = context;
        this.quanLyLoaiSachFragment = quanLyLoaiSachFragment;
    }

    public void insert(LoaiSach loaiSach) {
        key = databaseReference.push().getKey();

        databaseReference.child(key).setValue(loaiSach)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        nonUI.toast("dang ki thanh cong");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        nonUI.toast("dang ki that bai");
                    }
                });

    }

    public List<LoaiSach> getAll() {

        final List<LoaiSach> list = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    LoaiSach ls = data.getValue(LoaiSach.class);
                    list.add(ls);
                }

                quanLyLoaiSachFragment.capnhatLV();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        return list;
    }

    public void update(final LoaiSach loaiSach) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("tenLoaiSach").getValue(String.class).equals(loaiSach.tenLoaiSach)) {
                        key = data.getKey();
                        databaseReference.child(key).setValue(loaiSach)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("update Thanh cong");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nonUI.toast("update That bai");
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                nonUI.toast("ko truy cap dc du lieu");
            }
        });
    }

    public void delete(final LoaiSach item){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maLoaiSach").getValue(String.class).equalsIgnoreCase(item.maLoaiSach)){
                        key = data.getKey();

                        Log.d("getKey", "onCreate: key :" + key);


                        databaseReference.child(key).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("delete Thanh cong");
                                        Log.d("delete","delete Thanh cong");


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nonUI.toast("delete That bai");
                                        Log.d("delete","delete That bai");
                                    }
                                });

                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
