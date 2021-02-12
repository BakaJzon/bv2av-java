package bakajzon.bv2av.test;

import java.util.Random;

import bakajzon.bv2av.Offline;

public class test2 {
	
	public static final int times = 1024*1024*4;

	public static void main(String[] args) {
		long start, end;
		long randomTime;
		{
			start = System.currentTimeMillis();
			new Random().ints(times, 1, 2147483647/2)
					.parallel()
					.forEach(i -> { return; });
			end = System.currentTimeMillis();
			System.out.println(randomTime = end - start);
		}	{
			start = System.currentTimeMillis();
			new Random().ints(times, 1, 2147483647/2)
					.parallel()
					.forEach(Offline::toBvidOffline);
			end = System.currentTimeMillis();
			System.out.println(end - start);
			System.out.println(end - start - randomTime);
		}
	}

}
