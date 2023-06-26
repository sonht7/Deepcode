package com.deepcode.model;

import java.util.Date;

public class ProductGroup {
	
	private int id;
	private int categoryId;
	private Date createdDate;
	private Date modifiedDate;
	private String name;
	private String image;
	private boolean isDelete;
	private boolean isActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean boolean1) {
		this.isActive = boolean1;
	}
	
	
	
	
	
	
	

}
