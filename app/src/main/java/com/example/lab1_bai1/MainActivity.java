package com.example.lab1_bai1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.lab1_bai1.dao.HoaDonDAO;
import com.example.lab1_bai1.dao.SachDAO;
import com.example.lab1_bai1.fragment.HoaDonFragment;
import com.example.lab1_bai1.fragment.QuanLyLoaiSachFragment;
import com.example.lab1_bai1.fragment.QuanLyNguoiDungFragment;
import com.example.lab1_bai1.fragment.ThongKeFragment;
import com.example.lab1_bai1.fragment.ThongTinFragment;
import com.example.lab1_bai1.model.HoaDon;
import com.example.lab1_bai1.model.Sach;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager = getSupportFragmentManager();
    TextView name;


    public static ArrayList<Sach> listSach;
    public static ArrayList<HoaDon> listHoaDon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listSach = new ArrayList<>();
        listHoaDon = new ArrayList<>();

        SachDAO sdao = new SachDAO(this);
        listSach = (ArrayList<Sach>) sdao.getAll();

        HoaDonDAO hoaDonDAO = new HoaDonDAO(this);
        listHoaDon.clear();
        listHoaDon = (ArrayList<HoaDon>) hoaDonDAO.getAll();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView =(NavigationView) findViewById(R.id.nvView);
        drawerLayout =(DrawerLayout) findViewById(R.id.drawLayout);

        View header = navigationView.getHeaderView(0);
        name = header.findViewById(R.id.tvNameHeader);

        Bundle bundle = getIntent().getExtras();
        name.setText("Xin chào " + bundle.getString("name"));

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.qlNguoiDung:
                        toolbar.setTitle("Quản lý người dùng");
                        QuanLyNguoiDungFragment f1 = new QuanLyNguoiDungFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.framelayout,f1)
                                .commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.qlLoaiSach:
                        toolbar.setTitle("Loại sách");
                        QuanLyLoaiSachFragment f2 = new QuanLyLoaiSachFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.framelayout,f2)
                                .commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.hoadon:
                        toolbar.setTitle("Hóa đơn");
                        HoaDonFragment f3 = new HoaDonFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.framelayout,f3)
                                .commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.thongke:
                        toolbar.setTitle("Thống kê sách bán chạy");
                        ThongKeFragment f4 = new ThongKeFragment();
                        fragmentManager.beginTransaction().replace(R.id.framelayout,f4).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.thongtin:
                        toolbar.setTitle("Thông tin");
                        ThongTinFragment f6 = new ThongTinFragment();
                        fragmentManager.beginTransaction().replace(R.id.framelayout,f6).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.exit:
                        Toast.makeText(MainActivity.this, "Bạn đã thoát", Toast.LENGTH_LONG).show();
                        System.exit(0);
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
            else {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        }
        return false;
    }
}

