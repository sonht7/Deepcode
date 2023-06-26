package com.deepcode.dao;

import java.util.List;

import com.deepcode.model.Farm;
import com.deepcode.model.Rating;


public interface FarmDbInterface {
	
	public Farm add(Farm model);
	public boolean delete(int id);
	public Farm update(Farm model);
	public List<Farm> getAll();
	public Farm getById(int id);
	
	public Farm addRatingByFarmId(Rating model, int farmId);
	public boolean deleteRatingbyFarmId(int ratingId, int farmId);
	public Farm updateRatingByFarmId(Rating model, int farmId);
	public List<Farm> getAllRatingByFarmId(int farmId);
	public Farm getRatingByFarmId(int farmid);

	


}
