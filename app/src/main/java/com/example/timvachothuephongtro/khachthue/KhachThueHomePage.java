package com.example.timvachothuephongtro.khachthue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timvachothuephongtro.others.DSPhongAdapter;
import com.example.timvachothuephongtro.others.ThongTinCaNhan;
import com.example.timvachothuephongtro.object.PhongTro;
import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class KhachThueHomePage extends AppCompatActivity {
    private RecyclerView recView;
    private DSPhongAdapter adapter;
    private ArrayList<PhongTro> dsPhongTro;
    private DBHelper db;
    private BottomNavigationView khachMenu;
    private FloatingActionButton btnFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_khach_thue_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recView = findViewById(R.id.recView);
        khachMenu = findViewById(R.id.khachMenu);

        btnFilter = findViewById(R.id.btnFilter);

        db = new DBHelper(this);
        dsPhongTro = db.dsPhongTro();
        adapter = new DSPhongAdapter(this,dsPhongTro,false,true);
        adapter.notifyDataSetChanged();
        recView.setAdapter(adapter);
        recView.setLayoutManager(new GridLayoutManager(this,1));

        khachMenu.setSelectedItemId(R.id.khachthuehome);
        khachMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.khachthuehome){
                    Intent refresh = new Intent(KhachThueHomePage.this,KhachThueHomePage.class);
                    startActivity(refresh);
                    finish();
                }
                else if(item.getItemId() == R.id.phongyeuthich){
                    Intent i = new Intent(KhachThueHomePage.this, DanhSachPhongYeuThich.class);
                    i.putExtra("usernameKhach", getIntent().getStringExtra("usernameKhach"));
                    startActivity(i);
                }
                else if(item.getItemId() == R.id.khachthuechat){
                    Intent i = new Intent(KhachThueHomePage.this, DSChatCuaKhach.class);
                    i.putExtra("usernameKhach", getIntent().getStringExtra("usernameKhach"));
                    startActivity(i);
                }
                else if(item.getItemId() == R.id.khachthuettcn){
                    Intent i = new Intent(KhachThueHomePage.this, ThongTinCaNhan.class);
                    i.putExtra("usernameKhach", getIntent().getStringExtra("usernameKhach"));
                    startActivity(i);
                }
                return false;
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KhachThueHomePage.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_filter, null); // Sử dụng layout dialog_filter.xml
                builder.setView(dialogView);

                EditText edtSoTien = dialogView.findViewById(R.id.edtSoTien);
                EditText edtDienTich = dialogView.findViewById(R.id.edtDienTich);

                builder.setPositiveButton("Lọc", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String soTienStr = edtSoTien.getText().toString();
                        String dienTichStr = edtDienTich.getText().toString();
                        if(soTienStr.isEmpty() && dienTichStr.isEmpty()){
                            adapter = new DSPhongAdapter(KhachThueHomePage.this,dsPhongTro,false,true);
                            recView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            filterRooms(soTienStr, dienTichStr);
                        }

                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    private void filterRooms(String soTienStr, String dienTichStr) {
        int soTien = -1;
        int dienTich = -1;

        try {
            if (!soTienStr.isEmpty()) {
                soTien = Integer.parseInt(soTienStr);
            }

            if (!dienTichStr.isEmpty()) {
                dienTich = Integer.parseInt(dienTichStr);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show();
            return; // Dừng lọc nếu có lỗi định dạng số
        }



        ArrayList<PhongTro> dsPhongTroDaLoc = new ArrayList<>();
        for (PhongTro phongTro : dsPhongTro) {
            if ((soTien == -1 || phongTro.getSoTien() <= soTien) &&
                    (dienTich == -1 || phongTro.getDienTich() <= dienTich)) {
                dsPhongTroDaLoc.add(phongTro);
            }
        }

        adapter = new DSPhongAdapter(this, dsPhongTroDaLoc,false,true); // Khởi tạo adapter mới với danh sách đã lọc
        recView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}