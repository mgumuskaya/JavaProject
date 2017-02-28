package com.gmsky.tabbedview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {//implements View.OnClickListener {
    String ip = "169.254.146.91";
    String db = "/ACTIVITY_DB";
    String un = "gmsky";
    String pass = "9999";

    Button b_login, b_join;
    EditText et_user_id, et_password, et_password01, et_password0, et_user_name, et_mail;
    Intent intent = null;
    String u_id = "mg", u_pass = "11";  //veri tabanından alınacak
    String user_id, password;
    TextView t_join_us;
    LinearLayout linearLayout1, linearLayout2;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManagement session;
    DB database;
    DBOperations dbOperations;
    String txt="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializer();
        //Toast.makeText(getApplicationContext(), "Kullanıcı Giriş Durumu: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);

        linearLayout1.setVisibility(View.VISIBLE);
        linearLayout2.setVisibility(View.INVISIBLE);

        t_join_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout1.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
            }
        });

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = et_user_id.getText().toString().trim();
                password = et_password.getText().toString().trim();

                if ((user_id.contentEquals(u_id) == true) && (password.contentEquals(u_pass) == true)) {
                    Log.e("MG___", "Kullanıcı adı ve parola doğru... " + " user_id=" + user_id + " u_id=" + u_id);

                    session.createLoginSession(user_id, password);
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG).show();

                    dbOperations.execute("");

                    intent = new Intent(MainActivity.this, OneFragment.class);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    finish();
                } else if (user_id.contentEquals("") == true || password.contentEquals("") == true) {
                    Log.e("MG___", "Kullanıcı adı ve parola boş geçilemez!");
                    Toast.makeText(MainActivity.this, "Kullanıcı adı ve parola boş geçilemez!", Toast.LENGTH_LONG).show();
                } else if ((user_id.contentEquals(u_id) == false) || (password.contentEquals(u_pass) == false)) {
                    Log.e("MG___", "Kullanıcı adı veya parola yanlış... " + " user_id=" + user_id + " " + u_id + " password= " + password + " " + u_pass);
                    Toast.makeText(MainActivity.this, "Kullanıcı adını veya şifreyi yanlış girdiniz!", Toast.LENGTH_LONG).show();
                }
            }
        });

        b_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent = new Intent(MainActivity.this, OneFragment.class);
                String j_pass0, j_pass1, j_u_name, j_mail;

                j_mail = et_mail.getText().toString().trim();
                j_u_name = et_user_name.getText().toString().trim();
                j_pass0 = et_password0.getText().toString().trim();
                j_pass1 = et_password01.getText().toString().trim();

                //NullControls and Validations

                /*if ((j_mail.equals("") == true) || (j_u_name.equals("") == true) || (j_pass0.equals("") == true) || (j_pass1.equals("") == true)) {
                    Log.e("MG___", "Boş alan ver");
                    Toast.makeText(MainActivity.this, "Lütfen alanları doldurunuz!", Toast.LENGTH_LONG).show();
                } else {
                    if (validateMail(j_mail) == false) {
                        Log.e("MG___", "mail adresi format hatası! ==> " + j_mail);
                        Toast.makeText(MainActivity.this, "Lütfen mail adresinizi uygun formatta giriniz!", Toast.LENGTH_LONG).show();
                    } else if (validatePassword(j_pass0) == false || validatePassword(j_pass1) == false) {
                        Log.e("MG___", "parola geçersiz! ==> p1=" + j_pass0 + " p2=" + j_pass1);
                        Toast.makeText(MainActivity.this, "Şifre en az bir; büyük harf, küçük harf, sayı ve özel karakter içermelidir! Boşluk içermemelidir!", Toast.LENGTH_LONG).show();
                    } else if (j_pass0.equals(j_pass1) == false) {
                        Log.e("MG___", "parolalar uyumsuz! ==> p1=" + j_pass0 + " p2=" + j_pass1);
                        Toast.makeText(MainActivity.this, "Şifreler aynı olmalıdır!", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent1 = new Intent(MainActivity.this, OneFragment.class);
                        intent1.putExtra("j_mail",j_mail);
                        intent1.putExtra("j_u_name",j_u_name);
                        intent1.putExtra("j_pass0",j_pass0);
                        startActivity(intent1);
                    }

                }*/
                //Intent intent1 = new Intent(MainActivity.this, OneFragment.class);
                //intente atmadan önce null kontrolü yapılmalı
                intent.putExtra("j_mail", j_mail);
                intent.putExtra("j_u_name", j_u_name);
                intent.putExtra("j_pass0", j_pass0);
                startActivity(intent);

                Log.e("MG___", j_mail + " " + j_u_name + " " + j_pass0 + " " + j_pass1);
            }
        });
    }


    protected void initializer() {
        b_login = (Button) findViewById(R.id.b_login);
        b_join = (Button) findViewById(R.id.b_join_us);

        et_user_id = (EditText) findViewById(R.id.e_user_id);
        et_password = (EditText) findViewById(R.id.e_password);
        et_mail = (EditText) findViewById(R.id.e_mail);
        et_user_name = (EditText) findViewById(R.id.e_user_name);
        et_password0 = (EditText) findViewById(R.id.e_password0);
        et_password01 = (EditText) findViewById(R.id.e_password01);

        t_join_us = (TextView) findViewById(R.id.tv_join_us);
        session = new SessionManagement(getApplicationContext());

        database=new DB();
        dbOperations=new DBOperations();
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateMail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePassword(String passwordStr) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(passwordStr);
        return matcher.find();
    }
}





/*
* ^               # start-of-string
(?=.*[0-9])       # a digit must occur at least once
(?=.*[a-z])       # a lower case letter must occur at least once
(?=.*[A-Z])       # an upper case letter must occur at least once
(?=.*[@#$%^&+=])  # a special character must occur at least once
(?=\S+$)          # no whitespace allowed in the entire string
.{8,}             # anything, at least eight places though
$                 # end-of-string*/










/*        b_kisi= (Button) findViewById(R.id.b_persons);
        b_grup= (Button) findViewById(R.id.b_groups);
        b_etkinlik= (Button) findViewById(R.id.b_activities);

        b_kisi.setOnClickListener(this);
        b_grup.setOnClickListener(this);
        b_etkinlik.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.b_persons):
                intent=new Intent(MainActivity.this,OneFragment.class);
                startActivity(intent);
                break;
            case (R.id.b_groups):
                intent=new Intent(MainActivity.this,TwoFragment.class);
                startActivity(intent);
                break;
            case (R.id.b_activities):
                intent=new Intent(MainActivity.this,ThreeFragment.class);
                startActivity(intent);
                break;
        }
    }*/


/*
OneFragment=persons
TwoFragment=Groups
ThreeFragment=Activities

üye ol ekranında mail adresi kullanıcı adı parola parola tekrarı
butona tıklayınca kişisel bilgiler ekranına geçip bilgileri ekleyecek
*/