package com.example.timvachothuephongtro.others;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.chutro.ChuTro;
import com.example.timvachothuephongtro.database.DBHelper;
import com.example.timvachothuephongtro.khachthue.KhachThue;
import com.example.timvachothuephongtro.object.TinNhan;
import com.example.timvachothuephongtro.object.TinNhanAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {
    private String chatID;
    private String ktHayct;
    private RecyclerView recView;
    private EditText edtMessage;
    private ImageButton imgBtnSend;
    private DatabaseReference dbRef;
    private ArrayList<TinNhan> dsTinNhan;
    private TinNhanAdapter adapter;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DBHelper(this);
        chatID = getIntent().getStringExtra("chatID");
        ktHayct = getIntent().getStringExtra("ktHAYct");
        dsTinNhan = new ArrayList<>();
        recView = findViewById(R.id.recyclerView);
        adapter = new TinNhanAdapter(this,dsTinNhan,ktHayct);
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(Chat.this));
        if(chatID != null)
            dbRef = FirebaseDatabase.getInstance().getReference().child("chats").child(chatID);
        dbRef.child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dsTinNhan.clear();
                for(DataSnapshot dtss: snapshot.getChildren()){
                    TinNhan tinNhan = dtss.getValue(TinNhan.class);
                    dsTinNhan.add(tinNhan);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Chat.this, "Loi", Toast.LENGTH_SHORT).show();
            }
        });
        edtMessage = findViewById(R.id.edtMessage);
        imgBtnSend = findViewById(R.id.imgBtnSend);
        imgBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dtbRef = FirebaseDatabase.getInstance().getReference().child("chats").child(chatID);
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String message = edtMessage.getText().toString();
                        TinNhan tinNhan = new TinNhan();
                        if(ktHayct.equals("ct")){
                            Long id = snapshot.child("chutroID").getValue(Long.class);
                            ChuTro chu = db.layThongTinChuTroTheoID(id.intValue());
                            setTitle(chu.getHoTen());
                            tinNhan.setIdNguoiGui(id.intValue());
                            tinNhan.setNguoiGui(ktHayct);
                            tinNhan.setNoiDung(message);
                            dtbRef.child("messages").push().setValue(tinNhan);
                        }
                        else if(ktHayct.equals("kt")){
                            Long id = snapshot.child("khachthueID").getValue(Long.class);
                            KhachThue khach = db.layThongTinKhachTheoID(id.intValue());
                            setTitle(khach.getHoTen());
                            tinNhan.setIdNguoiGui(id.intValue());
                            tinNhan.setNguoiGui(ktHayct);
                            tinNhan.setNoiDung(message);
                            dtbRef.child("messages").push().setValue(tinNhan);
                        }
                        edtMessage.setText("");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Chat.this, "Loi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}