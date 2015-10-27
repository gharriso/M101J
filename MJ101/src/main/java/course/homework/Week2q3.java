package course.homework;

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

public class Week2q3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoClient mc=new MongoClient(); 
		MongoDatabase db=mc.getDatabase("students");
		MongoCollection<Document> c=db.getCollection("grades"); 
		Bson sorter=new Document("student_id",1).append("score",1); 
		int last_student_id=-1; 
		int delete_count=0; 
		int total_count=0; 
		for (Document doc: c.find( eq("type","homework")).sort(sorter)) {
			int student_id=doc.getInteger("student_id", -2); 
			//System.out.println(doc.toJson()); 
			System.out.println(student_id+" : "+doc.getDouble("score")); 
			total_count++; 
			if (student_id!=last_student_id) {
				//System.out.println("I can delete this one"); 
				delete_count++; 

				c.deleteOne(eq("_id",doc.get("_id")));
			}
			last_student_id=student_id; 
		}
		System.out.println("Start count:"+total_count+",Deleted:"+delete_count); 
	}

}
