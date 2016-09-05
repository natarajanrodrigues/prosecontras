package br.edu.ifpb.enums;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by kieckegard on 01/09/2016.
 */
public enum Status implements RelationshipType{
    FOR(1),AGAINST(2);

    int id;

    Status (int n) {
        id = n;
    }

    public int getId() {
        return id;
    }
}
