package com.thiagovinicius.web.urlmkr.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RedirectEntry extends Composite{

	private static RedirectEntryUiBinder uiBinder = GWT
			.create(RedirectEntryUiBinder.class);

	interface RedirectEntryUiBinder extends UiBinder<Widget, RedirectEntry> {
	}

	public RedirectEntry(String codeIdText, String encodedUrl) {
		initWidget(uiBinder.createAndBindUi(this));
		this.codeId.setText(codeIdText);
		this.encoded.setText(encodedUrl);
	}

	@UiField
	Label codeId;

	@UiField
	TextBox encoded;

	@UiField
	Label copy;

	@UiHandler("encoded")
	public void onClick(ClickEvent e) {
		encoded.setFocus(true);
		encoded.setSelectionRange(0, encoded.getText().length());
		copy.setVisible(true);
	}

	@UiHandler("encoded")
	public void onFocus(BlurEvent e) {
		encoded.setSelectionRange(0, 0);
		copy.setVisible(false);
	}

}
