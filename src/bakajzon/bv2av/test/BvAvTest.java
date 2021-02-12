package bakajzon.bv2av.test;

import bakajzon.bv2av.Offline;
import bakajzon.bv2av.Online;

import java.io.*;

public class BvAvTest {

    private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

    private static PrintStream w = System.out;

    public static void main(String[] args) throws IOException {
        String line = null;
        while((line = r.readLine()) != null && !line.equalsIgnoreCase("q")) {
            final String bvid;
            final int aid;
            if(line.startsWith("BV")) {
                bvid = line;
                aid = -1;
            } else {
                bvid = null;
                if(line.toLowerCase().startsWith("av")) line = line.substring(2);
                aid = Integer.parseInt(line);
            }
            new Thread(() -> {
                try {
                    if (aid == -1) {
                        w.print("Offline: ");
                        w.print(bvid);
                        w.print(" -> ");
                        w.println(Offline.toAidOffline(bvid));
                        w.print("Online: ");
                        w.print(bvid);
                        w.print(" -> ");
                        w.println(Online.toAidOnline(bvid));
                    } else {
                        w.print("Offline: ");
                        w.print(aid);
                        w.print(" -> ");
                        w.println(Offline.toBvidOffline(aid));
                        w.print("Online: ");
                        w.print(aid);
                        w.print(" -> ");
                        w.println(Online.toBvidOnline(aid));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }).start();
        }
    }
}
