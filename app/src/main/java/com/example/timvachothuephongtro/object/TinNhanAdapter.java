package com.example.timvachothuephongtro.object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timvachothuephongtro.R; // Thay đổi package name nếu cần
import java.util.List;

public class TinNhanAdapter extends RecyclerView.Adapter<TinNhanAdapter.ViewHolder> {

    private Context context;
    private List<TinNhan> dsTinNhan;
    private String ktHAYct;

    public TinNhanAdapter(Context context, List<TinNhan> dsTinNhan, String ktHAYct) {
        this.context = context;
        this.dsTinNhan = dsTinNhan;
        this.ktHAYct = ktHAYct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == R.layout.item_tin_nhan_gui){
            View view = LayoutInflater.from(context).inflate(R.layout.item_tin_nhan_gui, parent, false);
            return new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_tin_nhan_nhan, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TinNhan tinNhan = dsTinNhan.get(position);
        holder.txtNguoiGui.setText(tinNhan.getNguoiGui());
        holder.txtNoiDung.setText(tinNhan.getNoiDung());
    }

    @Override
    public int getItemCount() {
        return dsTinNhan.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNguoiGui;
        TextView txtNoiDung;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNguoiGui = itemView.findViewById(R.id.txtNguoiGui);
            txtNoiDung = itemView.findViewById(R.id.txtNoiDung);
        }
    }

    @Override
    public int getItemViewType(int position) {
        for(TinNhan tinNhan:dsTinNhan){
            if(tinNhan.getNguoiGui().equals(ktHAYct)){
                return R.layout.item_tin_nhan_gui;
            }
            else return R.layout.item_tin_nhan_nhan;
        }
        return super.getItemViewType(position);
    }
}
