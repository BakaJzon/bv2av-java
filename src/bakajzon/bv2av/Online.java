package bakajzon.bv2av;

import java.io.*;
import java.net.*;

public class Online {
	
	public static int toAidOnline(String bvid) throws IOException {
		if(!bvid.startsWith("BV")) throw new IllegalArgumentException("bvid starts with \"BV\"");
		String data = httpString("http://api.bilibili.com/x/web-interface/view?bvid=" + bvid);
		if(data == null) return -1;
		int i = data.indexOf("aid");
		if(i == -1) return -1;
		while(data.charAt(i) != ':') i++;
		int begin = ++i, end = i;
		char c;
		while(!((c = data.charAt(end)) == ',' || c == '}')) end++;
		return Integer.parseInt(data.substring(begin, end));
	}
	
	public static String toBvidOnline(int aid) throws IOException {
		if(aid < 0) throw new IllegalArgumentException("aid starts at 0");
		String data = httpString("http://api.bilibili.com/x/web-interface/view?aid=" + aid);
		if(data == null) return null;
		int i = data.indexOf("\"bvid\"");
		if(i == -1) return null;
		i = data.indexOf("BV", i);
		if(i == -1) return null;
		return data.substring(i, i + 12);
	}

	private static String httpString(String urlstr) throws IOException {
		URL url = new URL(urlstr);
		try(Socket s = new Socket()) {
			new Thread(() -> {
				try {
					Thread.sleep(6000);
					if(!s.isClosed()) s.close();
				} catch (InterruptedException | IOException e) {
				}

			}).start();
			s.connect(new InetSocketAddress(url.getHost(), 80), 5800);
			
			PrintWriter w = new PrintWriter(new BufferedOutputStream(s.getOutputStream(), 128));
			w.println("GET " + url.getPath() + '?' + url.getQuery() + " HTTP/1.1");
			w.println("Connection: close");
			w.println("Host: " + url.getHost());
			w.println("Accept-Charset: utf-8");
			w.println("Accept-Encoding: none");
			w.println("Accept: */*");
			w.println("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.146 Safari/537.36");
			w.println();
			w.println();
			w.flush();
			s.shutdownOutput();
			
			BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()), 128);
			if(r.readLine().split(" ")[1].charAt(0) != '2')
				return null;
			String line;
			int length = -1;
			while((line = r.readLine()) != null && !line.equals("")) {
				String[] httpheader = line.split(": ");
				if(httpheader[0].equals("Content-Length")) length = Integer.parseInt(httpheader[1]);
			}
			StringBuilder stringBuilder = new StringBuilder(length == -1 ? 128 : length);
			line = null;
			while((line = r.readLine()) != null) stringBuilder.append(line);
			return stringBuilder.toString();
		}
	}
}
