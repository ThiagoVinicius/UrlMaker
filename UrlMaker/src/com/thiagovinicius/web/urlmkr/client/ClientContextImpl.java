package com.thiagovinicius.web.urlmkr.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.thiagovinicius.web.urlmkr.client.ui.CreateRedirectView;

public class ClientContextImpl implements ClientContext {
	
	private static final EventBus eventBus = new SimpleEventBus();
	private static final PlaceController placeController = new PlaceController(eventBus);
	
	private static final CreateRedirectView createRedirectView = new CreateRedirectView();
	private static final UrlMakerApiAsync urlMakerApi = GWT.create(UrlMakerApi.class);
	
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

	@Override
	public UrlMakerApiAsync getUrlMakerApi() {
		return urlMakerApi;
	}

	@Override
	public native void trackEvent(String category, String action, String label)
	/*-{
		$wnd._gaq.push(['_trackEvent', category, action, label]);
	}-*/;

	@Override
	public native void trackEvent(String category, String action, String label,
			int intArg) /*-{
		$wnd._gaq.push(['_trackEvent', category, action, label, intArg]);
	}-*/;

	@Override
	public native void trackPageview(String url) /*-{
		$wnd._gaq.push(['_trackPageview', url]);
	}-*/;

}
