package com.gmsky.tabbedview;

import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by gmsky on 10/02/2017.
 */

public class DBOperations extends AsyncTask<String, String, String> {

    String ip = "169.254.146.91";
    String db = "/ACTIVITY_DB";
    String un = "gmsky";
    String pass = "9999";

    Connection con;

    String res = "";
    Boolean isSuccess = false;

    DB database = new DB();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            con = database.getConnection(un, pass, db, ip); // Connect to database
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {
                String query = "SELECT * FROM person_info";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    res = rs.getString("p_user_name");
                    isSuccess = true;
                    Log.e("Error=>","Connected to DB successfully.   "+res);
                    Log.e("Error=>","Connected to DB successfully.");
                    con.close();
                } else {
                    res = "Don't found!";
                    isSuccess = false;
                    con.close();
                }
            }
        } catch (Exception e) {
            isSuccess = false;
            res = e.getMessage();
        }
        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        //s i bir yerlere gönder. s değeri de res değerine aşit olacak
        MainActivity ma = new MainActivity();
        ma.txt = s;
//        Toast.makeText(ma.getApplicationContext(),s,Toast.LENGTH_LONG);

    }

}
