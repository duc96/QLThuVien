package com.example.lab1_bai1.dao;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lab1_bai1.NonUI;
import com.example.lab1_bai1.fragment.QuanLyNguoiDungFragment;
import com.example.lab1_bai1.model.NguoiDung;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {

    DatabaseReference databaseReference;

    NonUI nonUI;
    Context context;
    String key;
    QuanLyNguoiDungFragment nguoiDungFragment;

    public NguoiDungDAO(Context context) {
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
        this.nonUI = new NonUI(context);
    }


    public NguoiDungDAO(Context context, QuanLyNguoiDungFragment nguoiDungFragment) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
        this.nonUI = new NonUI(context);
        this.context = context;
        this.nguoiDungFragment = nguoiDungFragment;
    }

    public void insert(NguoiDung nguoiDung) {
        key = databaseReference.push().getKey();

        databaseReference.child(key).setValue(nguoiDung)
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

    public List<NguoiDung> getAll() {

        final List<NguoiDung> list = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    NguoiDung nd = data.getValue(NguoiDung.class);
                    list.add(nd);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return list;
    }

    public void update(final NguoiDung nguoiDung) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("tenDangNhap").getValue(String.class).equals(nguoiDung.tenDangNhap)) {
                        key = data.getKey();
                        databaseReference.child(key).setValue(nguoiDung)
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
}
