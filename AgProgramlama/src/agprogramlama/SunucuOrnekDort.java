/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agprogramlama;

//Her kullanıcı isteği için ayrı bir iş parçacığı oluşturmak sistem kaynaklarının tüketilmesi açısından zararlı olabilir.
import static agprogramlama.SunucuOrnekUc.PORT;
import static agprogramlama.SunucuOrnekUc.karsiliginiBul;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class Hizmetci extends Thread {

    public Socket soket = null;
    public ArrayList havuz = null;

    public Hizmetci(ArrayList havuz) {
        this.havuz = havuz;
    }

    public synchronized void soketBelirt(Socket s) {
        this.soket = s;
        notify();
    }

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

    @Override
    public synchronized void run() {
        while (true) {
            try {
                if (soket == null) {
                    //soket yoksa kullanıcı da yok, bekle
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        continue;
                    }
                }

                System.out.println("Bağlantı alan iş parçacığı: " + this.getName() + " port numarası: " + soket);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(soket.getInputStream()));

                PrintWriter out = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(soket.getOutputStream())), true);
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
            } catch (IOException e) {
                System.err.println("Hata:" + this.getName() + "-->" + e);
            } finally {
                try {
                    soket.close(); //soket kapatıldıktan sonra
                    soket = null;  //boşaltılıp
                    havuz.add(soket); //yeni kullanıcı isteği için havuza atılacak
                } catch (IOException ex) {
                    System.err.println("Soket kapatılamadı. " + this.getName() + "-->" + ex);
                }
            }

        }
    }

}
//Bu nedenle İş parçacığı havuzu oluşturup aynı anda bu havuzdaki boşta duran iş parçacığı sayısı kadar kullanıcıya izin 
//verip havuza iş parçacığı devredildikçe kalan kullanıcı isteklerinin karşılanması daha iyi bir çözüm sunacaktır.

public class SunucuOrnekDort {

    public static final int PORT = 4567;
    public ArrayList havuz = new ArrayList();

    public void basla(int havuz_limiti) throws IOException {
        ServerSocket sunucuSoketi = new ServerSocket(PORT);
        System.out.println("Sunucu başladı. " + sunucuSoketi);

        for (int i = 0; i < havuz_limiti; i++) {
            Hizmetci h = new Hizmetci(havuz);
            h.start();
            havuz.add(h);
            System.out.println(h + " havuza eklendi...");
        }
        try {
            while (true) {
                Socket kullaniciSoketi = sunucuSoketi.accept();

                synchronized (havuz) {
                    if (havuz.isEmpty()) {
                        System.out.println("Yeni istek geldi ama boşta hizmetçi yok.");
                        kullaniciSoketi.close();
                    } else {
                        Hizmetci h = (Hizmetci) havuz.get(0);
                        havuz.remove(0);
                        kullaniciSoketi.setSoTimeout(5000); //zaman aşımı
                        h.soketBelirt(kullaniciSoketi);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Hata : " + e);
        } finally {
            sunucuSoketi.close();
        }
    }

    public static void main(String[] args) throws IOException {
        int havuz_limiti = 5;
        /*if (args.length==1) {
            try {
                havuz_limiti=Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.err.println("Sayı belirtiniz.");
                System.exit(-1);
            }
        }else{
            System.err.println("Havuzun içerisinde kaç adet iş parçacığı olması gerektiği belirtilmeli.");
            System.exit(-1);
        }*/
        SunucuOrnekDort sunucu=new SunucuOrnekDort();
        sunucu.basla(havuz_limiti);
    
    }
}
