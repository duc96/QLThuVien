package com.example.lab1_bai1.dao;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lab1_bai1.MainActivity;
import com.example.lab1_bai1.NonUI;
import com.example.lab1_bai1.SachActivity;
import com.example.lab1_bai1.model.Sach;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {

    DatabaseReference databaseReference;

    NonUI nonUI;
    Context context;
    String key;

    public SachDAO(Context context) {
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Sach");
        this.nonUI = new NonUI(context);
    }

    public void insert(Sach item) {
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

    public List<Sach> getAll() {

        final List<Sach> list = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Sach ls = data.getValue(Sach.class);
                    list.add(ls);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        return list;
    }

    public List<Sach> getAll1() {

        final List<Sach> list = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Sach ls = data.getValue(Sach.class);
                    list.add(ls);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        return list;
    }

    public void delete(final Sach item) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maSach").getValue(String.class).equalsIgnoreCase(item.maSach)){
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

    public void update(final Sach item) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("tenSach").getValue(String.class).equals(item.tenSach)) {
                        key = data.getKey();
                        databaseReference.child(key).setValue(item)
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

