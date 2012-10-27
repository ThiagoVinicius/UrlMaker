package com.thiagovinicius.web.urlmkr.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.utils.SystemProperty;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.thiagovinicius.web.urlmkr.client.UrlMakerApi;
import com.thiagovinicius.web.urlmkr.shared.DefaultValues;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UrlMakerApiImpl extends RemoteServiceServlet implements
		UrlMakerApi {

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
				String codedUrl = getRedirectUrl(req, uri.toString());
				resultMap.put(coderEntry.friendlyName, codedUrl);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		return resultMap;
	}

	private String getRedirectUrl(HttpServletRequest req, String uri)
			throws MalformedURLException {
		if (SystemProperty.environment.value() ==
				SystemProperty.Environment.Value.Production) {
			//production environment
			return new URL("http", DefaultValues.REDIRECT_DOMAIN, uri).toString();
		} else {
			//development environment
			URL orig = new URL(req.getRequestURL().toString());
			return new URL(orig.getProtocol(), orig.getHost(), orig.getPort(),
					uri).toString();
		}
	}

	@Override
	public void ping() {
	}

}
