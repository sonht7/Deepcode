package com.deepcode.core;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

public class MongoDB {
	@SuppressWarnings("resource")
	public static MongoDatabase getInstance(String dbName) {
		MongoClient mongoClient = null;
		mongoClient = new MongoClient(new MongoClientURI("mongodb://" + Constants.DOMAIN_MONGO + ":27018"));
		MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
		return mongoDatabase;
	}

	public static DB getDB(String dbName) {
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient(
				new MongoClientURI("mongodb://" + Constants.DOMAIN_MONGO + ":27018"));
		@SuppressWarnings("deprecation")
		DB db = mongoClient.getDB(dbName);
		return db;
	}

	public static String getNextId(DB db, String seq_name) {
		String sequence_collection = "seq"; // the name of the sequence collection
		String sequence_field = "seq"; // the name of the field which holds the sequence

		DBCollection seq = db.getCollection(sequence_collection); // get the collection (this will create it if needed)

		// this object represents your "query", its analogous to a WHERE clause in SQL
		DBObject query = new BasicDBObject();     
		query.put("_id", seq_name); // where _id = the input sequence name

		// this object represents the "update" or the SET blah=blah in SQL
		DBObject change = new BasicDBObject(sequence_field, 1);
		DBObject update = new BasicDBObject("$inc", change); // the $inc here is a mongodb command for increment

		// Atomically updates the sequence field and returns the value for you
		DBObject res = seq.findAndModify(query, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
		return res.get(sequence_field).toString();
	}

	public static String getNextSqId(String seq_name) {
		DB db = MongoDB.getDB(Constants.mongoDb_1);
		return getNextId(db, seq_name);
	}

	public static long getNextSqIdToLong(String seq_name) {
		DB db = MongoDB.getDB(Constants.mongoDb_1);
		long id = Long.parseLong(getNextId(db, seq_name));
		return id;
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       

	public static long getNextSequence(String name) {
		MongoCollection<Document> store = getInstance(Constants.mongoDb_1).getCollection("sequences");

		Document filter = new Document().append("_id", name);

		FindOneAndUpdateOptions findOneAndUpdateOptions = new FindOneAndUpdateOptions();
		findOneAndUpdateOptions.returnDocument(ReturnDocument.AFTER);
		findOneAndUpdateOptions.upsert(true);

		Document updateValue = new Document().append("$inc", new Document("seq", (long) 1));

		Document result = store.findOneAndUpdate(filter, updateValue, findOneAndUpdateOptions);

		long seq = result.getLong("seq");

		return seq;
	}
}
