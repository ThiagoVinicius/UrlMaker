package com.thiagovinicius.web.urlmkr.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.thiagovinicius.web.urlmkr.client.ui.CreateRedirectView;

public interface ClientContext {
	EventBus getEventBus();
	PlaceController getPlaceController();
	
	CreateRedirectView getCreateRedirectView();

	UrlMakerApiAsync getTrollCodeApi();
}
