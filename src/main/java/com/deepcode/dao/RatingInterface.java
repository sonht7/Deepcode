package com.deepcode.dao;

import java.util.List;

import com.deepcode.model.Rating;

public interface RatingInterface {
	public Rating add(Rating model);
	public boolean delete(int id);
	public Rating update(Rating model);
	public List<Rating> getAll();
	public Rating getById(int id);

}
