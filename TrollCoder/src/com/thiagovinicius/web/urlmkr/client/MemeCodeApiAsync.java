package com.thiagovinicius.web.urlmkr.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface MemeCodeApiAsync {
	void encode(String url, AsyncCallback<Map<String, String>> callback);
}
