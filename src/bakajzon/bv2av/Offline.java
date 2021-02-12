package bakajzon.bv2av;

import java.math.BigInteger;

/**
 * from mcfx - https://www.zhihu.com/question/381784377/answer/1099438784
 * @author CNOwl
 */
public class Offline {
	
	static class Model {	
		static final char[] TABLE, TR;
		
		static final byte[] S = new byte[] {11,10,3,8,4,6};
		
		static final long XOR = 177451812, ADD = 8728348608L;
		
		static final BigInteger fifty_eight = BigInteger.valueOf(58);
		
		static {
			TABLE = "fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF".toCharArray();
			TR = new char[124];
			for (char i = 0; i < 58; i++)
				TR[TABLE[i]] = i;
		}
	}

	public static int toAidOffline(String bvid) {
		if(!bvid.startsWith("BV")) throw new IllegalArgumentException("bvid must start with \"BV\"");
		BigInteger r = BigInteger.ZERO;
		char[] bvchars = bvid.toCharArray();
		for (int i = 0; i < 6; i++)
			r = r.add(BigInteger.valueOf(Model.TR[bvchars[Model.S[i]]]).multiply(Model.fifty_eight.pow(i)));
		return (int) ((r.longValue() - Model.ADD) ^ Model.XOR);
	}
	
	public static String toBvidOffline(int aid) {
		if(aid < 0) throw new IllegalArgumentException("aid must greater than 0");
		BigInteger x = BigInteger.valueOf((aid ^ Model.XOR) + Model.ADD);
		char[] bvchars = "BV1  4 1 7  ".toCharArray();
		for(int i = 0; i < 6; i++)
			bvchars[Model.S[i]] = Model.TABLE[
			                      x.divide(Model.fifty_eight.pow(i))//          x//58**i
			                      .remainder(Model.fifty_eight).intValue()];//          %58
		return String.valueOf(bvchars);
	}
}
