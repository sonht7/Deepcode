package com.deepcode.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.deepcode.core.Constants;
import com.deepcode.core.MongoDB;
import com.deepcode.keys.FarmKey;
import com.deepcode.keys.RatingKey;
import com.deepcode.model.Farm;
import com.deepcode.model.Rating;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

public class FarmDb implements FarmDbInterface{
	
	public static final String COLLECTION_CATEGORY = "farm";

	public static FarmDb instance = null;

	public static FarmDb getInstance() {

		if (instance == null) {

			return new FarmDb();
		}

		return instance;
	}
	

	@Override
	public Farm add(Farm model) {
		// TODO Auto-generated method stub
				final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
						.getCollection(COLLECTION_CATEGORY);

				// TODO Auto-generated method stub
				int id = (int) MongoDB.getNextSequence(FarmKey.farmId);
				model.setId(id);
				Document document = new Document();

				/*
				 * document .append("_id", id); document.append("name", model.getName());
				 * document.append("createdDate", new Date());
				 */

				document.append(FarmKey.id, id);
				document.append(FarmKey.address, model.getAddress());
				document.append(FarmKey.country, model.getCountry());
				document.append(FarmKey.createdDate, new Date());
				document.append(FarmKey.farmManager, model.getFarmManager());
				document.append(FarmKey.image, model.getImage());
				document.append(FarmKey.isActive, true);
				document.append(FarmKey.isDelete, false);
				document.append(FarmKey.loginName, model.getLoginName());
				document.append(FarmKey.modifiedDate, null);
				document.append(FarmKey.name, model.getName());
				document.append(FarmKey.notes,model.getNotes());
				document.append(FarmKey.ownerName, model.getOwnerName());
				document.append(FarmKey.password, model.getPassword());
				document.append(FarmKey.phoneNumber, model.getPhoneNumber());
				document.append(FarmKey.province, model.getProvince());
				
				
				List<Document> listRatingDocument = new ArrayList<Document>();
				
				for (Rating r: model.getListRating()) {
					Document dRating = new Document();
					int idR = (int) MongoDB.getNextSequence(RatingKey.ratingId);
					dRating.append(RatingKey.id, idR);
					dRating.append(RatingKey.comment, r.getComment());
					dRating.append(RatingKey.score, r.getScore());
					dRating.append(RatingKey.userId, r.getUserId());
					
					listRatingDocument.add(dRating);
				}
				document.append(FarmKey.listRating, listRatingDocument);
				

				store.insertOne(document);

				return model;
				
	}

	@Override
	public boolean delete(int id) {

		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);

		Document filter = new Document(FarmKey.id, id);
		Document doc = new Document()
				.append(FarmKey.isDelete, true)
				.append(FarmKey.modifiedDate, new Date());
		
		Document newValues = new Document("$set", doc);
		store.findOneAndUpdate(filter, newValues);

		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Farm update(Farm model) {
		// TODO Auto-generated method stub
				final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
						.getCollection(COLLECTION_CATEGORY);
				// TODO Auto-generated method stub
				
				Document filter = new Document(FarmKey.isDelete,false)
						.append(FarmKey.id, model.getId());
				
				Document document = new Document();
				document.append(FarmKey.address, model.getAddress());
				document.append(FarmKey.country, model.getCountry());
				document.append(FarmKey.farmManager, model.getFarmManager());
				document.append(FarmKey.image, model.getImage());
				document.append(FarmKey.isActive, model.isActive());
				document.append(FarmKey.isDelete, model.isDelete());
				
				document.append(FarmKey.loginName, model.getLoginName());
				document.append(FarmKey.modifiedDate, new Date());
				document.append(FarmKey.name, model.getName());
				document.append(FarmKey.notes,model.getNotes());
				document.append(FarmKey.ownerName, model.getOwnerName());
				document.append(FarmKey.password, model.getPassword());
				document.append(FarmKey.phoneNumber, model.getPhoneNumber());
				document.append(FarmKey.province, model.getProvince());

				FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
				options.returnDocument(ReturnDocument.AFTER);
				options.upsert(false);
				Document newValues = new Document("$set", document);
				Document documentNew = store.findOneAndUpdate(filter, newValues, options);
				return toFarm(documentNew);
	}

	@Override
	public List<Farm> getAll() {
		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);
		// TODO Auto-generated method stub
		
		Document filter = new Document(FarmKey.isDelete, false);
		List<Farm> lsReturn = new ArrayList<Farm>();
		
		FindIterable<Document> iterable = store.find(filter);
		Iterator<Document> iter = iterable.iterator();
		
		while (iter.hasNext()) {
			Document doc = iter.next();
			lsReturn.add(toFarm(doc));
		}
		return lsReturn;
	}

	@Override
	public Farm getById(int id) {
		// TODO Auto-generated method stub
				final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
						.getCollection(COLLECTION_CATEGORY);
				
				// TODO Auto-generated method stub
				
				Document filter = new Document(FarmKey.isDelete, false)
						.append(FarmKey.id, id);
				FindIterable<Document> iterable = store.find(filter);
				Iterator<Document> iter = iterable.iterator();
				if (iter.hasNext()) {
					Document doc = iter.next();
					return toFarm(doc);
				}
				 return null;	
	}
	private static Farm toFarm(Document doc) {
		Farm farm = new Farm();

		farm.setActive(doc.getBoolean(FarmKey.isActive));
		farm.setAddress(doc.getString(FarmKey.address));
		farm.setCountry(doc.getString(FarmKey.country));
		farm.setDelete(doc.getBoolean(FarmKey.isDelete));
		farm.setFarmManager(doc.getString(FarmKey.farmManager));
		farm.setId(doc.getInteger(FarmKey.id));
		farm.setImage(doc.getString(FarmKey.image));
		farm.setLoginName(doc.getString(FarmKey.loginName));
		farm.setName(doc.getString(FarmKey.name));
		farm.setNotes(doc.getString(FarmKey.notes));
		farm.setOwnerName(doc.getString(FarmKey.ownerName));
		farm.setPassword(doc.getString(FarmKey.password));
		farm.setPhoneNumber(doc.getInteger(FarmKey.phoneNumber));
		farm.setProvince(doc.getString(FarmKey.province));
		
		//farm.setListRating(doc.getList(FarmKey.listRating, clazz));
		
		
		List<Document>  listRDocument = doc.getList(FarmKey.listRating, Document.class);
		//Gson gson = new Gson();
		
		List<Rating> listRating = new ArrayList<Rating>();
		
		
		for(Document document: listRDocument)
		{
			
			int id = document.getInteger(RatingKey.id);
			String comment = document.getString(RatingKey.comment);
			Double score = document.getDouble(RatingKey.score);
			int userId = document.getInteger(RatingKey.userId);
			
			Rating rating = new Rating();
			rating.setId(id);
			rating.setComment(comment);
			rating.setScore(score);
			rating.setUserId(userId);
			
			listRating.add(rating);
			
		}
		
		farm.setListRating(listRating);
		return farm;
	}


	@Override
	public Farm addRatingByFarmId(Rating model, int farmId) {
		// TODO Auto-generated method stub
		final MongoCollection<Document> store = MongoDB.getInstance(Constants.mongoDb_1)
				.getCollection(COLLECTION_CATEGORY);
		
		// TODO Auto-generated method stub
		
		Farm farm = FarmDb.getInstance().getById(farmId);
		List<Rating> listRatings = farm.getListRating();
		int id = (int)MongoDB.getNextSequence(RatingKey.ratingId);
		model.setId(id);
		listRatings.add(model);
		
		
		
		//// ADD theem vao list nua, ve tu nghi lam not @@@@@@@@@@@@@@@@@
		// Hoac xem cach khac trong link nay: https://stackoverflow.com/questions/44478275/mongodb-add-nested-arrays-using-java
		
	//	store.insertOne(document);
		return null;
	}


	@Override
	public boolean deleteRatingbyFarmId(int ratingId, int farmId) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Farm updateRatingByFarmId(Rating model, int farmId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Farm> getAllRatingByFarmId(int farmId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Farm getRatingByFarmId(int farmid) {
		// TODO Auto-generated method stub
		return null;
	}

}
