package com.example.timvachothuephongtro.khachthue;

import com.example.timvachothuephongtro.object.NguoiDung;

public class KhachThue extends NguoiDung {
    private String vaiTro;

    public KhachThue(int id, String hoTen, String taiKhoan, String matKhau, String soDienThoai, String vaiTro) {
        super(id, hoTen, taiKhoan, matKhau, soDienThoai);
        this.vaiTro = "Khách thuê";
    }

    public String getVaiTro() {
        return vaiTro;
    }
}
