package com.example.timvachothuephongtro.others;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.access.LoginActivity;
import com.example.timvachothuephongtro.chutro.ChuTro;
import com.example.timvachothuephongtro.chutro.ChuTroHomePage;
import com.example.timvachothuephongtro.chutro.DanhSachPhongCuaChu;
import com.example.timvachothuephongtro.database.DBHelper;
import com.example.timvachothuephongtro.khachthue.DanhSachPhongYeuThich;
import com.example.timvachothuephongtro.khachthue.KhachThue;
import com.example.timvachothuephongtro.khachthue.KhachThueHomePage;
import com.example.timvachothuephongtro.object.PhongTro;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ThongTinCaNhan extends AppCompatActivity {
    private TextView txtTTCNHoTen, txtTTCNSDT, txtTTCNVaiTro,txtThongKe;
    private DBHelper db;
    private Button btnDangXuat,btnThayDoiMK;
    private BottomNavigationView bottomMenu;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_tin_ca_nhan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtTTCNHoTen = findViewById(R.id.txtTTCNHoTen);
        txtTTCNSDT = findViewById(R.id.txtTTCNSDT);
        txtTTCNVaiTro = findViewById(R.id.txtTTCNVaiTro);
        txtThongKe = findViewById(R.id.txtThongKe);
        bottomMenu = findViewById(R.id.bottomMenu);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnThayDoiMK = findViewById(R.id.btnThayDoiMK);

        db = new DBHelper(this);
        i = getIntent();
        if(i.hasExtra("usernameChu")){
            try {
                bottomMenu.inflateMenu(R.menu.chutro_menu_bar);
                bottomMenu.setSelectedItemId(R.id.chutrottcn);
                ChuTro chu = db.layThongTinChuTro(i.getStringExtra("usernameChu"));
                ArrayList<PhongTro> dsPhongTroCuaChu = db.timPhongCuaChu(chu.getTaiKhoan());
                int soPhongDaChoThue = 0;
                txtTTCNHoTen.setText(chu.getHoTen());
                txtTTCNSDT.setText(chu.getSoDienThoai());
                txtTTCNVaiTro.setText(chu.getVaiTro());
                for(PhongTro p : dsPhongTroCuaChu){
                    if(p.getDaChoThue() == 1)
                        soPhongDaChoThue++;
                }
                txtThongKe.setText("Số phòng đang có: " + db.timPhongCuaChu(chu.getTaiKhoan()).size() +"\nSố phòng đang cho thuê: " + soPhongDaChoThue);
                bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if(item.getItemId() == R.id.chutrophongcuatoi){
                            Intent intent = new Intent(ThongTinCaNhan.this, DanhSachPhongCuaChu.class);
                            intent.putExtra("usernameChu", i.getStringExtra("usernameChu"));
                            startActivity(intent);
                        }
                        else if(item.getItemId() == R.id.chutrohome){
                            Intent intent = new Intent(ThongTinCaNhan.this, ChuTroHomePage.class);
                            intent.putExtra("usernameChu", i.getStringExtra("usernameChu"));
                            startActivity(intent);
                        }
                        else if(item.getItemId() == R.id.chutrottcn){
                            finish();
                            Intent intent = new Intent(ThongTinCaNhan.this, ThongTinCaNhan.class);
                            intent.putExtra("usernameChu", i.getStringExtra("usernameChu"));
                            startActivity(intent);
                        }
                        return false;
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else{
            bottomMenu.inflateMenu(R.menu.khachthue_menu_bar);
            bottomMenu.setSelectedItemId(R.id.khachthuettcn);
            KhachThue khach = db.layThongTinKhach(i.getStringExtra("usernameKhach"));
            txtTTCNHoTen.setText(khach.getHoTen());
            txtTTCNSDT.setText(khach.getSoDienThoai());
            txtTTCNVaiTro.setText(khach.getVaiTro());
            txtThongKe.setText("Số phòng có thể thuê: " + db.dsPhongTro().size() +"\nSố phòng đã thích: " + db.layDSPhongYeuThich(khach.getTaiKhoan()).size());

            bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                   @Override
                   public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                       if (item.getItemId() == R.id.khachthuehome) {
                           Intent intent = new Intent(ThongTinCaNhan.this, KhachThueHomePage.class);
                           intent.putExtra("usernameKhach", i.getStringExtra("usernameKhach"));
                           startActivity(intent);
                       } else if (item.getItemId() == R.id.phongyeuthich) {
                           Intent intent = new Intent(ThongTinCaNhan.this, DanhSachPhongYeuThich.class);
                           intent.putExtra("usernameKhach", i.getStringExtra("usernameKhach"));
                           startActivity(intent);
                       } else if (item.getItemId() == R.id.khachthuettcn) {
                           finish();
                           Intent intent = new Intent(ThongTinCaNhan.this, ThongTinCaNhan.class);
                           intent.putExtra("usernameKhach", i.getStringExtra("usernameKhach"));
                           startActivity(intent);
                       }
                       return false;
                   }
            });
        }
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTinCaNhan.this, LoginActivity.class);
                UserSession.getInstance().setUsername("");
                startActivity(intent);
            }
        });
        btnThayDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view_doimk = LayoutInflater.from(ThongTinCaNhan.this).inflate(R.layout.dialog_doimk,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinCaNhan.this);
                builder.setTitle("Thay đổi mật khẩu");
                builder.setView(view_doimk);
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i1) {
                        if(xuLiDoiMK(view_doimk))
                            Toast.makeText(ThongTinCaNhan.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(ThongTinCaNhan.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }
    public boolean xuLiDoiMK(View view){
        EditText edtMKCu,edtMKMoi,edtXacNhanMKMoi;
        edtMKCu = view.findViewById(R.id.edtMKCu);
        edtMKMoi = view.findViewById(R.id.edtMKMoi);
        edtXacNhanMKMoi = view.findViewById(R.id.edtXacNhan);

        if(edtMKCu.getText().toString().isEmpty() || edtMKMoi.getText().toString().isEmpty() || edtXacNhanMKMoi.getText().toString().isEmpty()){
            Toast.makeText(ThongTinCaNhan.this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        else {
            if(edtMKMoi.getText().toString().equals(edtXacNhanMKMoi.getText().toString())){
                if(i.hasExtra("usernameChu")){
                    db.thayDoiMK(i.getStringExtra("usernameChu"),"ChuTro",edtMKMoi.getText().toString());
                    db.close();
                    return true;
                }
                else if(i.hasExtra("usernameKhach")){
                    db.thayDoiMK(i.getStringExtra("usernameKhach"),"KhachThue",edtMKMoi.getText().toString());
                    db.close();
                    return true;
                }
            }
        }
        return false;
    }
}