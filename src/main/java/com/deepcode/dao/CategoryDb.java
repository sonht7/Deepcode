package com.deepcode.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.deepcode.core.Constants;
import com.deepcode.core.MongoDB;
import com.deepcode.keys.CategoryKey;
import com.deepcode.model.Category;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

public class CategoryDb implements CategoryDbInterface {

	public static final String COLLECTION_CATEGORY = "category";

	public static CategoryDb instance = null;

	public static CategoryDb getInstance() {

		if (instance == null) {

			return new CategoryDb();
		}

		return instance;
	}

	@Override
	public Category add(Category model) {
		System.out.println("ewqewqewqewqewqewqewq");
		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);

		// TODO Auto-generated method stub
		int id = (int) MongoDB.getNextSequence(CategoryKey.categoryId);
		model.setId(id);
		Document document = new Document();

		/*
		 * document .append("_id", id); document.append("name", model.getName());
		 * document.append("createdDate", new Date());
		 */

		document.append(CategoryKey.id, id);
		document.append(CategoryKey.name, model.getName());
		document.append(CategoryKey.createdDate, new Date());
		document.append(CategoryKey.modifiedDate, new Date());
		document.append(CategoryKey.isDelete, false);
		
		

		store.insertOne(document);

		return model;
	}

	@Override
	public boolean delete(int id) {
		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);

		Document filter = new Document(CategoryKey.id, id);
		Document doc = new Document()
				.append(CategoryKey.id, id)
				.append(CategoryKey.isDelete, true)
				.append(CategoryKey.modifiedDate, new Date());

		Document newValues = new Document("$set", doc);
		store.findOneAndUpdate(filter, newValues);

		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Category update(Category model) {
		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);
		// TODO Auto-generated method stub
		Document filter = new Document(CategoryKey.isDelete,false)
				.append(CategoryKey.id, model.getId());
		
		Document doc = new Document();
		if (model.getName() != null)
		doc.append(CategoryKey.name, model.getName());
		doc.append(CategoryKey.modifiedDate, new Date());

		FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
		options.returnDocument(ReturnDocument.AFTER);
		options.upsert(false);
		Document newValues = new Document("$set", doc);
		Document documentNew = store.findOneAndUpdate(filter, newValues, options);
		return toCategory(documentNew);
	}

	@Override
	public List<Category> getAll() {
		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);
		// TODO Auto-generated method stub
		Document filter = new Document(CategoryKey.isDelete, false);
		List<Category> lsReturn = new ArrayList<Category>();
		
		FindIterable<Document> iterable = store.find(filter);
		Iterator<Document> iter = iterable.iterator();
		
		while (iter.hasNext()) {
			Document doc = iter.next();
			lsReturn.add(toCategory(doc));
		}
		return lsReturn;
	}

	private static Category toCategory(Document doc) {
		Category category = new Category();
		category.setId(doc.getInteger(CategoryKey.id));
		category.setName(doc.getString(CategoryKey.name));
		category.setCreatedDate(doc.getDate(CategoryKey.createdDate));
		category.setModifiedDate(doc.getDate(CategoryKey.modifiedDate));

		return category;
	}

	@Override
	public Category getById(int id) {
		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);
		// TODO Auto-generated method stub
		
		Document filter = new Document(CategoryKey.isDelete, false)
				.append(CategoryKey.id, id);
		FindIterable<Document> iterable = store.find(filter);
		Iterator<Document> iter = iterable.iterator();
		if (iter.hasNext()) {
			Document doc = iter.next();
			return toCategory(doc);
		}
		
		
		
		
		return null;
	}

}
