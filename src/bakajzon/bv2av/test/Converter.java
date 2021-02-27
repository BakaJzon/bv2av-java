package bakajzon.bv2av.test;

import java.io.*;

import bakajzon.bv2av.Offline;
import bakajzon.bv2av.Online;

public class Converter {

    private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

    private static PrintStream w = System.out;

    public static void main(String[] args) throws IOException {
        String line = null;
        while((line = r.readLine()) != null && !line.equalsIgnoreCase("q")) {
            final String bvid;
            final int avid;
            if(line.startsWith("BV")) {
                bvid = line;
                avid = -1;
            } else {
                bvid = null;
                if(line.toLowerCase().startsWith("av")) line = line.substring(2);
                avid = Integer.parseInt(line);
            }
            new Thread(() -> {
                try {
                	Object online, offline;
                    if (avid == -1) {
                        w.print("Offline: ");
                        w.print(bvid);
                        w.print(" -> ");
                        w.println(offline = Offline.toAvidOffline(bvid));
                        w.print("Online: ");
                        w.print(bvid);
                        w.print(" -> ");
                        w.println(online = Online.toAvidOnline(bvid));
                    } else {
                        w.print("Offline: ");
                        w.print(avid);
                        w.print(" -> ");
                        w.println(offline = Offline.toBvidOffline(avid));
                        w.print("Online: ");
                        w.print(avid);
                        w.print(" -> ");
                        w.println(online = Online.toBvidOnline(avid));
                    }
                    if(online != null && !online.equals(-1) && !offline.equals(online))
                    	System.err.println("ERROR!");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }).start();
        }
    }
}
