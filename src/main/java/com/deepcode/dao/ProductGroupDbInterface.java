package com.deepcode.dao;

import java.util.List;

import com.deepcode.model.ProductGroup;


public interface ProductGroupDbInterface {
	
	public ProductGroup add(ProductGroup model);
	public boolean delete(int id);
	public ProductGroup update(ProductGroup model);
	public List<ProductGroup> getAll();
	public ProductGroup getById(int id);
	
	
	
	
	

}
