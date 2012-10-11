package com.thiagovinicius.web.trollcoder.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CreateRedirectPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<CreateRedirectPlace> {

		@Override
		public CreateRedirectPlace getPlace(String token) {
			return new CreateRedirectPlace();
		}
		@Override
		public String getToken(CreateRedirectPlace place) {
			return "";
		}
		
	}
	
}
