package com.example.timvachothuephongtro.chutro;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.database.DBHelper;
import com.example.timvachothuephongtro.khachthue.DSChatCuaKhach;
import com.example.timvachothuephongtro.khachthue.KhachThue;
import com.example.timvachothuephongtro.object.DSChatRecView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DSChatCuaChu extends AppCompatActivity {
    private String usernameChu;
    private DatabaseReference dbRef;
    private DBHelper db;
    private ArrayList<String> dsChatID;
    private DSChatRecView adapter;
    private RecyclerView recView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dschat_cua_chu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DBHelper(this);
        dsChatID = new ArrayList<>();
        usernameChu = getIntent().getStringExtra("usernameChu");
        ChuTro chu = db.layThongTinChuTro(usernameChu);
        recView = findViewById(R.id.recView);
        adapter = new DSChatRecView(DSChatCuaChu.this,dsChatID,"ct");
        recView.setAdapter(adapter);
        recView.setLayoutManager(new GridLayoutManager(DSChatCuaChu.this,1));

        dbRef = FirebaseDatabase.getInstance().getReference().child("chats");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dsChatID.clear();
                for(DataSnapshot dtss: snapshot.getChildren()){
                    if(dtss.child("chutroID").getValue(Long.class).equals((long)chu.getId())){
                        dsChatID.add(dtss.getKey());
                        adapter.notifyDataSetChanged();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DSChatCuaChu.this, "Lỗi trong dsChatcuaKhach", Toast.LENGTH_SHORT).show();
            }
        });

    }


}