package com.thiagovinicius.web.urlmkr.server;

import java.math.BigInteger;

public class DnaCoder implements IdCoder {

	private static final char CODEWORDS[] = { 'A', 'T', 'G', 'C' };

	@Override
	public String encodeId(long id) {
		BigInteger bint = new BigInteger(Long.toBinaryString(id).replace('-', '1'), 2);
		String result = bint.toString(CODEWORDS.length);
		for (int i = 0; i < CODEWORDS.length; ++i) {
			result = result.replace(
					Integer.toString(i).charAt(0),
					CODEWORDS[i]);
		}
		return result;
	}

	@Override
	public long decodeId(String encoded) {
		String decoded = encoded.toUpperCase();
		for (int i = 0; i < CODEWORDS.length; ++i) {
			decoded = decoded.replace(
					CODEWORDS[i],
					Integer.toString(i).charAt(0));
		}
		return new BigInteger(decoded, CODEWORDS.length).longValue();
	}

}
