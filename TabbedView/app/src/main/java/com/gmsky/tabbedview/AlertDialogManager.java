package com.gmsky.tabbedview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by gmsky on 05/01/2017.
 */

public class AlertDialogManager {
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        if (status != null)
            alertDialog.setIcon((status) ? R.drawable.success_icon : R.drawable.error_icon);
        alertDialog.setButton("Tamam", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.show();
    }
}
