package com.example.timvachothuephongtro.object;

public class TinNhan {
    private int idNguoiGui;
    private String nguoiGui;
    private String noiDung;

    public TinNhan() {
    }

    public TinNhan(int idNguoiGui, String nguoiGui, String noiDung) {
        this.idNguoiGui = idNguoiGui;
        this.nguoiGui = nguoiGui;
        this.noiDung = noiDung;
    }

    public int getIdNguoiGui() {
        return idNguoiGui;
    }

    public void setIdNguoiGui(int idNguoiGui) {
        this.idNguoiGui = idNguoiGui;
    }

    public String getNguoiGui() {
        return nguoiGui;
    }

    public void setNguoiGui(String nguoiGui) {
        this.nguoiGui = nguoiGui;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
