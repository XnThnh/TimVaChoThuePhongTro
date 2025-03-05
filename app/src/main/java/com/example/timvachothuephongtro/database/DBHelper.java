package com.example.timvachothuephongtro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.timvachothuephongtro.chutro.ChuTro;
import com.example.timvachothuephongtro.khachthue.KhachThue;
import com.example.timvachothuephongtro.object.PhongTro;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "timvachothuephongtro.db";
    public DBHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS KhachThue (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hoten TEXT NOT NULL," +
                "taikhoan TEXT NOT NULL," +
                "matkhau TEXT NOT NULL," +
                "sodienthoai TEXT NOT NULL," +
                "vaitro TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS ChuTro (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hoten TEXT NOT NULL," +
                "taikhoan TEXT NOT NULL," +
                "matkhau TEXT NOT NULL," +
                "sodienthoai TEXT NOT NULL," +
                "vaitro TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS PhongTro (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "sotien INTEGER NOT NULL," +
                "dientich INTEGER NOT NULL," +
                "diachi TEXT NOT NULL," +
                "giadien INTEGER NOT NULL," +
                "gianuoc INTEGER NOT NULL," +
                "giawifi INTEGER NOT NULL," +
                "tienich TEXT NOT NULL," +
                "dachothue INTEGER DEFAULT 0," +
                "urlanh TEXT, "+
                "chutroid INTEGER NOT NULL," +
                "FOREIGN KEY(chutroid) REFERENCES ChuTro(id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS PhongYeuThich (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "phongtroid INTEGER NOT NULL," +
                "khachthueid INTEGER NOT NULL," +
                "FOREIGN KEY(phongtroid) REFERENCES PhongTro(id)," +
                "FOREIGN KEY(khachthueid) REFERENCES KhachThue(id))");
        db.execSQL("INSERT INTO KhachThue (hoten, taikhoan, matkhau,sodienthoai, vaitro) VALUES ('Khach 1', 'khachthue1', '1','123', 'Khách thuê')");
        db.execSQL("INSERT INTO ChuTro (hoten, taikhoan, matkhau,sodienthoai, vaitro) VALUES ('Chu tro 1', 'chutro1', '1','456', 'Chủ trọ')");
        db.execSQL("INSERT INTO ChuTro (hoten, taikhoan, matkhau,sodienthoai, vaitro) VALUES ('Chu tro 2', 'chutro2', '1','789', 'Chủ trọ')");

        db.execSQL("INSERT INTO PhongTro (sotien, dientich, diachi, giadien, gianuoc, giawifi, tienich, dachothue,urlanh, chutroid) VALUES\n" +
                "(3000000, 20, \"123 Đường A, Phường B, Quận C\", 3500, 20000, 100000, \"Máy lạnh, Tủ lạnh, Giường, Bàn ghế\"," +
                " 0, \"https://bandon.vn/uploads/posts/thiet-ke-nha-tro-dep-2020-bandon-0.jpg\", 1), " +
                "(4500000, 25, \"456 Đường D, Phường E, Quận F\", 3800, 22000, 120000, \"Máy lạnh, Giường, Bàn ghế, Ban công\"," +
                " 0, \"https://cafefcdn.com/203337114487263232/2024/11/26/46826586011156477472353227663679545028498852n-1732612272499-17326122727241511362724.jpg\", 2), " +
                "(3500000, 22, \"789 Đường G, Phường H, Quận I\", 3600, 21000, 110000, \"Máy lạnh, Tủ lạnh, Giường\"," +
                " 0, \"https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2022/10/4/1100796/7669B8393f63f83da172.jpg\", 1)," +
                "(5000000, 30, \"101 Đường K, Phường L, Quận M\", 4000, 25000, 130000, \"Máy lạnh, Tủ lạnh, Giường, Bàn ghế, Bếp\"," +
                " 0, \"https://i-connect.com.vn/data/news/7046/anh-28-do-noi-that-thong-minh.jpg\", 1)," +
                "(4000000, 28, \"234 Đường N, Phường O, Quận P\", 3700, 23000, 115000, \"Máy lạnh, Giường, Bàn ghế\", " +
                "0, \"https://s-housing.vn/wp-content/uploads/2022/09/thiet-ke-phong-tro-dep-54.jpg\", 2);");

        db.execSQL("INSERT INTO PhongYeuThich (phongtroid, khachthueid) VALUES\n" +
                "(1, 1),\n" +
                "(3, 1),\n" +
                "(5, 1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       db.execSQL("DROP TABLE IF EXISTS KhachThue");
       db.execSQL("DROP TABLE IF EXISTS ChuTro");
       db.execSQL("DROP TABLE IF EXISTS PhongTro");
       db.execSQL("DROP TABLE IF EXISTS PhongYeuThich");
       onCreate(db);
    }
    public int kiemTraTK(String taiKhoan, String matKhau) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM KhachThue WHERE taikhoan = ? AND matkhau = ?", new String[]{taiKhoan, matKhau});
            if (cursor.getCount() > 0) {
                return 1;
            }
            else {
                cursor = db.rawQuery("SELECT * FROM ChuTro WHERE taikhoan = ? AND matkhau = ?", new String[]{taiKhoan, matKhau});
                if (cursor.getCount() > 0) {
                    return 0;
                }
            }

        } catch (Exception e) {
            return -1;
        }
        cursor.close();
        return -1;
    }
    public KhachThue layThongTinKhach(String taiKhoanKhach) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("KhachThue",null,"taikhoan = ?",new String[]{taiKhoanKhach},null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        KhachThue kt = new KhachThue(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        cursor.close();
        return kt;
    }
    public ChuTro layThongTinChuTro(String taiKhoanChu) {
       SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query("ChuTro",null,"taikhoan = ?",new String[]{taiKhoanChu},null,null,null);
            if(cursor != null)
                cursor.moveToFirst();
            ChuTro ct = new ChuTro(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            cursor.close();
            return ct;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<PhongTro> dsPhongTro(){
        ArrayList<PhongTro> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM PhongTro ";
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            PhongTro pt = new PhongTro(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3),cursor.getInt(4),
                    cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getInt(8),cursor.getString(9),cursor.getInt(10));
            if(pt.getDaChoThue() == 0) list.add(pt);
        }
        cursor.close();
        return list;
    }
    public boolean kiemTraTenDangNhap(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("KhachThue", null, "taikhoan = ?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return false;
        } else {
            if (cursor != null) cursor.close();
            cursor = db.query("ChuTro", null, "taikhoan = ?", new String[]{username}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.close();
                return false;
            } else {
                if (cursor != null) cursor.close();
                return true;
            }
        }
    }
    public boolean themNguoiDung(String tenBang,String hoTen, String taiKhoan, String matKhau, String soDienThoai, String vaiTro){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues nguoiDung = new ContentValues();
            nguoiDung.put("hoten",hoTen);
            nguoiDung.put("taikhoan",taiKhoan);
            nguoiDung.put("matkhau",matKhau);
            nguoiDung.put("sodienthoai",soDienThoai);
            nguoiDung.put("vaitro",vaiTro);
            db.insert(tenBang,null,nguoiDung);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<PhongTro> timPhongCuaChu(String username){
        ChuTro chu = layThongTinChuTro(username);
        ArrayList<PhongTro> dsPhongCuaChu = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("PhongTro",null,"chutroid = ?",new String[]{String.valueOf(chu.getId())},null,null,null);
        while(cursor.moveToNext()){
            PhongTro pt = new PhongTro(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3),cursor.getInt(4),
                    cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getInt(8),cursor.getString(9),cursor.getInt(10));
            dsPhongCuaChu.add(pt);
        }
        cursor.close();
        return dsPhongCuaChu;
    }
    public boolean themPhong(PhongTro phong){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues phong_moi = new ContentValues();
        phong_moi.put("sotien",phong.getSoTien());
        phong_moi.put("dientich",phong.getDienTich());
        phong_moi.put("diachi",phong.getDiaChi());
        phong_moi.put("giadien",phong.getGiaDien());
        phong_moi.put("gianuoc",phong.getGiaNuoc());
        phong_moi.put("giawifi",phong.getGiaWifi());
        phong_moi.put("tienich",phong.getTienIch());
        phong_moi.put("dachothue",phong.getDaChoThue());
        phong_moi.put("urlanh",phong.getUrlAnh());
        phong_moi.put("chutroid",phong.getChuTroId());
        if(db.insert("PhongTro",null,phong_moi) != -1){
            db.close();
            return true;
        }
        else{
            db.close();
            return false;
        }

    }
    public boolean thayDoiTrangThaiThue(int idPhong, int trangThai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        // Sửa logic ở đây:
        if (trangThai == 0) {
            content.put("dachothue", 0); // Đã huỷ cho thuê (0)
        } else if (trangThai == 1) {
            content.put("dachothue", 1); // Đã cho thuê (1)
        } else {
            // Xử lý trường hợp trangThai không hợp lệ (tùy chọn)
            return false; // Hoặc throw new IllegalArgumentException("Trạng thái không hợp lệ");
        }

        if (db.update("PhongTro", content, "id=?", new String[]{String.valueOf(idPhong)}) > 0) {
            return true;
        } else {
            return false;
        }
    }
    public ChuTro layThongTinChuTroTheoID(int idChu) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("ChuTro",null,"id = ?",new String[]{String.valueOf(idChu)},null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        ChuTro ct = new ChuTro(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        cursor.close();
        return ct;
    }
    public KhachThue layThongTinKhachTheoID(int idKhach) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("KhachThue",null,"id = ?",new String[]{String.valueOf(idKhach)},null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        KhachThue kt = new KhachThue(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        cursor.close();
        return kt;
    }
    public ArrayList<PhongTro> layDSPhongYeuThich(String usernameKhach){
        KhachThue khach = layThongTinKhach(usernameKhach);
        ArrayList<PhongTro> dsPhongYeuThich = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"phongtroid"};
        Cursor cursor = db.query("PhongYeuThich",null,"khachthueid = ?",new String[]{String.valueOf(khach.getId())},null,null,null);
        while(cursor.moveToNext()){
            try {
                PhongTro pt = dsPhongTro().get(cursor.getInt(1) - 1);
                dsPhongYeuThich.add(pt);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        cursor.close();
        return dsPhongYeuThich;
    }
    public boolean themPhongYeuThich(int khachThueID,int phongTroID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("phongtroid",phongTroID);
        content.put("khachthueid",khachThueID);
        if(db.insert("PhongYeuThich",null,content) != -1){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean xoaPhongYeuThich(int phongTroID){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete("PhongYeuThich","phongtroid = ?",new String[]{String.valueOf(phongTroID)}) > 0)
            return true;
        else return false;
    }
    public boolean thayDoiMK(String usernameChu,String vaitro,String matKhau){
        SQLiteDatabase db = this.getWritableDatabase();
        if(vaitro.equals("ChuTro")){
            ContentValues content = new ContentValues();
            content.put("matkhau",matKhau);
            if(db.update("ChuTro",content,"taikhoan = ?",new String[]{usernameChu}) > 0)
                return true;
            else return false;
        }
        else{
            ContentValues content = new ContentValues();
            content.put("matkhau",matKhau);
            if(db.update("KhachThue",content,"taikhoan = ?",new String[]{usernameChu}) > 0)
                return true;
            else return false;
        }
    }
}
