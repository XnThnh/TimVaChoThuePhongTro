package com.example.timvachothuephongtro.object;

public class NguoiDung {
    private int id;
    private String hoTen;
    private String taiKhoan;
    private String matKhau;
    private String soDienThoai;

    public NguoiDung() {
    }

    public NguoiDung(int id, String hoTen, String taiKhoan, String matKhau, String soDienThoai) {
        this.id = id;
        this.hoTen = hoTen;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.soDienThoai = soDienThoai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }
}
