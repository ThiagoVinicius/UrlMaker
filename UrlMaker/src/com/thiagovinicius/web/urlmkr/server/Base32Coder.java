package com.thiagovinicius.web.urlmkr.server;

import java.math.BigInteger;

public class Base32Coder implements IdCoder {

	@Override
	public String encodeId(long id) {
		// replace the negative sign, '-', by z.
		return Long.toString(id, 32).replace('-', 'z');
	}

	@Override
	public long decodeId(String encoded) {
		// replace 'z' by the negative sign '-'
		return new BigInteger(encoded.replace('z', '-'), 32).longValue();
	}

}
