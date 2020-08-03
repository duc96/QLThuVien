package com.example.lab1_bai1.dao;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lab1_bai1.NonUI;
import com.example.lab1_bai1.fragment.HoaDonFragment;
import com.example.lab1_bai1.model.HoaDon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    DatabaseReference databaseReference;

    List<HoaDon> list;

    NonUI nonUI;
    Context context;
    String key;
    HoaDonFragment hoaDonFragment;

    public HoaDonDAO(Context context) {
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("HoaDon");
        this.nonUI = new NonUI(context);
    }

    public HoaDonDAO(Context context, HoaDonFragment hoaDonFragment) {
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("HoaDon");
        this.nonUI = new NonUI(context);
        this.hoaDonFragment = hoaDonFragment;
    }

    public void insert(HoaDon item) {
        key = databaseReference.push().getKey();

        databaseReference.child(key).setValue(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        nonUI.toast("them thanh cong");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        nonUI.toast("them that bai");
                    }
                });
    }

    public List<HoaDon> getAll() {

        list = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HoaDon hd = data.getValue(HoaDon.class);
                    list.add(hd);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        return list;
    }

}
