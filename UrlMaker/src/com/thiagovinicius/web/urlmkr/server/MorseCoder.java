package com.thiagovinicius.web.urlmkr.server;

public class MorseCoder implements IdCoder {

	@Override
	public String encodeId(long id) {
		// As always, nothing to see here.
		return "";
	}

	@Override
	public long decodeId(String encoded) {
		return 0L;
	}

}
