package com.thiagovinicius.web.urlmkr.server;


public class FuCoder {

	public static class Coder implements IdCoder {

		@Override
		public String encodeId(long id) {
			return FuCoder.encode(id);
		}

		@Override
		public long decodeId(String encoded) {
			return FuCoder.decode(encoded);
		}

	}

	public static String encode(long id) {
		// Not that easy, boy. Go think about how it is implemented.
		return "";
	}

	public static long decode(String encodedStr) {
		// I said: Not that easy.
		return 0L;
	}

}
