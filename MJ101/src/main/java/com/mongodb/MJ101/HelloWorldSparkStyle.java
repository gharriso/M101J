package com.mongodb.MJ101;

import spark.Spark;
import spark.Route; 

 

public class HelloWorldSparkStyle {
	public static void main ( String[] args ) {
		 
		Spark.get("/hello", (req, res) -> "Hello World from Guy's Spark");
	}
}
