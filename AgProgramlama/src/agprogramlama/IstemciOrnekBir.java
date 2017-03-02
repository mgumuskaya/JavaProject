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
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author gmsky
 */
public class IstemciOrnekBir {

    public static void main(String[] args) throws IOException {
        InetAddress adres = InetAddress.getByName(null); //InetAddress nesnesi elde ediliyor. Bu nesne ile üzerinde çalışılan sistemin local host ve IP bilgilerini elde ediyoruz.
        System.out.println("IP Adresi: " + adres);
        Socket soket = new Socket(adres, 4567);//Kullanıcı uygulaması sunucu uygulamasına bağlanmış oluyor.

        try {
            System.out.println("soket açılıyor " + soket);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(soket.getInputStream()));

            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(soket.getOutputStream())), true);

            for (int i = 0; i < 10; i++) {
                try {
                    //Sunucuya verileri bekleterek gönder
                    Thread.sleep(10000);
                    //0-9 arası rastgele sayı üret
                    int sayi = (int) (Math.random() * 10);
                    //üretilen rastgele sayıları sunucuya gönder
                    out.println(sayi);
                    //Sunucudan dönen cevabı oku
                    String str = in.readLine();
                    System.out.println("-->" + sayi + "=" + str);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            System.out.println("bağlantı kapatılıyor");
            soket.close();
        }
    }

}
