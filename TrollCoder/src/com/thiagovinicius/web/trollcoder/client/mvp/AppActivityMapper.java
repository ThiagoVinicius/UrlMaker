package com.thiagovinicius.web.trollcoder.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.thiagovinicius.web.trollcoder.client.ClientContext;
import com.thiagovinicius.web.trollcoder.client.activity.CreateRedirectActivity;
import com.thiagovinicius.web.trollcoder.client.place.CreateRedirectPlace;

public class AppActivityMapper implements ActivityMapper {

	private ClientContext context;

	public AppActivityMapper(ClientContext ctx) {
		super();
		this.context = ctx;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof CreateRedirectPlace) {
			return new CreateRedirectActivity(context);
		}
		return null;
	}

}
