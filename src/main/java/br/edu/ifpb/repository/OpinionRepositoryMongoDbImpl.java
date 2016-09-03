package br.edu.ifpb.repository;

import br.edu.ifpb.entity.Opinion;
import br.edu.ifpb.entity.Topic;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kieckegard on 02/09/2016.
 */
public class OpinionRepositoryMongoDbImpl implements OpinionRepository {

    private final MongoDatabase mongoDatabase;
    private final MongoCollection<Document> collection;

    public OpinionRepositoryMongoDbImpl() {
        mongoDatabase = MongoDbConnection.getMongoClient().getDatabase("prosecontras");
        collection = mongoDatabase.getCollection("opinion");
    }

    @Override
    public void save(Opinion opinion) {
        collection.insertOne(opinion.toDocument());
    }

    @Override
    public List<Opinion> listByTopic(Topic topic) {
        FindIterable<Document> iterable = this.collection.find(new Document("positionings.topic.id", topic.getId()));
        List<Opinion> opinions = new LinkedList<>();
        MongoCursor<Document> cursor = iterable.iterator();

        while(cursor.hasNext()) {
            opinions.add(new Opinion().fromDocument(cursor.next()));
        }

        return opinions;
    }
}
