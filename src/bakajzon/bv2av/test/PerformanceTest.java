package bakajzon.bv2av.test;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import bakajzon.bv2av.Offline;

public class PerformanceTest {
	
	public static final int TIMES = 1024*1024*4;

	public static void main(String[] args) {
		long start, end, rt;
		System.out.println("Test times: " + TIMES);
		{//raw Random.class test
			start = System.currentTimeMillis();
			new Random().ints(TIMES, 1, 2147483647)
					.forEach(i -> { return; });
			end = System.currentTimeMillis();
			System.out.println("Random Performance:  " + (rt = end - start) + "ms");
		}	{//av2bv test
			int[] avids = new Random().ints(TIMES, 1, 2147483647).toArray();

				start = System.currentTimeMillis();
				IntStream.of(avids)
						.forEach(Offline::toBvidOffline);
				end = System.currentTimeMillis();
				System.out.println("Av2bv Test:  " + (end - start - rt) + "ms");
		}  {//bv2av test
			ArrayList<String> bvids = new ArrayList<>(TIMES);
			new Random().ints(TIMES, 1, 2147483647)
					.mapToObj(Offline::toBvidOffline)
					.forEach(bvids::add);
			
				start = System.currentTimeMillis();
				bvids.stream()
						.forEach(Offline::toAvidOffline);
				end = System.currentTimeMillis();
				System.out.println("Bv2av Test:  " + (end - start - rt) + "ms");
		}
	}

}
