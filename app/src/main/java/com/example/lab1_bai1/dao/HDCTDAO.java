package com.example.lab1_bai1.dao;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.lab1_bai1.NonUI;
import com.example.lab1_bai1.model.HoaDonChiTiet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HDCTDAO {
    DatabaseReference databaseReference;

    NonUI nonUI;
    Context context;
    String key;

    public HDCTDAO(Context context) {
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("HDCT");
        this.nonUI = new NonUI(context);
    }

    public void insert(HoaDonChiTiet item) {
        key = databaseReference.push().getKey();

        databaseReference.child(key).setValue(item)
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
}
