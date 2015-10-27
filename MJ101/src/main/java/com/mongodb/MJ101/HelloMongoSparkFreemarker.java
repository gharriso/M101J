package com.mongodb.MJ101;

import java.io.File;
import java.io.StringWriter;

import java.util.HashMap;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
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
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;
import com.mongodb.*;
import java.util.*;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class HelloMongoSparkFreemarker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration configuration = new Configuration();
		// configuration.setClassForTemplateLoading(HelloWorldFreemarker.class,"/templates");

		MongoClient mongo=new MongoClient();
		MongoDatabase db=mongo.getDatabase("course");
		final MongoCollection<Document> collection=db.getCollection("hello"); 
		collection.drop();
		collection.insertOne(new Document("name","guy from mongo").append("title", "Hello"));
		
		 
		try {;
			configuration.setDirectoryForTemplateLoading(new File("templates"));
			Template helloTemplate = configuration.getTemplate("hello.ftl");
			StringWriter writer = new StringWriter();
			Document doc=collection.find().first();
			 
		 
			helloTemplate.process(doc, writer);
			System.out.println(writer);
			 Spark.get(new Spark.Route("/")); 
			 
			

		} catch (

		Exception e)

		{
			e.printStackTrace();
			System.exit(1);
		}

	}

}
