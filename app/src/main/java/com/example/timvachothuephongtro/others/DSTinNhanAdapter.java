package com.example.timvachothuephongtro.others;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.khachthue.KhachThue;

import java.util.ArrayList;

public class DSTinNhanAdapter extends RecyclerView.Adapter<DSTinNhanAdapter.ViewHolder>{
    private Context context;
    private ArrayList<KhachThue> dsTinNhanCuaKhach;
    public DSTinNhanAdapter(Context context, ArrayList<KhachThue> dsTinNhanCuaKhach) {
        this.context = context;
        this.dsTinNhanCuaKhach = dsTinNhanCuaKhach;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tin_nhan_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTenNguoiGui.setText(dsTinNhanCuaKhach.get(position).getHoTen());
    }

    @Override
    public int getItemCount() {
        return dsTinNhanCuaKhach.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView txtTenNguoiGui;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            txtTenNguoiGui = itemView.findViewById(R.id.txtTenNguoiGui);
        }

    }
}
