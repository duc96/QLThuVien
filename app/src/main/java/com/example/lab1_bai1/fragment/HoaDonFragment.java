package com.example.lab1_bai1.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab1_bai1.R;
import com.example.lab1_bai1.adapter.HoaDonAdapter;
import com.example.lab1_bai1.adapter.HoaDonChiTietAdapter;
import com.example.lab1_bai1.dao.HDCTDAO;
import com.example.lab1_bai1.dao.HoaDonDAO;
import com.example.lab1_bai1.model.HoaDon;
import com.example.lab1_bai1.model.HoaDonChiTiet;
import com.example.lab1_bai1.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HoaDonFragment extends Fragment {

    TextView tvMaHDCT, tvDonGia, tvThanhTien;
    EditText edtSoLuong;
    Spinner spnTenSach;

    ArrayList<HoaDonChiTiet> listHDCT;
    ArrayList<HoaDon> listHoaDon;
    ArrayList<Sach> listSach;

    ListView lvHoaDon;
    FloatingActionButton floatHoaDon;
    TextView maHD, ngayLapHD;
    Button btnThemHD;
    DatabaseReference mdata;
    DatabaseReference mdata1;
    DatabaseReference mdata2;
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    HoaDonAdapter adapter;
    HoaDonDAO hoaDonDAO;
    ListView lvTamHDCT;

    public HoaDonFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hoa_don, container, false);

        lvHoaDon = v.findViewById(R.id.lvHoaDon);
        floatHoaDon = v.findViewById(R.id.floatHoaDon);

        mdata1 = FirebaseDatabase.getInstance().getReference("Sach");
        mdata = FirebaseDatabase.getInstance().getReference("HoaDon");
        mdata2 = FirebaseDatabase.getInstance().getReference("HDCT");

        hoaDonDAO = new HoaDonDAO(getActivity(), this);

        listHoaDon = new ArrayList<>();
        listSach = new ArrayList<>();
        listHDCT = new ArrayList<>();

        final ValueEventListener listener = new ValueEventListener() {
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
        };
        mdata1.addValueEventListener(listener);

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

        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listHoaDon.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HoaDon a = snapshot.getValue(HoaDon.class);
                    listHoaDon.add(a);
                }
                capnhatLV();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new HoaDonAdapter(getContext(), R.layout.row_hoa_don, listHoaDon);
        lvHoaDon.setAdapter(adapter);

        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String ma = listHoaDon.get(position).maHD;
//                System.out.println("Ma HD: " + ma);
                List<HoaDonChiTiet> a = new ArrayList();
                for (int i = 0; i < listHDCT.size(); i++) {
                    if (listHDCT.get(i).maHDCT.substring(0, 4).equals(ma)) {
//                        System.out.println("Ma HDCT: " + listHDCT.get(i).maHDCT.substring(0, 4));
                        a.add(listHDCT.get(i));
                    }
                }
//                System.out.println(a);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final View inflater1 = LayoutInflater.from(getContext()).inflate(R.layout.tam,null);
                ListView tam = inflater1.findViewById(R.id.lvtam);
                HoaDonChiTietAdapter hoaDonChiTietAdapter = new HoaDonChiTietAdapter(getActivity(), R.layout.row_hdct, a);

                tam.setAdapter(hoaDonChiTietAdapter);
                builder.setTitle("Chi tiết hóa đơn")
                        .setView(inflater1);
                builder.show();

            }
        });

        floatHoaDon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listHDCT.clear();
                // them hoa don
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View viewThem = LayoutInflater.from(getContext())
                        .inflate(R.layout.them_hoa_don, null);

                maHD = viewThem.findViewById(R.id.tvMaHoaDon);
                ngayLapHD = viewThem.findViewById(R.id.tvNgayTaoHoaDon);
                btnThemHD = viewThem.findViewById(R.id.btnThemHD);
                lvTamHDCT = viewThem.findViewById(R.id.lvTamHDCT);

                SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
                String a = format.format(Calendar.getInstance().getTime());
                final int count = listHoaDon.size() + 1;
                maHD.setText("HD " + count);
                ngayLapHD.setText("Ngay tao : " + a);

                btnThemHD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        final View viewThemHDCT = LayoutInflater.from(viewThem.getContext())
                                .inflate(R.layout.them_hdct, null);

                        tvMaHDCT = viewThemHDCT.findViewById(R.id.tvMaHDCT);
                        spnTenSach = viewThemHDCT.findViewById(R.id.spnTenSach);
                        edtSoLuong = viewThemHDCT.findViewById(R.id.edtSoLuong);
                        tvDonGia = viewThemHDCT.findViewById(R.id.tvDonGia);
                        tvThanhTien = viewThemHDCT.findViewById(R.id.tvThanhTien);

                        List name = new ArrayList<>();
                        for (int t = 0; t < listSach.size(); t++) {
                            name.add(listSach.get(t).tenSach);
                        }
                        int count1 = listHDCT.size() + 1;
                        tvMaHDCT.setText("HD " + count + " - " + count1);

                        ArrayAdapter<String> tenSachAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, name);
                        tenSachAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnTenSach.setAdapter(tenSachAdapter);

                        spnTenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                for (int k = 0; k < listSach.size(); k++) {
                                    if (spnTenSach.getSelectedItem().toString().equals(listSach.get(k).tenSach)) {
                                        tvDonGia.setText(listSach.get(k).giaBia + "");
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });

                        edtSoLuong.setText("1");
                        edtSoLuong.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                tvThanhTien.setText("Tong tien: 0");
                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                String aaa = edtSoLuong.getText().toString();
                                if (aaa == null) {

                                    edtSoLuong.setText("1");
                                    tvThanhTien.setText(tvDonGia.getText().toString());

                                } else {

                                    Float num = Float.parseFloat(aaa);
                                    tvThanhTien.setText(num * Float.parseFloat(tvDonGia.getText().toString()) + "");

                                }
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });


                        builder1.setTitle("Them HDCT")
                                .setView(viewThemHDCT)
                                .setPositiveButton("Them", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        listHDCT.add(new HoaDonChiTiet(tvMaHDCT.getText().toString(),
                                                spnTenSach.getSelectedItem().toString(),
                                                Integer.parseInt(edtSoLuong.getText().toString()),
                                                Float.parseFloat(tvDonGia.getText().toString()),
                                                Float.parseFloat(tvThanhTien.getText().toString())));

                                        HoaDonChiTietAdapter hoaDonChiTietAdapter = new HoaDonChiTietAdapter(getActivity(), R.layout.row_hdct, listHDCT);

                                        lvTamHDCT.setAdapter(hoaDonChiTietAdapter);

                                        hoaDonChiTietAdapter.notifyDataSetChanged();


                                    }
                                })
                                .setNegativeButton("Huy", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                        builder1.show();


                    }
                });

                builder.setTitle("Them hoa don")
                        .setView(viewThem)
                        .setPositiveButton("Them", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String ma = maHD.getText().toString();
                                String ngay = ngayLapHD.getText().toString();

                                HoaDon a = new HoaDon(ma, ngay);

                                hoaDonDAO.insert(a);

                                HDCTDAO d1 = new HDCTDAO(getActivity());

                                for (int z = 0; z < listHDCT.size(); z++) {
                                    d1.insert(listHDCT.get(z));
                                }

                            }
                        })
                        .setNegativeButton("Huy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();
            }
        });

        return v;
    }

    public void capnhatLV() {
        adapter.notifyDataSetChanged();
    }

}
