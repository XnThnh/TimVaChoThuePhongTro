package com.example.timvachothuephongtro.object;

public class PhongTro {
    private int id;
    private int soTien;
    private int dienTich;
    private String diaChi;
    private int giaDien;
    private int giaNuoc;
    private int giaWifi;
    private String tienIch;
    private int daChoThue;
    private int chuTroId;

    public PhongTro() {
    }

    public PhongTro(int id, int soTien, int dienTich, String diaChi, int giaDien, int giaNuoc, int giaWifi, String tienIch, int daChoThue, int chuTroId) {
        this.id = id;
        this.soTien = soTien;
        this.dienTich = dienTich;
        this.diaChi = diaChi;
        this.giaDien = giaDien;
        this.giaNuoc = giaNuoc;
        this.giaWifi = giaWifi;
        this.daChoThue = daChoThue;
        this.tienIch = tienIch;
        this.chuTroId = chuTroId;
    }

    public PhongTro(int soTien, int dienTich, String diaChi, int giaDien, int giaWifi, int giaNuoc, String tienIch, int daChoThue, int chuTroId) {
        this.soTien = soTien;
        this.dienTich = dienTich;
        this.diaChi = diaChi;
        this.giaDien = giaDien;
        this.giaWifi = giaWifi;
        this.giaNuoc = giaNuoc;
        this.tienIch = tienIch;
        this.daChoThue = daChoThue;
        this.chuTroId = chuTroId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public int getDienTich() {
        return dienTich;
    }

    public void setDienTich(int dienTich) {
        this.dienTich = dienTich;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getGiaNuoc() {
        return giaNuoc;
    }

    public void setGiaNuoc(int giaNuoc) {
        this.giaNuoc = giaNuoc;
    }

    public int getGiaDien() {
        return giaDien;
    }

    public void setGiaDien(int giaDien) {
        this.giaDien = giaDien;
    }

    public int getGiaWifi() {
        return giaWifi;
    }

    public void setGiaWifi(int giaWifi) {
        this.giaWifi = giaWifi;
    }

    public String getTienIch() {
        return tienIch;
    }

    public void setTienIch(String tienIch) {
        this.tienIch = tienIch;
    }

    public int getDaChoThue() {
        return daChoThue;
    }

    public void setDaChoThue(int daChoThue) {
        this.daChoThue = daChoThue;
    }

    public int getChuTroId() {
        return chuTroId;
    }

    public void setChuTroId(int chuTroId) {
        this.chuTroId = chuTroId;
    }
}