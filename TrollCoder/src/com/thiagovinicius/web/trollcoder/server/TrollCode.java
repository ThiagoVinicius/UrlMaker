package com.thiagovinicius.web.trollcoder.server;


public class TrollCode {
	
	public static class Coder implements IdCoder {
		
		public final int minLen, maxLen;
		
		public Coder() {
			this(0, Integer.MAX_VALUE);
		}
		
		public Coder(int minLen, int maxLen) {
			this.minLen = minLen;
			this.maxLen = maxLen;
		}
		
		@Override
		public String encodeId(long id) {
			return new String(TrollCode.encode(id, minLen, maxLen));
		}

		@Override
		public long decodeId(String encoded, int begin) {
			return TrollCode.decodeLong(encoded.substring(begin).toCharArray());
		}
		
	}
	
	private TrollCode() {
	}
	
	public static char[] minimalLengthEncode(byte input[]) {
		return encode(input, 0, Integer.MAX_VALUE);
	}
	
	public static char[] minimalLengthEncode(long input) {
		return encode(input, 0, Integer.MAX_VALUE);
	}
	
	public static char[] encode(long input, int minLen, int maxLen) throws IndexOutOfBoundsException {
		byte data[] = new byte[Long.SIZE/8];
		for (int i = 0; i < data.length; ++i) {
			data[i] = (byte) ((input >> (Long.SIZE - ((i+1)*8))) & 0xff);
		}
		return encode(data, minLen, maxLen);
	}
	
	public static long decodeLong(char encoded[]) {
		byte data[] = decodeBytes(encoded);
		long result = 0L;
		for (int i = 0; i < data.length; ++i) {
			result |= ((long) (data[i]) & 0xff) << (Long.SIZE - ((i+1)*8));
		}
		return result;
	}
	
	public static char[] encode(byte input[], int minLen, int maxLen) {
		//This is not the code you're looking for!
		//The code implementing this method has been intentionally removed!
		//It is maintained elsewhere, and left as an excercise to the reader.
		return new char[]{};
	}
	
	public static byte[] decodeBytes(char encoded[]) {
		//This also is not the code you're looking for...
		//The code implementing this method has been intentionally removed!
		//It is maintained elsewhere, and left as an excercise to the reader.
		return new byte[]{};
	}
	
}
