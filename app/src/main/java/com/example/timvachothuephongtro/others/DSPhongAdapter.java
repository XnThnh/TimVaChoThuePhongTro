package com.example.timvachothuephongtro.others;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timvachothuephongtro.R;
import com.example.timvachothuephongtro.object.PhongTro;

import java.util.ArrayList;

public class DSPhongAdapter extends RecyclerView.Adapter<DSPhongAdapter.ViewHolder>{
    private ArrayList<PhongTro> dsPhongTro;
    private Context context;
    private boolean isDanhSachPhongCuaChu;
    private boolean isKhachThue;
    public DSPhongAdapter(Context context,ArrayList<PhongTro> dsPhongTro,boolean isDanhSachPhongCuaChu,boolean isKhachThue) {
        this.dsPhongTro = dsPhongTro;
        this.context = context;
        this.isDanhSachPhongCuaChu = isDanhSachPhongCuaChu;
        this.isKhachThue = isKhachThue;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dsphong_recview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.img);
        holder.txtDienTich.setText("Diện tích : " + dsPhongTro.get(position).getDienTich() + "m²");
        holder.txtDiaChi.setText("Địa chỉ : " + dsPhongTro.get(position).getDiaChi());
        holder.txtGiaTien.setText("Giá tiền : " + dsPhongTro.get(position).getSoTien() + "đ");
        int vitri = position;
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietPhong.class);
                intent.putExtra("idPhong",dsPhongTro.get(vitri).getId());
                intent.putExtra("idChu",dsPhongTro.get(vitri).getChuTroId());
                intent.putExtra("isDanhSachPhongCuaChu",isDanhSachPhongCuaChu);
                intent.putExtra("isKhachThue",isKhachThue);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsPhongTro.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imageView;
        private TextView txtDienTich;
        private TextView txtDiaChi;
        private TextView txtGiaTien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent =  itemView.findViewById(R.id.parent);
            imageView =  itemView.findViewById(R.id.imageView);
            txtDienTich = itemView.findViewById(R.id.txtDienTich);
            txtDiaChi =  itemView.findViewById(R.id.txtDiaChi);
            txtGiaTien =  itemView.findViewById(R.id.txtGiaTien);
        }
    }
}
