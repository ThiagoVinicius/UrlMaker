package com.thiagovinicius.web.trollcoder.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.thiagovinicius.web.trollcoder.client.ui.CreateRedirectView;

public class ClientContextImpl implements ClientContext {
	
	private static final EventBus eventBus = new SimpleEventBus();
	private static final PlaceController placeController = new PlaceController(eventBus);
	
	private static final CreateRedirectView createRedirectView = new CreateRedirectView();
	
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public CreateRedirectView getCreateRedirectView() {
		return createRedirectView;
	}

}
