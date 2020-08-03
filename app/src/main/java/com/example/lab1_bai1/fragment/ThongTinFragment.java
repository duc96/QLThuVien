package com.example.lab1_bai1.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab1_bai1.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThongTinFragment extends Fragment {

    public ThongTinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin, container, false);


        return view;

    }

}
