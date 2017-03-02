/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agprogramlama;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author gmsky
 */
public class SunucuOrnekBir {

    public static final int PORT = 4567; //0-1024 dışında bir port seçilmelidir.

    public static String karsiliginiBul(int sayi) {

        String ifade = null;
        switch (sayi) {
            case 0:
                ifade = "sıfır";
                break;
            case 1:
                ifade = "bir";
                break;
            case 2:
                ifade = "iki";
                break;
            case 3:
                ifade = "üç";
                break;
            case 4:
                ifade = "dört";
                break;
            case 5:
                ifade = "beş";
                break;
            case 6:
                ifade = "altı";
                break;
            case 7:
                ifade = "yedi";
                break;
            case 8:
                ifade = "sekiz";
                break;
            case 9:
                ifade = "dokuz";
                break;
            default:
                ifade = "bilinmeyen değer";
                break;
        }
        return ifade;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket sunucuSoketi = new ServerSocket(PORT);
        System.out.println("Başladı " + sunucuSoketi);
        try {
            //yeni bir bağlantı gelene kadar bekleyecek
            Socket kullaniciSoketi = sunucuSoketi.accept();
            try {
                System.out.println("Bağlantı kabul edildi.");
                //kullanıcıdan gelen verileri alacak
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kullaniciSoketi.getInputStream()));

                //kullanıcıdan gelen isteğin cevabını gönderecek.
                PrintWriter out = new PrintWriter(
                        new BufferedWriter(
                                //true parametresini vermemizin nedeni buffer ın dolmasını beklemeden verileri göndermesi
                                new OutputStreamWriter(kullaniciSoketi.getOutputStream())), true);

                //Kullanıcıdan gelen verileri okumaya başla
                String str = in.readLine();
                while (str != null) {
                    System.out.println(str);
                    Integer i = new Integer(str);
                    int sayi = i.intValue();
                    //gelen sayının karşılığını bul
                    String ifade = karsiliginiBul(sayi);
                    //bulunan karşılık kullanıcıya gönderiliyor.
                    out.println(ifade);
                    //kullanıcıdan geleni okumaya devam et.
                    str = in.readLine();
                }
            } finally {
                //Her zaman açılan soketler kapatılmalı
                System.out.println("Bağlantı kapatılıyor!");
                kullaniciSoketi.close();
            }
        } finally {
            //sunucu soketi de kapatılacak
            sunucuSoketi.close();
        }
    }
}
