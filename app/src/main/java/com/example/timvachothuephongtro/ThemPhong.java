package com.example.timvachothuephongtro;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.timvachothuephongtro.chutro.DanhSachPhongCuaChu;
import com.example.timvachothuephongtro.database.DBHelper;
import com.example.timvachothuephongtro.object.PhongTro;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ThemPhong extends AppCompatActivity implements View.OnClickListener{
    private ImageButton imageButton;
    private EditText edtSoTien,edtDienTich,edtDiaChi,edtGiaDien,edtGiaNuoc,edtGiaWifi,edtTienIch,edtURLAnh;
    private int chutroID;
    private DBHelper db;
    private Button btnThem;
    private Intent layDuLieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_phong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DBHelper(this);
        layDuLieu = getIntent();
        String layDuLieuChu = layDuLieu.getStringExtra("usernameChu");
        chutroID = db.layThongTinChuTro(layDuLieuChu).getId();
        ImageButton imageButton = findViewById(R.id.imageButton);
        edtSoTien = findViewById(R.id.edtSoTien);
        edtDienTich = findViewById(R.id.edtDienTich);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtGiaDien = findViewById(R.id.edtGiaDien);
        edtGiaNuoc = findViewById(R.id.edtGiaNuoc);
        edtGiaWifi = findViewById(R.id.edtGiaWifi);
        edtTienIch = findViewById(R.id.edtTienIch);
        edtURLAnh = findViewById(R.id.edtURLAnh);
        btnThem = findViewById(R.id.btnThem);
        btnThem.setOnClickListener(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtURLAnh.getText().toString().isEmpty())
                    Glide.with(ThemPhong.this)
                            .asBitmap()
                            .load(edtURLAnh.getText().toString()).into(imageButton);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == btnThem){
            if(edtSoTien.getText().toString().isEmpty() || edtDienTich.getText().toString().isEmpty() ||
                    edtDiaChi.getText().toString().isEmpty() || edtGiaDien.getText().toString().isEmpty() ||
                    edtGiaNuoc.getText().toString().isEmpty() || edtGiaWifi.getText().toString().isEmpty() ||
                    edtTienIch.getText().toString().isEmpty() || edtURLAnh.getText().toString().isEmpty()){
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
                        0,edtURLAnh.getText().toString(),chutroID);
                db.themPhong(phong_moi);
                db.close();
                Intent i = new Intent(this, DanhSachPhongCuaChu.class);
                i.putExtra("usernameChu",layDuLieu.getStringExtra("usernameChu"));
                startActivity(i);
            }

        }
    }
}