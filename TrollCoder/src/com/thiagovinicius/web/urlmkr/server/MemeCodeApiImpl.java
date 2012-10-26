package com.thiagovinicius.web.urlmkr.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.thiagovinicius.web.urlmkr.client.MemeCodeApi;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MemeCodeApiImpl extends RemoteServiceServlet implements
		MemeCodeApi {

	@Override
	public Map<String, String> encode(String urlStr) throws IllegalArgumentException {

		URL url = null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException ex1) {
			try {
				url = new URL("http://"+urlStr);
			} catch (MalformedURLException ex2) {
				throw new IllegalArgumentException(ex1.getMessage());
			}
		}

		if (!url.getProtocol().equals("http") && !url.getProtocol().equals("https")) {
			throw new IllegalArgumentException("Protocol not supported: " +
					url.getProtocol());
		}

		RedirectorBinding binding = new RedirectorBinding();
		binding.setUrl(url);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(binding);
		} finally {
			pm.close();
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		StringBuilder uri = new StringBuilder();
		HttpServletRequest req = getThreadLocalRequest();

		for (Coders coderEntry : Coders.values()) {
			String encoded = coderEntry.coder.encodeId(binding.getKey());

			uri.setLength(0);
			uri.append(coderEntry.handle);
			uri.append(encoded);

			try {
				URL orig = new URL(req.getRequestURL().toString());
				URL codedUrl = new URL(orig.getProtocol(), orig.getHost(),
						orig.getPort(), uri.toString());
				resultMap.put(coderEntry.friendlyName, codedUrl.toString());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		return resultMap;
	}
}
