package com.thiagovinicius.web.urlmkr.server;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class UrlUnmaker extends HttpServlet {

	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String encoded = req.getRequestURI();
		AtomicInteger offset = new AtomicInteger();
		IdCoder coder = Coders.getCoder(encoded, offset);
		if (coder == null) {
			resp.sendError(404);
			return;
		}
		Long id = coder.decodeId(encoded.substring(offset.get()));
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			RedirectorBinding redir = pm.getObjectById(RedirectorBinding.class,
					id);
			resp.sendRedirect(redir.getUrl().toExternalForm());
		} finally {
			pm.close();
		}
	}
	
}
