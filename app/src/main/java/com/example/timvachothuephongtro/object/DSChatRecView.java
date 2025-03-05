package com.example.timvachothuephongtro.object;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timvachothuephongtro.others.Chat;
import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.chutro.ChuTro;
import com.example.timvachothuephongtro.database.DBHelper;
import com.example.timvachothuephongtro.khachthue.KhachThue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DSChatRecView extends RecyclerView.Adapter<DSChatRecView.ViewHolder>{
    private Context context;
    private ArrayList<String> dsChatID;
    private DatabaseReference dbRef;
    private String ktHAYct;
    private DBHelper db;

    public DSChatRecView(Context context, ArrayList<String> dsChatID,String ktHAYct) {
        this.context = context;
        this.dsChatID = dsChatID;
        this.ktHAYct = ktHAYct;
        db = new DBHelper(context);
        dbRef = FirebaseDatabase.getInstance().getReference().child("chats");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tin_nhan_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            dbRef.child(dsChatID.get(position)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(ktHAYct.equals("ct")) {
                        Long id = snapshot.child("khachthueID").getValue(Long.class);
                        KhachThue khach = null;
                        if (id != null) {
                            khach = db.layThongTinKhachTheoID(id.intValue());
                            holder.txtTenNguoiGui.setText(khach.getHoTen());
                        }
                        else {
                            holder.txtTenNguoiGui.setText("khong tim thay");
                        }

                    }
                    else if(ktHAYct.equals("kt")){
                        Long id = snapshot.child("chutroID").getValue(Long.class);
                        Log.d("id",id +"");
                        ChuTro chu = null;
                        if (id != null) {
                            chu = db.layThongTinChuTroTheoID(id.intValue());
                            Log.d("chu",chu.getHoTen());
                            holder.txtTenNguoiGui.setText(chu.getHoTen());
                        }
                        else {
                            holder.txtTenNguoiGui.setText("khong tim thay");
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context,"Lỗi trong dsChatRecView",Toast.LENGTH_SHORT).show();
                }
            });
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Chat.class);
                    intent.putExtra("chatID",dsChatID.get(position));
                    intent.putExtra("ktHAYct",ktHAYct);
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return dsChatID.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private TextView txtTenNguoiGui;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            txtTenNguoiGui = itemView.findViewById(R.id.txtTenNguoiGui);
        }
    }
}
