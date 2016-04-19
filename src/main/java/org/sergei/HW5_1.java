package org.sergei;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergei_Doroshenko on 4/18/2016.
 */
public class HW5_1 {
    public static void main(String[] args) {
        final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost"));
        final MongoDatabase blogDatabase = mongoClient.getDatabase("test");
        final MongoCollection<Document> zipsCollection = blogDatabase.getCollection("zips");

        List<Document> pipeline = Arrays.asList(
                Document.parse("{$project:{first: {$substr:[\"$city\",0,1]}, pop:1 }}"),
                Document.parse("{$match: {first: {$regex: '[0-9]'}}}"),
                Document.parse("{$group: {_id:null, number: {$sum:\"$pop\"}}}")
        );

//        List<Document> post = zipsCollection.aggregate(pipeline).into(new ArrayList<>());
        List<Document> result = zipsCollection.aggregate(pipeline).into(new ArrayList<>());

        result.forEach(System.out::println);
    }
}
