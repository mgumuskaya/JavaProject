package com.gmsky.tabbedview;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by gmsky on 05/01/2017.
 */

public class SessionManagement {
    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "UserPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_UID = "user_id";
    public static final String KEY_UNAME = "user_name";

    public SessionManagement(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String user_id, String user_name) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_UID, user_id);
        editor.putString(KEY_UNAME, user_name);
        editor.commit();
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(_context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user id
        user.put(KEY_UID, pref.getString(KEY_UID, null));
        // user username
        user.put(KEY_UNAME, pref.getString(KEY_UNAME, null));
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

}
