/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agprogramlama;

//Bu uygulamada iş parçacıkları kullanılarak kullanıcıdan gelen isteklere cevap vermeye çalışılacak
import static agprogramlama.SunucuOrnekIki.karsiliginiBul;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SunucuOrnekUc extends Thread {

    public static final int PORT = 4567;
    Socket soket = null;

    public SunucuOrnekUc(Socket s) {
        this.soket = s;
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
    public void run() {
        try {
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
            System.err.println("Hata: " + this.getName() + "==>" + e);
        } finally {
            System.out.println("Bağlantı kapatılıyor.");
            try {
                soket.close();
            } catch (Exception e) {
                System.err.println("Soket kapatılamadı. Hata: " + this.getName() + "==>" + e);
            }
        }
    }
    
    //Bu uygulama sayesinde artık sunucu kullanıcılara hizmet vermek için hizmet alan kullanıcının işinin bitmesini beklemeyecektir. 
    public static void main(String[] args) throws IOException{
        ServerSocket sunucuSoketi=new ServerSocket(PORT);
        System.out.println("Sunucu başladı.");
        try {
            for (;;) {  //Kullanıcıdan gelen tüm istekler ayrı birer iş parçacığına atanarak kullanıcılara cevap verilmesi sağlanacak.
                Socket kullaniciSoket=sunucuSoketi.accept();
                System.out.println("Bağlantı kabul edildi.");
                
                //İş parçacığına aktarım 
                kullaniciSoket.setSoTimeout(1000);  //Bu yordam sayesinde Socket nesnesine zaman sınırı konulmuştur.  Bu yordam; kullanıcı uygulama 1000 ms üzerinde bir sürede veri gönderirse SocketTimedoutException üretip soketi kapatır.
                new SunucuOrnekUc(kullaniciSoket).start();
            }
        } catch (Exception e) {
        }
    }
}
