package com.example.timvachothuephongtro.khachthue;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.database.DBHelper;
import com.example.timvachothuephongtro.object.PhongTro;
import com.example.timvachothuephongtro.others.DSPhongAdapter;
import com.example.timvachothuephongtro.others.ThongTinCaNhan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class DanhSachPhongYeuThich extends AppCompatActivity {
    private RecyclerView recView;
    private DSPhongAdapter adapter;
    private DBHelper db;
    private Intent intentGetInfo;
    private ArrayList<PhongTro> dsPhongYeuThich;
    private BottomNavigationView bottomMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_sach_phong_yeu_thich);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomMenu = findViewById(R.id.bottomMenu);
        bottomMenu.setSelectedItemId(R.id.phongyeuthich);

        db = new DBHelper(this);
        intentGetInfo = getIntent();
        String usernameKhach = intentGetInfo.getStringExtra("usernameKhach");
        dsPhongYeuThich = db.layDSPhongYeuThich(usernameKhach);

        recView = findViewById(R.id.recView);
        adapter = new DSPhongAdapter(this, dsPhongYeuThich,false,false);
        adapter.notifyDataSetChanged();
        recView.setAdapter(adapter);
        recView.setLayoutManager(new GridLayoutManager(this,1));

        bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.phongyeuthich){
                    finish();
                    Intent i = new Intent(DanhSachPhongYeuThich.this, DanhSachPhongYeuThich.class);
                    i.putExtra("usernameKhach", usernameKhach);
                    startActivity(i);
                }
                else if(item.getItemId() == R.id.khachthuehome){
                    Intent i = new Intent(DanhSachPhongYeuThich.this, KhachThueHomePage.class);
                    i.putExtra("usernameKhach", usernameKhach);
                    startActivity(i);
                }
                else if(item.getItemId() == R.id.khachthuettcn){
                    Intent i = new Intent(DanhSachPhongYeuThich.this, ThongTinCaNhan.class);
                    i.putExtra("usernameKhach", usernameKhach);
                    startActivity(i);
                }
                return false;
            }
        });
    }
}