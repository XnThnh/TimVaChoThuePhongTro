package com.example.timvachothuephongtro.chutro;

import com.example.timvachothuephongtro.object.NguoiDung;

public class ChuTro extends NguoiDung {
    private String vaiTro ;

    public ChuTro(int id, String hoTen, String taiKhoan, String matKhau, String soDienThoai, String vaiTro) {
        super(id, hoTen, taiKhoan, matKhau, soDienThoai);
        this.vaiTro = "Chủ trọ";
    }

    public String getVaiTro() {
        return vaiTro;
    }
}
