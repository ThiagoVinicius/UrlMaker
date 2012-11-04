package com.thiagovinicius.web.urlmkr.server;

import java.math.BigInteger;

public class HexCoder implements IdCoder {

	@Override
	public String encodeId(long id) {
		return Long.toHexString(id);
	}

	@Override
	public long decodeId(String encoded) {
		//No, Long.parseLong(encoded, 16) won't do it. It doesn't like negative
		//numbers.
		return new BigInteger(encoded, 16).longValue();
	}

}
