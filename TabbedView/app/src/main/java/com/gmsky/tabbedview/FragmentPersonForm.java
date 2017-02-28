package com.gmsky.tabbedview;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by gmsky on 27/12/2016.
 */

public class FragmentPersonForm extends Fragment {

    SessionManagement session;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener datePickerDialog = null;
    EditText e_birthday;
    Button b_join;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_person_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        session = new SessionManagement(getActivity().getApplicationContext());
        session.checkLogin();

        e_birthday= (EditText) getActivity().findViewById(R.id.et_birthday);
        b_join= (Button) getActivity().findViewById(R.id.btn_join);

        myCalendar=Calendar.getInstance();
        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        e_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), datePickerDialog, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        b_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myFormatD = "dd/MM/yyyy", myFormatT = "HH:mm";
                SimpleDateFormat sdfD = new SimpleDateFormat(myFormatD, Locale.US);
                SimpleDateFormat sdfT = new SimpleDateFormat(myFormatT, Locale.getDefault());
                String createDate=sdfD.format(Calendar.getInstance().getTime()) + "-" + sdfT.format(Calendar.getInstance().getTime());
                Log.e("GMSKY==>",createDate);


            }
        });
        //nesnelerin ilklemesi bu metot içinde yapılacak
    }

    private void updateDate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        e_birthday.setText(sdf.format(myCalendar.getTime()));
    }
}
