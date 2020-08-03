package com.example.lab1_bai1.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab1_bai1.R;
import com.example.lab1_bai1.model.HoaDon;
import com.example.lab1_bai1.model.HoaDonChiTiet;
import com.example.lab1_bai1.model.Sach;
import com.example.lab1_bai1.model.tenSoluong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThongKeFragment extends Fragment {

    TextView tvTuNgay;
    TextView tvDenNgay;
    Button btnTim;
    TextView tvNoiDung;
    String tuNgay = "";
    String denNgay = "";

    DatabaseReference mdata;
    DatabaseReference mdata1;
    DatabaseReference mdata2;

    ArrayList<HoaDonChiTiet> listHDCT;
    ArrayList<HoaDon> listHoaDon;
    ArrayList<Sach> listSach;

    public ThongKeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);

        tvTuNgay = view.findViewById(R.id.tvTuNgay);
        tvDenNgay = view.findViewById(R.id.tvDenNgay);
        tvNoiDung = view.findViewById(R.id.tvNoiDung);

        btnTim = view.findViewById(R.id.btnTim);

        mdata1 = FirebaseDatabase.getInstance().getReference("Sach");
        mdata = FirebaseDatabase.getInstance().getReference("HoaDon");
        mdata2 = FirebaseDatabase.getInstance().getReference("HDCT");

        listHoaDon = new ArrayList<>();
        listSach = new ArrayList<>();
        listHDCT = new ArrayList<>();

        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listHoaDon.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HoaDon hd = snapshot.getValue(HoaDon.class);
                    listHoaDon.add(hd);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mdata1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSach.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Sach s = snapshot.getValue(Sach.class);
                    listSach.add(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mdata2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listHDCT.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HoaDonChiTiet s = snapshot.getValue(HoaDonChiTiet.class);
                    listHDCT.add(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        setOnClickListener();

        return view;
    }

    protected void setOnClickListener(){
        tvTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonTuNgay();
            }
        });

        tvDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonDenNgay();
            }
        });

        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tim();
            }
        });
    }

    private void chonTuNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                tuNgay = dateFormat.format(calendar.getTime());
                tvTuNgay.setText("Từ ngày \n" + tuNgay);
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void chonDenNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                denNgay = dateFormat.format(calendar.getTime());
                tvDenNgay.setText("Đến ngày \n" + denNgay);
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    protected  void tim(){
        try {
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(tuNgay);
            Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(denNgay);
            Boolean kq = false;

            ArrayList<tenSoluong> aaa = new ArrayList<>();
            ArrayList<HoaDonChiTiet> listHDTam = new ArrayList<>();

//            System.out.println(listHoaDon);
            for (int i = 0; i < listHoaDon.size(); i++){

                String day = listHoaDon.get(i).ngayMua.substring(11,21);

                Date dateHD = new SimpleDateFormat("dd/MM/yyyy").parse(day);

                if ((dateHD.after(date1) || dateHD.equals(date1)) && (dateHD.before(date2) || dateHD.equals(date2))){
                    kq = true;
                    for(int j = 0 ; j < listHDCT.size(); j ++){
                        if (listHoaDon.get(i).maHD.equals(listHDCT.get(j).maHDCT.substring(0,4))){
                            listHDTam.add(listHDCT.get(j));
                        }
                    }
                }
            }
//            System.out.println("hdct Tam: " + listHDTam);

//            System.out.println("list Sach: "+listSach);
            for (Sach s : listSach){
                int num = 0;

                for (HoaDonChiTiet hdct : listHDTam){
                    if (s.tenSach.equals(hdct.tenSach)){
                        num += hdct.soLuong;
                    }
                }
                aaa.add(new tenSoluong(s.tenSach,num));
            }
            System.out.println("aaa hai cham: " + aaa);

            int max = 0;
            String u = "####";
            for (int i = 0; i < aaa.size(); i++){
                if (max < aaa.get(i).soluong || max == aaa.get(i).soluong){
                    max = aaa.get(i).soluong;
                    u = aaa.get(i).ten;
                }
            }

            int tt = 0;
            for (int i = 0 ; i < listHDTam.size(); i++){
                tt +=listHDTam.get(i).tongTien;
            }

            tvNoiDung.setText("* Tổng doanh thu: " + tt +"\n* Sách bán chạy nhất:  " + u + "\n* Số lượng: " + max +" cuốn");
            if (kq ==false){
                Toast.makeText(getActivity(), "Không bán được gì", Toast.LENGTH_SHORT).show();
                tvNoiDung.setText("* Tổng doanh thu: 0" +
                        "\n* Sách bán chạy nhất: 0" + "\n* Số lượng: 0 cuốn");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
