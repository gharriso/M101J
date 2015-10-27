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

public class HelloWorldFreemarker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration configuration = new Configuration();
		// configuration.setClassForTemplateLoading(HelloWorldFreemarker.class,"/templates");

		try {
			configuration.setDirectoryForTemplateLoading(new File("templates"));
			Template helloTemplate = configuration.getTemplate("HelloWorldFreeMarker.ftl");
			StringWriter writer = new StringWriter();
			HashMap<String, Object> helloMap = new HashMap<String, Object>();
			helloMap.put("name", "Guy");
			helloMap.put("title", "Hello from Guy with Spark and Freemarker");
			helloTemplate.process(helloMap, writer);
			System.out.println(writer);
			//Spark.get("/hello", (req, res) -> writer);
			Spark.get("/", new Route( {
	            public Object handle(final Request request, final Response response){
	            return "Hello World";
	            }
			}));
	         Spark.get("/echo/:thing", new Route() {
	                public Object handle(final Request request, final Response response){
	                return request.params(":thing");
	              }
	        });
			Spark.get("/", new Route() {  
				public Object handle(final Request request, final Response response){
	            return "Hello World from the root handler";
	            }});
			Spark.get("/echo/:thing", new Route() {  
				public Object handle(final Request request, final Response response){
					return request.params(":thing");
	            }});

		} catch (

		Exception e)

		{
			e.printStackTrace();
			System.exit(1);
		}

	}

}
