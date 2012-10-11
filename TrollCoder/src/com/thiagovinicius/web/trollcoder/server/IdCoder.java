package com.thiagovinicius.web.trollcoder.server;

public interface IdCoder {
	public String encodeId(long id);
	public long decodeId(String encoded, int begin);
}
