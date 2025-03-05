package com.example.timvachothuephongtro.chutro;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timvachothuephongtro.others.ThemPhong;
import com.example.timvachothuephongtro.others.DSPhongAdapter;
import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.others.ThongTinCaNhan;
import com.example.timvachothuephongtro.database.DBHelper;
import com.example.timvachothuephongtro.object.PhongTro;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class DanhSachPhongCuaChu extends AppCompatActivity {
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 123;
    private RecyclerView recView;
    private DSPhongAdapter adapter;
    private ArrayList<PhongTro> dsPhongTroCuaChu;
    private DBHelper db;
    private BottomNavigationView bottomMenu;
    private Intent intent;
    private FloatingActionButton addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_sach_phong_cua_chu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottomMenu = findViewById(R.id.bottomMenu);

        db = new DBHelper(this);

        addButton = findViewById(R.id.addButton);

        intent = getIntent();
        recView = findViewById(R.id.recView);
        String taiKhoanChu = intent.getStringExtra("usernameChu");
        dsPhongTroCuaChu = db.timPhongCuaChu(taiKhoanChu);
        adapter = new DSPhongAdapter(this,dsPhongTroCuaChu,true,false);
        adapter.notifyDataSetChanged();
        recView.setAdapter(adapter);
        recView.setLayoutManager(new GridLayoutManager(this,1));
        bottomMenu.setSelectedItemId(R.id.chutrophongcuatoi);
        bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.chutrohome){
                    Intent i = new Intent(DanhSachPhongCuaChu.this, ChuTroHomePage.class);
                    i.putExtra("usernameChu", taiKhoanChu);
                    startActivity(i);
                }
                else if(item.getItemId() == R.id.chutrophongcuatoi){
                    finish();
                     Intent i = new Intent(DanhSachPhongCuaChu.this, DanhSachPhongCuaChu.class);
                     i.putExtra("usernameChu", taiKhoanChu);
                     startActivity(i);
                }
                else if(item.getItemId() == R.id.chutrochat){
                    Intent i = new Intent(DanhSachPhongCuaChu.this, DSChatCuaChu.class);
                    i.putExtra("usernameChu", taiKhoanChu);
                    startActivity(i);
                }
                else if(item.getItemId() == R.id.chutrottcn){
                    Intent i = new Intent(DanhSachPhongCuaChu.this, ThongTinCaNhan.class);
                    i.putExtra("usernameChu", taiKhoanChu);
                    startActivity(i);
                }
                return false;
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DanhSachPhongCuaChu.this, ThemPhong.class);
                i.putExtra("usernameChu",taiKhoanChu);
                startActivity(i);
            }
        });

    }
}