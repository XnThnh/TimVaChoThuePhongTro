package com.example.timvachothuephongtro.khachthue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.database.DBHelper;
import com.example.timvachothuephongtro.object.DSChatRecView;
import com.example.timvachothuephongtro.others.ThongTinCaNhan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DSChatCuaKhach extends AppCompatActivity {
    private String usernameKhach;
    private DatabaseReference dbRef;
    private DBHelper db;
    private ArrayList<String> dsChatID;
    private DSChatRecView adapter;
    private RecyclerView recView;
    private BottomNavigationView bottomMenuKhach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dschat_cua_khach);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottomMenuKhach = findViewById(R.id.bottomMenuKhach);
        bottomMenuKhach.setSelectedItemId(R.id.khachthuechat);

        db = new DBHelper(this);
        dsChatID = new ArrayList<>();
        usernameKhach = getIntent().getStringExtra("usernameKhach");
        KhachThue khach = db.layThongTinKhach(usernameKhach);

        recView = findViewById(R.id.recView);
        adapter = new DSChatRecView(DSChatCuaKhach.this,dsChatID,"kt");
        recView.setAdapter(adapter);
        recView.setLayoutManager(new GridLayoutManager(DSChatCuaKhach.this,1));

        dbRef = FirebaseDatabase.getInstance().getReference().child("chats");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dsChatID.clear();
                for(DataSnapshot dtss: snapshot.getChildren()){
                    if(dtss.child("khachthueID").getValue(Long.class).equals((long)khach.getId())){
                        dsChatID.add(dtss.getKey());
                        adapter.notifyDataSetChanged();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DSChatCuaKhach.this, "Lỗi trong dsChatcuaKhach", Toast.LENGTH_SHORT).show();
            }
        });
        bottomMenuKhach.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.khachthuehome){
                    Intent refresh = new Intent(DSChatCuaKhach.this,KhachThueHomePage.class);
                    startActivity(refresh);
                }
                else if(item.getItemId() == R.id.phongyeuthich){
                    Intent i = new Intent(DSChatCuaKhach.this, DanhSachPhongYeuThich.class);
                    i.putExtra("usernameKhach", getIntent().getStringExtra("usernameKhach"));
                    startActivity(i);
                }
                else if(item.getItemId() == R.id.khachthuechat){
                    finish();
                    Intent i = new Intent(DSChatCuaKhach.this, DSChatCuaKhach.class);
                    i.putExtra("usernameKhach", getIntent().getStringExtra("usernameKhach"));
                    startActivity(i);
                }
                else if(item.getItemId() == R.id.khachthuettcn){
                    Intent i = new Intent(DSChatCuaKhach.this, ThongTinCaNhan.class);
                    i.putExtra("usernameKhach", getIntent().getStringExtra("usernameKhach"));
                    startActivity(i);
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,KhachThueHomePage.class);
        i.putExtra("usernameKhach",usernameKhach);
        startActivity(i);
        finish();
    }
}