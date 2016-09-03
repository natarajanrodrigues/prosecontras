package br.edu.ifpb.entity;

import org.bson.Document;

/**
 * Created by kieckegard on 02/09/2016.
 */
public interface MongoDbObject<T> {

    Document toDocument();

    T fromDocument(Document document);

}
