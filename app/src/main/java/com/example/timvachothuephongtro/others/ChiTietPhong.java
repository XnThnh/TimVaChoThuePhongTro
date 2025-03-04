package com.example.timvachothuephongtro.others;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.database.DBHelper;
import com.example.timvachothuephongtro.object.PhongTro;
import com.example.timvachothuephongtro.object.TinNhan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChiTietPhong extends AppCompatActivity {
    private static final int REQUEST_TO_CALL = 123;
    private TextView txtChiTietSoTien, txtChiTietDienTich, txtChiTietDiaChi
            , txtChiTietGiaDien, txtChiTietGiaNuoc, txtChiTietGiaWifi,txtChiTietTienIch;
    private Button btnGoiChoChu,btnNhanTin;
    private ImageView imageView2;
    private DBHelper db;
    private ArrayList<PhongTro> dsPhongTro;
    private Switch switchThue;
    private FloatingActionButton btnThich;
    private int idPhong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_phong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtChiTietSoTien = findViewById(R.id.txtChiTietSoTien);
        txtChiTietDienTich = findViewById(R.id.txtChiTietDienTich);
        txtChiTietDiaChi = findViewById(R.id.txtChiTietDiaChi);
        txtChiTietGiaDien = findViewById(R.id.txtChiTietGiaDien);
        txtChiTietGiaNuoc = findViewById(R.id.txtChiTietGiaNuoc);
        txtChiTietGiaWifi = findViewById(R.id.txtGiaWifi);
        txtChiTietTienIch = findViewById(R.id.txtChiTietTienIch);

        btnGoiChoChu = findViewById(R.id.btnGoiChoChu);
        btnNhanTin = findViewById(R.id.btnNhanTin);

        switchThue = findViewById(R.id.switchThue);
        btnThich = findViewById(R.id.btnThich);

        imageView2 = findViewById(R.id.imageView2);

        db = new DBHelper(this);
        dsPhongTro = db.dsPhongTro();
        Intent layIDPhong = getIntent();
        if(layIDPhong.getBooleanExtra("isDanhSachPhongCuaChu",false)){
            switchThue.setVisibility(View.VISIBLE);
            btnGoiChoChu.setVisibility(View.GONE);
            dsPhongTro = db.timPhongCuaChu(db.layThongTinChuTroTheoID(layIDPhong.getIntExtra("idChu",0)).getTaiKhoan());
        }
        if(layIDPhong.getBooleanExtra("isKhachThue",false)){
            btnThich.setVisibility(View.VISIBLE);
            btnNhanTin.setVisibility(View.VISIBLE);
        }
        else {
            btnThich.setVisibility(View.GONE);
            btnNhanTin.setVisibility(View.GONE);
        }
        //lay thong tin phong tro da duoc an vao trong Recycle View
        idPhong = 0;
        while(idPhong<dsPhongTro.size()){
            if(dsPhongTro.get(idPhong).getId() == layIDPhong.getIntExtra("idPhong",0)){
                Glide.with(ChiTietPhong.this).asBitmap()
                        .load(dsPhongTro.get(idPhong).getUrlAnh()).into(imageView2);
                txtChiTietSoTien.setText(String.valueOf(dsPhongTro.get(idPhong).getSoTien()));
                txtChiTietDienTich.setText(String.valueOf(dsPhongTro.get(idPhong).getDienTich()));
                txtChiTietDiaChi.setText(dsPhongTro.get(idPhong).getDiaChi());
                txtChiTietGiaDien.setText(String.valueOf(dsPhongTro.get(idPhong).getGiaDien()));
                txtChiTietGiaNuoc.setText(String.valueOf(dsPhongTro.get(idPhong).getGiaNuoc()));
                txtChiTietGiaWifi.setText(String.valueOf(dsPhongTro.get(idPhong) .getGiaWifi()));
                txtChiTietTienIch.setText(dsPhongTro.get(idPhong).getTienIch());
                if(dsPhongTro.get(idPhong).getDaChoThue() == 1) switchThue.setChecked(true);
                else switchThue.setChecked(false);
                break;
            }
            else{
                idPhong++;
            }
        }

        if(layIDPhong.getBooleanExtra("isDanhSachPhongCuaChu",false)){
            switchThue.setVisibility(View.VISIBLE);
            btnGoiChoChu.setVisibility(View.GONE);
            dsPhongTro = db.timPhongCuaChu(db.layThongTinChuTroTheoID(layIDPhong.getIntExtra("idChu",0)).getTaiKhoan());
        }
        if (layIDPhong.getBooleanExtra("isKhachThue", false)) {
            ArrayList<PhongTro> dsPhongYeuThich = db.layDSPhongYeuThich(UserSession.getInstance().getUsername());
            btnThich.setVisibility(View.VISIBLE);

            boolean daThich = false;
            int idPhongHienTai = dsPhongTro.get(idPhong).getId();

            for (PhongTro phongYeuThich : dsPhongYeuThich) {
                if (phongYeuThich.getId() == idPhongHienTai) {
                    daThich = true;
                    break;
                }
            }

            if (daThich) {
                btnThich.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red)));
            } else {
                btnThich.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
            }
        }
        //lay thong tin phong tro da duoc an vao trong Recycle View
        switchThue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Switch đang ở trạng thái "checked" (đã bật)
                    if (db.thayDoiTrangThaiThue(dsPhongTro.get(idPhong).getId(), 1)) {
                        Toast.makeText(ChiTietPhong.this, "Đã cho thuê phòng", Toast.LENGTH_SHORT).show(); // Sửa lại thông báo
                    } else {
                        // Xử lý lỗi khi cập nhật CSDL
                        Toast.makeText(ChiTietPhong.this, "Lỗi khi cho thuê phòng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Switch đang ở trạng thái "unchecked" (đã tắt)
                    if (db.thayDoiTrangThaiThue(dsPhongTro.get(idPhong).getId(), 0)) {
                        Toast.makeText(ChiTietPhong.this, "Đã huỷ cho thuê phòng", Toast.LENGTH_SHORT).show(); // Sửa lại thông báo
                    } else {
                        // Xử lý lỗi khi cập nhật CSDL
                        Toast.makeText(ChiTietPhong.this, "Lỗi khi huỷ cho thuê phòng", Toast.LENGTH_SHORT).show();
                    }
                }
                dsPhongTro = db.dsPhongTro(); // Cập nhật lại danh sách phòng trọ sau mỗi lần thay đổi
            }
        });
        btnGoiChoChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnGoiChoChu.getVisibility() == View.VISIBLE){
                    int sdtChu = Integer.parseInt(db.layThongTinChuTroTheoID(layIDPhong.getIntExtra("idChu",0)).getSoDienThoai());
                    if(xinQuyenGoi()){
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+sdtChu));
                        startActivity(callIntent);
                    }
               }
            }
        });
        btnThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<PhongTro> layDSPhongYeuThich = db.layDSPhongYeuThich(UserSession.getInstance().getUsername());
                int idPhongHienTai = layIDPhong.getIntExtra("idPhong", 0);
                boolean daThich = false;

                for (PhongTro phongYeuThich : layDSPhongYeuThich) {
                    if (phongYeuThich.getId() == idPhongHienTai) {
                        daThich = true;
                        break; // Tìm thấy phòng trong danh sách yêu thích, dừng vòng lặp
                    }
                }

                if (daThich) {
                    if (db.xoaPhongYeuThich(idPhongHienTai)) {
                        Toast.makeText(ChiTietPhong.this, "Đã hủy thích", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChiTietPhong.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (db.themPhongYeuThich(db.layThongTinKhach(UserSession.getInstance().getUsername()).getId(), idPhongHienTai)) {
                        Toast.makeText(ChiTietPhong.this, "Đã thích", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChiTietPhong.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnNhanTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idChu = dsPhongTro.get(idPhong).getChuTroId();
                int idKhach = db.layThongTinKhach(UserSession.getInstance().getUsername()).getId();
                DatabaseReference messageRef = FirebaseDatabase.getInstance()
                        .getReference("chats")
                        .child(idKhach + "_" + idChu);
                messageRef.child("chutroID").setValue(idChu);
                messageRef.child("khachthueID").setValue(idKhach);
                String messageID = messageRef.push().getKey();
                TinNhan tinNhan = new TinNhan(idKhach,"kt", "Xin chào chu");
                messageRef.child("messages").child(messageID).setValue(tinNhan);
                Intent i = new Intent(ChiTietPhong.this, Chat.class);
                i.putExtra("chatID",idKhach + "_" + idChu);
                i.putExtra("ktHAYct","kt");
                startActivity(i);
            }
        });
    }
    private boolean xinQuyenGoi(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        if(checkSelfPermission(Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            String[] permission = {Manifest.permission.CALL_PHONE};
            requestPermissions(permission,REQUEST_TO_CALL);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_TO_CALL && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            xinQuyenGoi();
        }
    }
}