package com.example.lab1_bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab1_bai1.dao.HoaDonDAO;
import com.example.lab1_bai1.dao.NguoiDungDAO;
import com.example.lab1_bai1.dao.SachDAO;
import com.example.lab1_bai1.model.HoaDon;
import com.example.lab1_bai1.model.NguoiDung;
import com.example.lab1_bai1.model.Sach;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity{

    TextView tvDangNhap;
    TextView tvDangKi;
    EditText edtTenDangNhap;
    EditText edtMatKhau;
    public Boolean rep = false;
    public static ArrayList<NguoiDung> listND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_chao);

        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(getApplicationContext());
        listND = (ArrayList<NguoiDung>) nguoiDungDAO.getAll();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_login);

                tvDangNhap = findViewById(R.id.tvDangNhap);
                tvDangKi = findViewById(R.id.tvDangKi);
                edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
                edtMatKhau = findViewById(R.id.edtMatKhau);

                tvDangNhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String a = edtTenDangNhap.getText().toString();
                        String b = edtMatKhau.getText().toString();

                        for(int i = 0 ; i < listND.size() ; i ++){
                            NguoiDung c = listND.get(i);
                            if(a.equals(c.tenDangNhap) &&
                                    b.equals(c.matKhau)){
                                rep = true;
                            }
                        }

                        if(rep == true){
                            Toast.makeText(LoginActivity.this, "Dang nhap thanh cong !!", Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("name",a);

                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
                            edtTenDangNhap.setText("");
                            edtMatKhau.setText("");
                        }
                        rep = false;
                    }
                });

                tvDangKi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View mview) {

                        rep = true;
                        mview = getLayoutInflater().inflate(R.layout.dangki_layout, null);

                        final EditText edTen = mview.findViewById(R.id.edtTen);
                        final EditText edPass = mview.findViewById(R.id.edtPass);
                        final EditText edSDT = mview.findViewById(R.id.edtSDT);

                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setView(mview);
                        builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                for(int j = 0 ; j < listND.size() ; j ++){
                                    NguoiDung d = listND.get(j);
                                    if(edTen.getText().toString().equals(d.tenDangNhap) &&
                                            edPass.getText().toString().equals(d.matKhau)){
                                        Toast.makeText(LoginActivity.this, "Nguoi dung da ton tai !!", Toast.LENGTH_SHORT).show();
                                        edTen.setText("");
                                        edPass.setText("");
                                        edSDT.setText("");
                                        rep = false;
                                    }
                                }
                                if(rep == true){
                                    NguoiDung nd = new NguoiDung(edTen.getText().toString(),
                                            edPass.getText().toString(),Integer.parseInt(edSDT.getText().toString()));

                                    NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(getApplicationContext());
                                    nguoiDungDAO.insert(nd);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
            }

        }, 2000);
    }

}
