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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

class IsVeren extends Thread {

    @Override
    public void run() {
        try {
            InetAddress adres = InetAddress.getByName(null);
            Socket soket = new Socket(adres, 4567);
//Soketin kapanmasını garantilemek için işi yapan kodlar try içine alınır
            boolean hata = false;
            try {
                System.out.println("soket açılıyor " + soket);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(soket.getInputStream()));

                PrintWriter out = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(soket.getOutputStream())), true);
                try {
                    int sayi = (int) (Math.random() * 10);
                    out.println(sayi);
                    String str = in.readLine();
                } catch (Exception e) {
                    hata = true;
                    System.out.println(this.getName() + " ben sunucuya bağlanamadımmm...");
                }
                if (!hata) {
                    System.out.println(this.getName() + " ben sunucuya bağlandum.");
                }

            } finally {
                System.out.println("Bağlantı kapatılıyor.");
                soket.close();
            }
        } catch (IOException ex) {
            System.err.println(this.getName() + " hata oluştu : " + ex);
        }
    }
}

public class IstemciIki {

    public static void main(String[] args) {
        int istek_limiti = 3;
        /*if (args.length == 1) {
            try {
                istek_limiti = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.err.println("Sayı belirtiniz.");
                System.exit(-1);
            }
        } else {
            System.err.println("Karşı tarafa kaç istek gönderileceği belirtilmeli.");
            System.exit(-1);
        }*/
        for (int i = 0; i < istek_limiti; i++) {
            IsVeren m = new IsVeren();
            m.start();
        }
    }
}
