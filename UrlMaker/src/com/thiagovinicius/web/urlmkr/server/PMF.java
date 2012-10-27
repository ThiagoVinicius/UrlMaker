package com.thiagovinicius.web.urlmkr.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class PMF {
	
	private final static PersistenceManagerFactory singleton = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	private PMF() {
	};

	public static PersistenceManagerFactory get() {
		return singleton;
	}

}
