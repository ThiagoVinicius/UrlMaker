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
	}

	private ClientContext context;
	private View view;
	private UrlMakerApiAsync trollEncoder;
	
	public CreateRedirectActivity(ClientContext ctx) {
		this.context = ctx;
		this.trollEncoder = ctx.getTrollCodeApi();
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
		trollEncoder.encode(url, new AsyncCallback<Map<String, String>>() {
			@Override
			public void onSuccess(Map<String, String> result) {
				view.setError(false);
				view.clearEncodedUrls();
				String keys[] = result.keySet().toArray(new String[]{});
				Arrays.sort(keys);
				for (String k : keys) {
					view.addEncodedUrl(k, result.get(k));
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				view.setError(true);
			}
		});
	}

}
