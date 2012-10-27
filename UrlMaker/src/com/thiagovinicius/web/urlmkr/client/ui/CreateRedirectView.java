package com.thiagovinicius.web.urlmkr.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.thiagovinicius.web.urlmkr.client.activity.CreateRedirectActivity;

public class CreateRedirectView extends Composite implements
		CreateRedirectActivity.View {

	private static CreateRedirectViewUiBinder uiBinder = GWT
			.create(CreateRedirectViewUiBinder.class);

	interface CreateRedirectViewUiBinder extends
			UiBinder<Widget, CreateRedirectView> {
	}

	public CreateRedirectView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private CreateRedirectActivity mActivity;

	@UiField
	Button encode;

	@UiField
	TextBox url;

	@UiField
	Label error;

	@UiField
	Label working;

	@UiField
	VerticalPanel redirects;

	@UiHandler("encode")
	void onClick(ClickEvent e) {
		mActivity.sendUrl(url.getText());
	}

	@UiHandler("url")
	void onKeyUp(KeyUpEvent e) {
		if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			encode.click();
		}
	}

	@Override
	public void setActivity(CreateRedirectActivity act) {
		this.mActivity = act;
	}

	@Override
	public void clearEncodedUrls() {
		redirects.clear();
	}

	@Override
	public void addEncodedUrl(String label, String url) {
		RedirectEntry entry = new RedirectEntry(label, url);
		redirects.add(entry);
	}

	@Override
	public void setError(boolean errorState) {
		error.setVisible(errorState);
	}

	@Override
	public void setWorking(boolean workingState) {
		encode.setEnabled(!workingState);
		working.setVisible(workingState);
	}

}
