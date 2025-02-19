package com.example.timvachothuephongtro.chutro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

public class ChuTroHomePage extends AppCompatActivity {
    private RecyclerView recView;
    private DSPhongAdapter adapter;
    private ArrayList<PhongTro> dsPhongTro;
    private DBHelper db;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton btnFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chu_tro_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recView = findViewById(R.id.recView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        btnFilter = findViewById(R.id.btnFilter);

        db = new DBHelper(this);
        dsPhongTro = db.dsPhongTro();
        adapter = new DSPhongAdapter(this,dsPhongTro,false,false);
        recView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recView.setLayoutManager(new GridLayoutManager(this,1));

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = getIntent();
                if(item.getItemId() == R.id.chutrohome){
                    Intent refresh = new Intent(ChuTroHomePage.this, ChuTroHomePage.class);
                    startActivity(refresh);
                    finish();
                }
                else if(item.getItemId() == R.id.chutrophongcuatoi){
                    Intent i2 = new Intent(ChuTroHomePage.this, DanhSachPhongCuaChu.class);
                    i2.putExtra("usernameChu", i.getStringExtra("usernameChu"));
                    startActivity(i2);
                    finish();
                }
                else if(item.getItemId() == R.id.chutrottcn){
                    Intent i2 = new Intent(ChuTroHomePage.this, ThongTinCaNhan.class);
                    i2.putExtra("usernameChu", i.getStringExtra("usernameChu"));
                    startActivity(i2);
                    finish();
                }
                return false;
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChuTroHomePage.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_filter, null);
                builder.setView(dialogView);

                EditText edtSoTien = dialogView.findViewById(R.id.edtSoTien);
                EditText edtDienTich = dialogView.findViewById(R.id.edtDienTich);

                builder.setPositiveButton("Lọc", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String soTienStr = edtSoTien.getText().toString();
                        String dienTichStr = edtDienTich.getText().toString();
                        if(soTienStr.isEmpty() && dienTichStr.isEmpty()){
                            adapter = new DSPhongAdapter(ChuTroHomePage.this,dsPhongTro,false,false);
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
        int soTien = -1; // Giá trị mặc định nếu người dùng không nhập
        int dienTich = -1;

        if (!soTienStr.isEmpty()) {
            soTien = Integer.parseInt(soTienStr);
        }

        if (!dienTichStr.isEmpty()) {
            dienTich = Integer.parseInt(dienTichStr);
        }

        ArrayList<PhongTro> dsPhongTroDaLoc = new ArrayList<>();
        for (PhongTro phongTro : dsPhongTro) {
            if ((soTien == -1 || phongTro.getSoTien() <= soTien) &&
                    (dienTich == -1 || phongTro.getDienTich() <= dienTich)) {
                dsPhongTroDaLoc.add(phongTro);
            }
        }

        // Cập nhật RecyclerView với danh sách đã lọc
        adapter = new DSPhongAdapter(this, dsPhongTroDaLoc, false, false);
        recView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}