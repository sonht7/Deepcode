package com.deepcode.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.deepcode.core.Constants;
import com.deepcode.core.MongoDB;
import com.deepcode.keys.ProductGroupKey;
import com.deepcode.model.ProductGroup;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

public class ProductGroupDb implements ProductGroupDbInterface{
	
	public static final String COLLECTION_CATEGORY = "productgroup";

	public static ProductGroupDb instance = null;

	public static ProductGroupDb getInstance() {

		if (instance == null) {

			return new ProductGroupDb();
		}

		return instance;
	}
	
	

	@Override
	public ProductGroup add(ProductGroup model) {
		// TODO Auto-generated method stub
		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);

		// TODO Auto-generated method stub
		int id = (int) MongoDB.getNextSequence(ProductGroupKey.productGroupId);
		model.setId(id);
		Document document = new Document();

		/*
		 * document .append("_id", id); document.append("name", model.getName());
		 * document.append("createdDate", new Date());
		 */

		document.append(ProductGroupKey.id, id);
		document.append(ProductGroupKey.categoryId, model.getCategoryId());
		document.append(ProductGroupKey.createdDate,new Date());
		document.append(ProductGroupKey.image, model.getImage());
		document.append(ProductGroupKey.isActive, true);
		document.append(ProductGroupKey.isDelete, false);
		document.append(ProductGroupKey.modifiedDate, null);
		document.append(ProductGroupKey.name,model.getName());
		
		
		
		

		store.insertOne(document);

		return model;
		
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		
		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);

		Document filter = new Document(ProductGroupKey.id, id);
		Document doc = new Document()
				.append(ProductGroupKey.isDelete, true)
				.append(ProductGroupKey.modifiedDate, new Date());
		
		Document newValues = new Document("$set", doc);
		store.findOneAndUpdate(filter, newValues);

		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ProductGroup update(ProductGroup model) {
		// TODO Auto-generated method stub
		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);
		// TODO Auto-generated method stub
		
		Document filter = new Document(ProductGroupKey.isDelete,false)
				.append(ProductGroupKey.id, model.getId());
		
		Document document = new Document();
		if (model.getName() != null)
			document.append(ProductGroupKey.name, model.getName());
			document.append(ProductGroupKey.modifiedDate, new Date());
			document.append(ProductGroupKey.image, model.getImage());
			document.append(ProductGroupKey.isActive, model.getIsActive());
			document.append(ProductGroupKey.isDelete, model.getIsDelete());

		FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
		options.returnDocument(ReturnDocument.AFTER);
		options.upsert(false);
		Document newValues = new Document("$set", document);
		Document documentNew = store.findOneAndUpdate(filter, newValues, options);
		return toProductGroup(documentNew);
	}

	@Override
	public List<ProductGroup> getAll() {
		// TODO Auto-generated method stub
		
		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);
		// TODO Auto-generated method stub
		
		Document filter = new Document(ProductGroupKey.isDelete, false);
		List<ProductGroup> lsReturn = new ArrayList<ProductGroup>();
		
		FindIterable<Document> iterable = store.find(filter);
		Iterator<Document> iter = iterable.iterator();
		
		while (iter.hasNext()) {
			Document doc = iter.next();
			lsReturn.add(toProductGroup(doc));
		}
		return lsReturn;
		
	}

	@Override
	public ProductGroup getById(int id) {
		// TODO Auto-generated method stub
		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);
		
		// TODO Auto-generated method stub
		
		Document filter = new Document(ProductGroupKey.isDelete, false)
				.append(ProductGroupKey.id, id);
		FindIterable<Document> iterable = store.find(filter);
		Iterator<Document> iter = iterable.iterator();
		if (iter.hasNext()) {
			Document doc = iter.next();
			return toProductGroup(doc);
		}
		 return null;	

	}
	
	private static ProductGroup toProductGroup(Document doc) {
		ProductGroup productGroup = new ProductGroup();

		productGroup.setCategoryId(doc.getInteger(ProductGroupKey.categoryId));
		productGroup.setCreatedDate(doc.getDate(ProductGroupKey.createdDate));
		productGroup.setImage(doc.getString(ProductGroupKey.image));
		productGroup.setId(doc.getInteger(ProductGroupKey.id));
		productGroup.setIsActive(doc.getBoolean(ProductGroupKey.isActive));
		productGroup.setIsDelete(doc.getBoolean(ProductGroupKey.isDelete));
		
		productGroup.setModifiedDate(doc.getDate(ProductGroupKey.modifiedDate));
		productGroup.setName(doc.getString(ProductGroupKey.name));

		return productGroup;
	}

}
