package com.thiagovinicius.web.trollcoder.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("api/troll")
public interface TrollCodeApi extends RemoteService {
	String encode(String url) throws IllegalArgumentException;
}
