package com.gmsky.tabbedview;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class FragmentActivityForm extends Fragment {

    AlertDialogManager alert = new AlertDialogManager();

    SessionManagement session;
    Button b_create_activity;
    EditText e_activity_name, e_date, e_time, e_activity_owner, e_group_name, e_place;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener datePickerDialog = null;
    TimePickerDialog.OnTimeSetListener timePickerDialog = null;
    String activity_name, activity_owner, group_name, a_place, activity_date_time  /*j_mail, j_user_name, j_password, user_id, password, */;
    Intent intent = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_one);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.f_activity_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializer();
        Toast.makeText(getActivity().getApplicationContext(), "Kullanıcı Giriş Durumu: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();

        String name = user.get(SessionManagement.KEY_UNAME);
        String u_id = user.get(SessionManagement.KEY_UID);

        //veri tabanına bağlanabilirsen oradan user_id nin user name ini çek yaz activity ovnera
        e_activity_owner.setText(u_id);
        //activity i veri tabanına kaydederken user id ile kaydet çekerken user name ile çek

        b_create_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_name = e_activity_name.getText().toString().trim();
                activity_owner = e_activity_owner.getText().toString().trim();
                group_name = e_group_name.getText().toString().trim();
                a_place = e_place.getText().toString().trim();
                activity_date_time = e_date.getText().toString().trim() + " " + e_time.getText().toString().trim();
                if (activity_name.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Lütfen etkinlik adını giriniz!", Toast.LENGTH_LONG).show();
                } else if (group_name.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Lütfen grup seçiniz!", Toast.LENGTH_LONG).show();
                } else if (a_place.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Lütfen  etkinliğin yapılacağı yeri giriniz!", Toast.LENGTH_LONG).show();
                } else if (e_date.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Lütfen etkinlik tarihini giriniz!", Toast.LENGTH_LONG).show();
                } else if (e_time.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Lütfen etkinlik saatini giriniz!", Toast.LENGTH_LONG).show();
                }
            }
        });


        /*user_id = getActivity().getIntent().getStringExtra("user_id");
        password = getActivity().getIntent().getStringExtra("password");
        j_mail = getActivity().getIntent().getStringExtra("j_mail");
        j_user_name = getActivity().getIntent().getStringExtra("j_u_name");
        j_password = getActivity().getIntent().getStringExtra("j_pass0");*/


        myCalendar = Calendar.getInstance();

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

        timePickerDialog = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTime();
            }
        };

        e_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), datePickerDialog, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        e_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), timePickerDialog, myCalendar
                        .get(Calendar.HOUR_OF_DAY), myCalendar
                        .get(Calendar.MINUTE), false).show();
            }
        });

        //nesnelerin ilklemesi bu metot içinde yapılacak
    }

    private void updateDate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        e_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTime() {
        String myFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        e_time.setText(sdf.format(myCalendar.getTime()));
    }

    public void initializer() {
        e_date = (EditText) getActivity().findViewById(R.id.et_date);
        e_time = (EditText) getActivity().findViewById(R.id.et_time);
        e_activity_owner = (EditText) getActivity().findViewById(R.id.et_activity_owner);
        e_group_name = (EditText) getActivity().findViewById(R.id.et_group_name);
        e_place = (EditText) getActivity().findViewById(R.id.et_place);
        e_activity_name = (EditText) getActivity().findViewById(R.id.et_activity_name);

        b_create_activity = (Button) getActivity().findViewById(R.id.btn_create_activity);
        session = new SessionManagement(getActivity().getApplicationContext());
    }
}