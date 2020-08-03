package com.example.lab1_bai1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1_bai1.adapter.SachAdapter;
import com.example.lab1_bai1.dao.SachDAO;
import com.example.lab1_bai1.model.LoaiSach;
import com.example.lab1_bai1.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.lab1_bai1.MainActivity.listSach;
import static com.example.lab1_bai1.fragment.QuanLyLoaiSachFragment.ls;

public class SachActivity extends AppCompatActivity {

    TextView tv;
    ListView lv;
    FloatingActionButton floatSach;
    SachAdapter adapter;
    ArrayList<Sach> aaa = new ArrayList<>();
    SachDAO sachDAO;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        sachDAO = new SachDAO(getApplicationContext());
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        final Sach s = (Sach) adapter.getItem(pos);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.them_sach_layout, null);

        switch (item.getItemId()) {
            case R.id.capnhat:

                AlertDialog.Builder builder = new AlertDialog.Builder(SachActivity.this);

                final EditText tenSach = v.findViewById(R.id.edtTenSach);
                final EditText maSach = v.findViewById(R.id.edtMaSach);
                final EditText tacGia = v.findViewById(R.id.edtTacGia);
                final EditText gia = v.findViewById(R.id.edtGia);
                final EditText soLuong = v.findViewById(R.id.edtSoLuong);

                tenSach.setText(s.tenSach);
                tenSach.setEnabled(false);
                maSach.setText(s.maSach);
                maSach.setEnabled(false);
                tacGia.setText(s.baoVe);
                gia.setText(s.giaBia + "");
                soLuong.setText(s.soLuong + "");

                builder.setTitle("Cap Nhat Sach")
                        .setView(v)
                        .setPositiveButton("Cap nhat", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Sach s1 = new Sach(tenSach.getText().toString(),
                                        maSach.getText().toString(),
                                        s.maLoai,
                                        tacGia.getText().toString(),
                                        Float.parseFloat(gia.getText().toString()),
                                        Integer.parseInt(soLuong.getText().toString()));

                                sachDAO.update(s1);
                            }
                        })
                        .setNegativeButton("Huy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();
                break;
            case R.id.xoa:

                Sach s1 = new Sach(s.tenSach,
                        s.maSach,
                        s.maLoai,
                        s.baoVe,
                        s.giaBia,
                        s.soLuong);

                sachDAO.delete(s1);
                aaa.remove(pos);
                capnhatLV();

                break;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);

        lv = findViewById(R.id.lvSach);
        floatSach = findViewById(R.id.floatSach);
        tv = findViewById(R.id.tvBundleLoaiSach);
        Bundle a = getIntent().getExtras();
        tv.setText(a.get("tenloai").toString());

        String loai1 = a.get("maloai").toString();

        for (int i = 0; i < listSach.size(); i++) {
            if (listSach.get(i).maLoai.equals(loai1)) {
                aaa.add(listSach.get(i));
            }
        }

        adapter = new SachAdapter(getApplicationContext(), R.layout.row_sach, aaa);
        lv.setAdapter(adapter);

        registerForContextMenu(lv);

        floatSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SachActivity.this);
                final View v = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.them_sach_layout, null);
                builder.setTitle("Thêm sách mới.")
                        .setView(v)
                        .setPositiveButton("Thêm", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String maLoai = "";
                                EditText edtTenSach = v.findViewById(R.id.edtTenSach);
                                EditText edtMaSach = v.findViewById(R.id.edtMaSach);
                                EditText edtTacGia = v.findViewById(R.id.edtTacGia);
                                EditText edtGiaBia = v.findViewById(R.id.edtGia);
                                EditText edtSoLuong = v.findViewById(R.id.edtSoLuong);

                                for (int a = 0; a < ls.size(); a++) {
                                    LoaiSach a1 = ls.get(a);
                                    String tenLoai = a1.tenLoaiSach;
                                    if (tenLoai.equals(tv.getText().toString())) {
                                        maLoai = a1.maLoaiSach;
                                    }
                                }

                                SachDAO dao = new SachDAO(SachActivity.this);
                                dao.insert(new Sach(edtTenSach.getText().toString(),
                                        edtMaSach.getText().toString(),
                                        maLoai,
                                        edtTacGia.getText().toString(),
                                        Float.parseFloat(edtGiaBia.getText().toString()),
                                        Integer.parseInt(edtSoLuong.getText().toString())));
                                aaa.add(new Sach(edtTenSach.getText().toString(),
                                        edtMaSach.getText().toString(),
                                        maLoai,
                                        edtTacGia.getText().toString(),
                                        Float.parseFloat(edtGiaBia.getText().toString()),
                                        Integer.parseInt(edtSoLuong.getText().toString())));
                                capnhatLV();

                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                builder.show();
            }
        });
    }

    public void capnhatLV() {
        adapter.notifyDataSetChanged();
    }

}
