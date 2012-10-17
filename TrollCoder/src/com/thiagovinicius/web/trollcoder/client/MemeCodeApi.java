package com.thiagovinicius.web.trollcoder.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("api")
public interface MemeCodeApi extends RemoteService {
	Map<String, String> encode(String url) throws IllegalArgumentException;
}
