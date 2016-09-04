package br.edu.ifpb.repository;

import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.entity.UserProfile;
import br.edu.ifpb.enums.Status;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by kieckegard on 01/09/2016.
 */
@Repository
public class UserTopicRepositoryNeo4jImpl implements UserTopicRepository {

//    private String path = "C:/Users/kieckegard/Documents/Neo4j/default.graphdb";
    private String path = "/Users/susanneferraz/Dropbox/ADS 2016.1/neo4j";
    private File file;
    private GraphDatabaseService service;

    public UserTopicRepositoryNeo4jImpl() {
        file = new File(path);
        service = new GraphDatabaseFactory().newEmbeddedDatabase(file);
    }

    @Override
    public void newRelationship(UserProfile user, Topic topic, Status status) {

        try (Transaction tx = service.beginTx()) {

            persistUser(user);
            persistTopic(topic);

            Node userNode = getUserNodeById(user.getId());
            Node topicNode = getTopicNodeById(topic.getId());

            deleteSameRelationship(userNode,topic.getId());

            userNode.createRelationshipTo(topicNode, status);

            tx.success();
        }
    }

    @Override
    public void deleteSameRelationship(Node userNode, Long topicId) {
        for(Relationship r : userNode.getRelationships(Direction.OUTGOING)) {
            Long targetTopicId = (Long) r.getEndNode().getProperty("id");
            if(targetTopicId == topicId) {
                System.out.println("User is already in a relationship with topic " + targetTopicId + " deleting it... ");
                r.delete();
            }
        }
    }

    private void persistUser(UserProfile user) {

        Long id = user.getId();

        if (!isUserExists(id)) {
            Node personAux = service.createNode();
            personAux.addLabel(Label.label("user"));
            personAux.setProperty("id", id);
        }
    }

    private boolean isUserExists(Long id) {
        return (getUserNodeById(id) != null);
    }

    private Node getUserNodeById(Long id) {
        Node auxUser = service.findNode(Label.label("user"), "id", id);
        return auxUser;
    }

    private void persistTopic(Topic topic) {

        Long id = topic.getId();

        if (!isTopicExists(id)) {
            Node topicAux = service.createNode();
            topicAux.addLabel(Label.label("topic"));
            topicAux.setProperty("id", id);
        }
    }

    private boolean isTopicExists(Long id) {
        return (getTopicNodeById(id) != null);
    }

    private Node getTopicNodeById(Long id) {
        Node auxUser = service.findNode(Label.label("topic"), "id", id);
        return auxUser;
    }

    @Override
    public Set<Long> getSuggestedTopicsByForTopics(Set<Topic> topics) {

        Set<Long> suggestTopics = new TreeSet<>();

        try (Transaction tx = service.beginTx()) {

            for(Topic topic : topics){
                Node forTopic = getTopicNodeById(topic.getId());

                for(Relationship relationship : forTopic.getRelationships(Direction.OUTGOING,Status.FOR)) {

                    Node likedUser = relationship.getEndNode();

                    for(Relationship likedUserForTopicRelation : likedUser.getRelationships(Direction.INCOMING, Status.FOR)) {
                        Node suggestTopic = likedUserForTopicRelation.getEndNode();

                        suggestTopics.add((Long) suggestTopic.getProperty("id"));
                    }

                }

            }

            tx.success();

            return suggestTopics;
        }
    }

    @Override
    public Integer getForQtde(Topic topic) {

        Integer result;

        try (Transaction tx = service.beginTx()) {

            Node topicNode = getTopicNodeById(topic.getId());
            Iterable<Relationship> iterable = null;
            if (topicNode != null) {
                iterable = topicNode.getRelationships(Direction.INCOMING, Status.FOR);
                result = countRelationship(iterable.iterator());
            } else {
                result = new Integer(0);
            }

            tx.success();

        }

        return result;

    }

    @Override
    public Integer getAgainstQtde(Topic topic) {

        Integer result;

        try (Transaction tx = service.beginTx()) {

            Node topicNode = getTopicNodeById(topic.getId());
            Iterable<Relationship> iterable = null;
            if (topicNode != null) {
                iterable = topicNode.getRelationships(Direction.INCOMING, Status.AGAINST);
                result = countRelationship(iterable.iterator());
            } else {
                result = new Integer(0);
            }

            tx.success();

        }

        return result;


    }

    @Override
    public Integer getWhoVotedQtd() {
        ResourceIterator<Node> users = service.findNodes(Label.label("user"));
        Integer count=0;
        while(users.hasNext()) {
            count++;
            users.next();
        }

        return count;
    }

    private Integer countRelationship(Iterator<Relationship> iterator) {
        Integer forCount = 0;

        while(iterator.hasNext()) {
            forCount++;

            iterator.next();
        }

        return forCount;
    }
}
