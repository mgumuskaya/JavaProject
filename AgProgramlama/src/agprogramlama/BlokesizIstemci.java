/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agprogramlama;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author gmsky
 */
class Robot extends Thread {

    public void beklet(String isim) {
        try {
            int sure = 0;
            if (isim.equals("Thread-1")) {
                sure = 1000;
            } else if (isim.equals("Thread-2")) {
                sure = 2000;
            } else if (isim.equals("Thread-3")) {
                sure = 1500;
            }
            Thread.sleep(sure);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void run() {
        try {
            InetAddress adres = InetAddress.getByName(null);
            Socket soket = new Socket(adres, 1234);
            boolean hata = false;
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(soket.getInputStream()));

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soket.getOutputStream())), true);

                StringBuffer sb = null;
                try {
                    int sayi = (int) (Math.random() * 10);
                    out.println(sayi);
                    beklet(this.getName());
                    sb = new StringBuffer();
                    int gelen = in.read();
                    while (gelen != '\n') {
                        sb.append((char) gelen);
                        gelen = in.read();
                    }
                } catch (Exception e) {
                    hata = true;
                    System.out.println(this.getName() + ": Ben sunucuya bağlanamadım.");
                }
                if (!hata) {
                    System.out.println(this.getName() + ": Sunucuya bağlandım. Sonuç: " + sb);
                }
            } finally {
                System.out.println(this.getName() + " Bağlantı kapatılıyor.");
                soket.close();
            }
        } catch (Exception e) {
            System.err.println("Hata: " + e);
        }
    }
}

public class BlokesizIstemci {

    public static void main(String[] args) {
        int istek_limiti = 7;
        for (int i = 0; i < istek_limiti; i++) {
            Robot r = new Robot();
            r.start();
        }
    }
}
