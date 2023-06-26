package com.deepcode.request;

import com.deepcode.model.Rating;

public class AddRatingByFarmId {
	private int farmId;
	private Rating rating;
	
	public int getFarmId() {
		return farmId;
	}
	public void setFarmId(int farmId) {
		this.farmId = farmId;
	}
	public Rating getRating() {
		return rating;
	}
	public void setRating(Rating rating) {
		this.rating = rating;
	}
	
	

}
