package com.example.lab1_bai1.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lab1_bai1.LoginActivity;
import com.example.lab1_bai1.R;
import com.example.lab1_bai1.adapter.NguoiDungAdapter;
import com.example.lab1_bai1.dao.NguoiDungDAO;
import com.example.lab1_bai1.model.NguoiDung;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuanLyNguoiDungFragment extends Fragment {

    List<NguoiDung> listND = new ArrayList();
    ListView lvND;
    public NguoiDungAdapter adapter;


    public QuanLyNguoiDungFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_nguoi_dung, container, false);

        lvND = view.findViewById(R.id.lvNguoiDung);

        listND = LoginActivity.listND;

        adapter = new NguoiDungAdapter(getActivity(),listND,this);

        lvND.setAdapter(adapter);

        return view;
    }
}
