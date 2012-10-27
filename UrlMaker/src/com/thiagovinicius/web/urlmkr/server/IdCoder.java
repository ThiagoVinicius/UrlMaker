package com.thiagovinicius.web.urlmkr.server;

public interface IdCoder {
	public String encodeId(long id);
	public long decodeId(String encoded, int begin);
}
