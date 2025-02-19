package com.example.timvachothuephongtro.chutro;

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
import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.others.ThongTinCaNhan;
import com.example.timvachothuephongtro.database.DBHelper;
import com.example.timvachothuephongtro.object.PhongTro;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class DanhSachPhongCuaChu extends AppCompatActivity {
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
                ChuTro chutroHienTai = db.layThongTinChuTro(taiKhoanChu);
                taoDialogVaThemPhong(chutroHienTai.getId());
                dsPhongTroCuaChu = db.timPhongCuaChu(taiKhoanChu);
            }
        });
    }
    private void taoDialogVaThemPhong(int chuTroID){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_them_phong = LayoutInflater.from(this).inflate(R.layout.layout_them_phong,null);
        builder.setTitle("Thêm phòng")
                .setView(view_them_phong)
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int soPhongCu = dsPhongTroCuaChu.size();
                        xuLyThemPhong(view_them_phong,chuTroID);
                        if (dsPhongTroCuaChu.size() >= soPhongCu) {
                            Toast.makeText(DanhSachPhongCuaChu.this, "Thêm phòng thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
    }

    private void xuLyThemPhong(View view_them_phong,int chutroID) {
        EditText edtSoTien = view_them_phong.findViewById(R.id.edtSoTien);
        EditText edtDienTich = view_them_phong.findViewById(R.id.edtDienTich);
        EditText edtDiaChi = view_them_phong.findViewById(R.id.edtDiaChi);
        EditText edtGiaDien = view_them_phong.findViewById(R.id.edtGiaDien);
        EditText edtGiaNuoc = view_them_phong.findViewById(R.id.edtGiaNuoc);
        EditText edtGiaWifi = view_them_phong.findViewById(R.id.edtGiaWifi);
        EditText edtTienIch = view_them_phong.findViewById(R.id.edtTienIch);
        DBHelper db = new DBHelper(this);
        if(edtSoTien.getText().toString().isEmpty() || edtDienTich.getText().toString().isEmpty() ||
        edtDiaChi.getText().toString().isEmpty() || edtGiaDien.getText().toString().isEmpty() ||
        edtGiaNuoc.getText().toString().isEmpty() || edtGiaWifi.getText().toString().isEmpty() ||
        edtTienIch.getText().toString().isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            PhongTro phong_moi = new PhongTro(Integer.parseInt(edtSoTien.getText().toString()),
                    Integer.parseInt(edtDienTich.getText().toString()),
                    edtDiaChi.getText().toString(),
                    Integer.parseInt(edtGiaDien.getText().toString()),
                    Integer.parseInt(edtGiaNuoc.getText().toString()),
                    Integer.parseInt(edtGiaWifi.getText().toString()),
                    edtTienIch.getText().toString(),
                    0,chutroID);
            db.themPhong(phong_moi);
            db.close();
        }

    }
}