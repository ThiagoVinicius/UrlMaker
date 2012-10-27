package com.thiagovinicius.web.urlmkr.client.activity;

import java.util.Arrays;
import java.util.Map;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.thiagovinicius.web.urlmkr.client.ClientContext;
import com.thiagovinicius.web.urlmkr.client.UrlMakerApiAsync;
import com.thiagovinicius.web.urlmkr.client.ui.CreateRedirectView;

public class CreateRedirectActivity extends AbstractActivity {

	public interface View {
		public void setActivity(CreateRedirectActivity act);
		public void clearEncodedUrls();
		public void addEncodedUrl(String label, String url);
		public void setError(boolean error);
		public void setWorking(boolean working);
	}

	private ClientContext context;
	private View view;
	private UrlMakerApiAsync urlMakerRemote;
	
	public CreateRedirectActivity(ClientContext ctx) {
		this.context = ctx;
		this.urlMakerRemote = ctx.getUrlMakerApi();
		this.view = null;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		CreateRedirectView view = context.getCreateRedirectView();
		this.view = view;
		this.view.setActivity(this);
		this.view.setError(false);
		panel.setWidget(view);
	}

	public void sendUrl(String url) {
		view.clearEncodedUrls();
		view.setError(false);
		view.setWorking(true);
		urlMakerRemote.encode(url, new AsyncCallback<Map<String, String>>() {
			@Override
			public void onSuccess(Map<String, String> result) {
				view.setError(false);
				view.setWorking(false);
				String keys[] = result.keySet().toArray(new String[]{});
				Arrays.sort(keys);
				for (String k : keys) {
					view.addEncodedUrl(k, result.get(k));
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				view.setError(true);
				view.setWorking(false);
			}
		});
	}

}
