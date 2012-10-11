package com.thiagovinicius.web.trollcoder.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.thiagovinicius.web.trollcoder.client.ClientContext;

public class CreateRedirectActivity extends AbstractActivity {

	ClientContext context;
	
	public CreateRedirectActivity(ClientContext ctx) {
		this.context = ctx;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(context.getCreateRedirectView());
	}

}
