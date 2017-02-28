package com.gmsky.tabbedview;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by gmsky on 02/01/2017.
 */

public class DB {
    private static final String ERR = "ERROR=>";

    @SuppressLint("NewAPI")
    public Connection getConnection(String user, String password, String database, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + database + ";user=" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e(ERR, se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e(ERR, e.getMessage());
        } catch (Exception e) {
            Log.e(ERR, e.getMessage());
        }
        return connection;
    }
}

    /*private static final String DB_NAME = "Conn";
    private static final int DB_VERSION = 1;
    private static final String DB_CREATE = "CREATE TABLE Info ("
            + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "IpAdresi TEXT, VeriTabaniAdi TEXT, "
            + "KullaniciAdi TEXT, Sifre TEXT);";

    private static final String DB_DROP = "DROP TABLE IF EXISTS Info";

    private DbHelper helper;
    private Context context;
    private SQLiteDatabase database;

    private static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DB_DROP);
            onCreate(db);
        }
    }

    public DB(Context context) {
        this.context = context;
    }

    public DB open() {
        helper = new DbHelper(context);
        database = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean Insert(String ipAdresi, String veriTabaniAdi,
                          String kullaniciAdi, String sifre) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("IpAdresi", ipAdresi);
            cv.put("VeriTabaniAdi", veriTabaniAdi);
            cv.put("KullaniciAdi", kullaniciAdi);
            cv.put("Sifre", sifre);
            database.insert("Info", null, cv);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean Update(String ipAdresi, String veriTabaniAdi,
                          String kullaniciAdi, String sifre) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("IpAdresi", ipAdresi);
            cv.put("VeriTabaniAdi", veriTabaniAdi);
            cv.put("KullaniciAdi", kullaniciAdi);
            cv.put("Sifre", sifre);
            database.update("Info", cv, "ID=?", new String[] { "1" });
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Cursor Query() {
        Cursor c = database.query("Info", new String[] { "ID", "IpAdresi",
                        "VeriTabaniAdi", "KullaniciAdi", "Sifre" }, null, null, null,
                null, null);
        return c;
    }

    public void Delete() {
        database.delete("Info", null, null);
    }*/


