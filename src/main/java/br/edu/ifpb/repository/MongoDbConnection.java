package br.edu.ifpb.repository;

import com.mongodb.MongoClient;

/**
 * Created by kieckegard on 03/09/2016.
 */
public class MongoDbConnection {

    private static MongoClient mongoClient;
    private static String host = "localhost";

    public static MongoClient getMongoClient() {
        if(mongoClient == null)
            mongoClient = new MongoClient(host);
        return mongoClient;
    }

}
