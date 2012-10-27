package com.thiagovinicius.web.urlmkr.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.thiagovinicius.web.urlmkr.client.mvp.AppActivityMapper;
import com.thiagovinicius.web.urlmkr.client.mvp.AppPlaceHistoryMapper;
import com.thiagovinicius.web.urlmkr.client.place.CreateRedirectPlace;
import com.thiagovinicius.web.urlmkr.shared.DefaultValues;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class UrlMaker implements EntryPoint {

	private SimplePanel appWidget = new SimplePanel();

	@Override
	public void onModuleLoad() {
		if (redirectToMainDomain()) {
			return; //don't let the app load, we're done.
		}

		ClientContext clientFactory = GWT.create(ClientContext.class);

		pingServer(clientFactory);

		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();

		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(appWidget);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, new CreateRedirectPlace());

		RootPanel.get().add(appWidget);
		// Goes to place represented on URL or default place
		historyHandler.handleCurrentHistory();
	}

	private boolean redirectToMainDomain() {
		if (GWT.isProdMode() && !Window.Location.getHost().startsWith("127.")) {
			String host = Window.Location.getHost();
			if (!host.equals(DefaultValues.MAIN_DOMAIN)) {
				String url = Window.Location.getHref();
				url = url.replaceFirst(host, DefaultValues.MAIN_DOMAIN);
				Window.Location.replace(url);
				return true;
			}
		}
		return false;
	}

	private void pingServer(ClientContext clientFactory) {
		//Calling ping does nothing, so we do the same.
		clientFactory.getUrlMakerApi().ping(new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {}
			@Override
			public void onFailure(Throwable caught) {}
		});
	}

}
