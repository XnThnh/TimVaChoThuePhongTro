package com.example.timvachothuephongtro.access;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.chutro.ChuTro;
import com.example.timvachothuephongtro.chutro.ChuTroHomePage;
import com.example.timvachothuephongtro.database.DBHelper;
import com.example.timvachothuephongtro.khachthue.KhachThue;
import com.example.timvachothuephongtro.khachthue.KhachThueHomePage;
import com.example.timvachothuephongtro.others.UserSession;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView txtSignUp;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DBHelper(this);
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        txtSignUp=findViewById(R.id.txtSignUp);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAccountEntered()){
                    if(db.kiemTraTK(edtUsername.getText().toString(), edtPassword.getText().toString()) == 0){
                        Intent i = new Intent(LoginActivity.this, ChuTroHomePage.class);
                        i.putExtra("usernameChu", edtUsername.getText().toString());
                        startActivity(i);
                    }
                    else if(db.kiemTraTK(edtUsername.getText().toString(), edtPassword.getText().toString()) == 1){
                        Intent i = new Intent(LoginActivity.this, KhachThueHomePage.class);
                        i.putExtra("usernameKhach", edtUsername.getText().toString());
                        UserSession.getInstance().setUsername(edtUsername.getText().toString());
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignUp.class);
                startActivity(i);
            }
        });
    }
    private boolean isAccountEntered(){
        if (edtUsername.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }
}