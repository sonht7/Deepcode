package com.deepcode.dao;

import java.util.List;

import com.deepcode.model.Category;

public interface CategoryDbInterface {
	
	public Category add(Category model);
	public boolean delete(int id);
	public Category update(Category model);
	public List<Category> getAll();
	public Category getById(int id);
	
	
	
	

}
