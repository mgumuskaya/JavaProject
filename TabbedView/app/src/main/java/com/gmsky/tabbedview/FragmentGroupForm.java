package com.gmsky.tabbedview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by gmsky on 27/12/2016.
 */

public class FragmentGroupForm extends Fragment {

    TextView t_creating_date;
    SessionManagement session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_one);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.f_group_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        session = new SessionManagement(getActivity().getApplicationContext());
        session.checkLogin();

        String myFormatD = "dd/MM/yyyy", myFormatT = "HH:mm";
        SimpleDateFormat sdfD = new SimpleDateFormat(myFormatD, Locale.US);
        SimpleDateFormat sdfT = new SimpleDateFormat(myFormatT, Locale.getDefault());

        /*Log.e("GMSKY==>", sdfD.format(Calendar.getInstance().getTime()));
        Log.e("GMSKY==>", sdfT.format(Calendar.getInstance().getTime()));*/
        t_creating_date = (TextView) getActivity().findViewById(R.id.et_creating_date);
        t_creating_date.setText(sdfD.format(Calendar.getInstance().getTime()) + "-" + sdfT.format(Calendar.getInstance().getTime()));
        //nesnelerin ilklemesi bu metot içinde yapılacak
    }
}