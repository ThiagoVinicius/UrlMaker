package com.thiagovinicius.web.trollcoder.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.thiagovinicius.web.trollcoder.client.mvp.AppActivityMapper;
import com.thiagovinicius.web.trollcoder.client.mvp.AppPlaceHistoryMapper;
import com.thiagovinicius.web.trollcoder.client.place.CreateRedirectPlace;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TrollCoder implements EntryPoint {

	private SimplePanel appWidget = new SimplePanel();
	
	@Override
	public void onModuleLoad() {
		ClientContext clientFactory = GWT.create(ClientContext.class);
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
	
}
