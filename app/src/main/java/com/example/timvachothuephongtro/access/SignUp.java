package com.example.timvachothuephongtro.access;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.database.DBHelper;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    private EditText edtName, edtUsernameSU, edtPasswordSU, edtPhoneNumber;
    private Spinner spnRole;
    private Button btnSignUp;
    private ArrayList<String> listRole;
    private ArrayAdapter adapter;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtName=findViewById(R.id.edtName);
        edtUsernameSU=findViewById(R.id.edtUsernameSU);
        edtPasswordSU=findViewById(R.id.edtPasswordSU);
        edtPhoneNumber=findViewById(R.id.edtPhoneNumber);

        spnRole=findViewById(R.id.spnRole);
        btnSignUp=findViewById(R.id.btnSignUp);

        listRole = new ArrayList<>();
        listRole.add("Khách thuê");
        listRole.add("Chủ trọ");

        db = new DBHelper(this);

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listRole);
        spnRole.setAdapter(adapter);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDataEntered()){
                    if(checkValidAndSignUp()){
                        Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(SignUp.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(SignUp.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isDataEntered(){
        if(edtName.getText().toString().isEmpty() || edtUsernameSU.getText().toString().isEmpty()
                || edtPasswordSU.getText().toString().isEmpty() || edtPhoneNumber.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
    private boolean checkValidAndSignUp() {
        if (db.kiemTraTenDangNhap(edtUsernameSU.getText().toString())) {
            if (spnRole.getSelectedItem().toString().equals("Khách thuê")) {
                if(db.themNguoiDung("KhachThue", edtName.getText().toString(), edtUsernameSU.getText().toString(),
                        edtPasswordSU.getText().toString(), edtPhoneNumber.getText().toString(), "Khách thuê")) return true;
            } else {
                if(db.themNguoiDung("ChuTro", edtName.getText().toString(), edtUsernameSU.getText().toString(),
                        edtPasswordSU.getText().toString(), edtPhoneNumber.getText().toString(), "Chủ trọ")) return true;
            }
        }
        return false;
    }
}