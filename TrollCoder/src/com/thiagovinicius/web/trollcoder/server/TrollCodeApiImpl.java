package com.thiagovinicius.web.trollcoder.server;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.thiagovinicius.web.trollcoder.client.TrollCodeApi;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class TrollCodeApiImpl extends RemoteServiceServlet implements
		TrollCodeApi {

	@Override
	public String encode(String urlStr) throws IllegalArgumentException {

		URL url = null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}

		RedirectorBinding binding = new RedirectorBinding();
		binding.setUrl(url);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(binding);
		} finally {
			pm.close();
		}

		char encoded[] = TrollCode.encode(binding.getKey(), 1800, 1800);
		StringBuilder uri = new StringBuilder();
		uri.append(Coders.getHandle(TrollCode.Coder.class));
		uri.append(encoded);

		HttpServletRequest req = getThreadLocalRequest();
		URL result = null;
		try {
			URL orig = new URL(req.getRequestURL().toString());
			result = new URL(orig.getProtocol(), orig.getHost(),
					orig.getPort(), uri.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return result.toString();
	}
}
