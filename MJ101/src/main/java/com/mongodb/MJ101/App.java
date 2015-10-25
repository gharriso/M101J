package com.mongodb.MJ101;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
 
import com.mongodb.*;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		MongoClient mongoClient = new MongoClient("localhost");
		MongoDatabase database = mongoClient.getDatabase("m101");
		MongoCollection<Document> collection = database.getCollection("test1");

		collection.drop();

		Document guy = new Document().append("name", "Guy").append("array", Arrays.asList("1", "2", "3"));
		Document chris = new Document().append("name", "Chris").append("array", Arrays.asList("1", "2", "3"));
		// collection.insertOne(guy);
		collection.insertMany(Arrays.asList(guy, chris));

		MongoCollection<Document> coll2 = database.getCollection("test2");
		for (int i = 0; i < 10; i++) {
			coll2.insertOne(new Document().append("x", i));
		}
		Document first = coll2.find().first();
		System.out.println(first.toJson());
		List<Document> all = coll2.find().into(new ArrayList<Document>());
		for (Document cur : all) {
			System.out.println(cur.toJson());
		}

		MongoCursor<Document> cursor = coll2.find().iterator();
		try {
			Document d = cursor.next();
			System.out.println(d.toJson());
		} finally {
			cursor.close();
		}

		System.out.println("count=" + coll2.count());
		// printJson();
		coll2.find();

		Bson filter = new Document().append("x", 0);
		System.out.println(coll2.find(filter).first().toJson());

		coll2.drop();
		for (int i = 0; i <= 10; i++) {
			for (int j = 0; j <= 10; j++) {
				coll2.insertOne(new Document("x", i).append("y", j));
			}
		}

		filter = new Document("x", 0).append("y", new Document("$gt", 7));
		for (Document d : coll2.find(filter)) {
			System.out.println(d.toJson());

		}
		System.out.println("----- Document  filters -----"); 
		filter = new Document("x", 0).append("y", new Document("$gte", 5).append("$lte", 8));
		for (Document d : coll2.find(filter)) {
			System.out.println(d.toJson());

		}
		System.out.println("----- static filters -----"); 
		filter = and(eq("x", 0), and(gte("y", 5), lte("y", 8)));
		for (Document d : coll2.find(filter)) {
			System.out.println(d.toJson());

		}
		System.out.println("----- better static filters -----"); 
		filter = and(eq("x", 0), gte("y", 5), lte("y", 8));
		for (Document d : coll2.find(filter)) {
			System.out.println(d.toJson());

		}
		
		System.out.println("----- projection out -----"); 
		filter = and(eq("x", 0), gte("y", 5), lte("y", 8));
		Bson proj=new Document("x",0).append("_id", 0); 
		for (Document d : coll2.find(filter).projection(proj)) {
			System.out.println(d.toJson());

		}
		
		System.out.println("----- projection in -----"); 
		filter = and(eq("x", 0), gte("y", 5), lte("y", 8));
		proj=new Document("y",1).append("_id", 0); 
		for (Document d : coll2.find(filter).projection(proj)) {
			System.out.println(d.toJson());

		}
		System.out.println("----- projections class -----"); 
		filter = and(eq("x", 0), gte("y", 5), lte("y", 8));
		proj=Projections.exclude("x","_id"); 
		for (Document d : coll2.find(filter).projection(proj)) {
			System.out.println(d.toJson());

		}
		System.out.println("----- more complex projections class -----"); 
		filter = and(eq("x", 0), gte("y", 5), lte("y", 8));
		proj=Projections.fields( Projections.exclude("_id"),
				Projections.include("y")); 
		for (Document d : coll2.find(filter).projection(proj)) {
			System.out.println(d.toJson());

		}
		System.out.println("----- more complex projections class static imports  -----"); 
		filter = and(eq("x", 0), gte("y", 5), lte("y", 8));
		proj= fields( exclude("_id"),
				include("y")); 
		for (Document d : coll2.find(filter).projection(proj)) {
			System.out.println(d.toJson());

		}
		mongoClient.close();
	}
}