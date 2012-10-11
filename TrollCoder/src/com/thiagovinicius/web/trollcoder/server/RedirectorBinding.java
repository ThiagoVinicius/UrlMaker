package com.thiagovinicius.web.trollcoder.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import com.google.appengine.api.datastore.Text;


@PersistenceCapable(detachable="true")
public class RedirectorBinding implements StoreCallback {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long key;
	
	@Persistent
	private Text url;
	
	@Persistent
	private Date createdAt;

	public URL getUrl() {
		try {
			return new URL(url.getValue());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setUrl(URL url) {
		this.url = new Text(url.toString());
	}

	public long getKey() {
		return key;
	}
	
	public Date getCreationTimestamp() {
		return createdAt;
	}
	
	@Override
	public void jdoPreStore() {
		createdAt = new Date();
	}

}
