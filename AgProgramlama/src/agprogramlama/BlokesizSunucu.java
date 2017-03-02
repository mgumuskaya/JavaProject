/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agprogramlama;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author gmsky
 */
public class BlokesizSunucu {

    public Selector sel = null;
    public ServerSocketChannel sunucu = null;
    public SocketChannel soket = null;
    public int port = 1234;

    //varsayılan yapılandırıcı
    public BlokesizSunucu() {
        System.out.println("Varsayılan Yapılandırıcı");
    }

    /**
     * port numarası kullanılan yapılandırıcı
     *
     * @param port
     */
    public BlokesizSunucu(int port) {
        port = port;
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

    /**
     * Kanalları oluşturulduğu ve port numarasına bağlandığı yordam
     *
     * @exception IOException
     * @exception UnknownHostException
     */
    public void islemleriBaslat() throws IOException {
        System.out.println("İşlemler başlatılıyor.");
        sunucu = ServerSocketChannel.open(); //ServerSocketChannel nesnesi elde ediliyor.
        sunucu.configureBlocking(false); //bağlantının blokesiz olacağı belirtilir true gönderilirse blokeli bağlantı oluşturulur.
        InetAddress ia = InetAddress.getByName(null);
        InetSocketAddress isa = new InetSocketAddress(ia, port); //bilgisayar bilgileri ve port belirtilerek tutuluyor
        sunucu.socket().bind(isa); //Sunucu belirtilen port numarasına bağlanır.
    }

    /**
     * Kullanıcıdan gelen isteklerin karşılandığı ve ilgili yordamlara
     * yönlendirmeler bu yordam içerisinde yapılıyor
     *
     * @exception IOException
     */
    public void sunucuyuBaslat() throws IOException {
        System.out.println("sunucuyuBaslat() çağırıldı.");
        islemleriBaslat();
        sel = Selector.open(); //Seçici nesnesi oluşturuldu.
        System.out.println("Sunucu istekleri kabul etmeye hazır.");
        SelectionKey kabulAnahtari = sunucu.register(sel, SelectionKey.OP_ACCEPT); //Kanalların kullanıcıdan gelen isteklere yanıt verebilmesi için kendilerini seçiciye kayıt ettirmeleri gerekir. Seçici ile kanal arasındaki ilişkiyi seçim anahtarı ifade eder.
        //kullanıcıdan gelen isteklerin karşılanması aşaması
        while (kabulAnahtari.selector().select() > 0) {
            Set hazir_anahtarlar = sel.selectedKeys(); //Anahtarlar Set arayüzü tipindeki nesneye atılıyor.
            Iterator iterator = hazir_anahtarlar.iterator();
            while (iterator.hasNext()) {
                SelectionKey anahtar = (SelectionKey) iterator.next();
                iterator.remove(); //kullanıcı isteğine atanan anahtar çıkarılıyor.

                if (anahtar.isAcceptable()) {
                    System.out.println("Bağlantı kabul edildi.");
                    ServerSocketChannel ssc = (ServerSocketChannel) anahtar.channel();
                    soket = (SocketChannel) ssc.accept();
                    soket.configureBlocking(false);
                    SelectionKey diger = soket.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                }
                if (anahtar.isReadable()) {
                    System.out.println("Anahtar okunabilir.");
                    String ret = mesajOku(anahtar);
                    if ((ret == null) && (ret.length() > 0)) {
                        mesajGonder(soket, ret);
                    } else {
                        continue;
                    }
                }
                if (anahtar.isWritable()) {
                    System.out.println("Anahtar yazılabilir.");
                    String ret = mesajOku(anahtar);
                    soket = (SocketChannel) anahtar.channel();
                    if ((ret == null) && (ret.length() > 0)) {
                        mesajGonder(soket, ret);
                    } else {
                        continue;
                    }
                }

            }
        }
    }

    //Verileri kullanıcıya gönderen yordam
    public void mesajGonder(SocketChannel soket, String ret) {
        try {
            ret = ret.trim();
            Integer i = new Integer(ret.trim());
            int sayi = i.intValue();

            String ifade = karsiliginiBul(sayi);
            StringBuffer sb = new StringBuffer();
            sb.append(ifade);
            sb.append("\n");

            ByteBuffer buffer = ByteBuffer.wrap(sb.toString().getBytes());
            int nBytes = soket.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Kullanıcıdan gelen verileri okuyan yordam
    public String mesajOku(SelectionKey anahtar) {
        int nBytes = 0;
        soket = (SocketChannel) anahtar.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String sonuc = null;

        try {
            nBytes = soket.read(buffer);
            buffer.flip();
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer char_buffer = decoder.decode(buffer);
            sonuc = char_buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sonuc;
    }
    
    /**Blokesiz sunucu başlatılıyor*/
    public static void main(String[] args) {
        BlokesizSunucu bs=new BlokesizSunucu();
        try {
            bs.sunucuyuBaslat();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
