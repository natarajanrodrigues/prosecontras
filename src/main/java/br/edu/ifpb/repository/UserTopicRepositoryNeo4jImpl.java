package br.edu.ifpb.repository;

import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.entity.UserProfile;
import br.edu.ifpb.enums.Status;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.api.LegacyIndexApplierLookup;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;

/**
 * Created by kieckegard on 01/09/2016.
 */
@Repository
public class UserTopicRepositoryNeo4jImpl implements UserTopicRepository {

    //private String path = "C:/Users/kieckegard/Documents/Neo4j/default.graphdb";
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

            deleteSameRelationship(userNode, topic.getId());

            userNode.createRelationshipTo(topicNode, status);

            tx.success();
        }
    }

    private void deleteSameRelationship(Node userNode, Long topicId) {
        for (Relationship r : userNode.getRelationships(Direction.OUTGOING)) {
            Long targetTopicId = (Long) r.getEndNode().getProperty("id");
            if (targetTopicId.equals(topicId)) {
                r.delete();
            } else {
                System.out.println(targetTopicId + " != " + topicId);
            }
        }
    }

    @Override
    public Map<String, Integer> getTrends(Topic startTopic, Status startStatus, Topic targetTopic, Status targetStatus) {

        Map<String, Integer> result = new HashMap<>();

        try (Transaction tx = service.beginTx()) {
            Node startTopicNode = getTopicNodeById(startTopic.getId());

            List<Node> startUsersNodes = getUserNodesByTopic(startTopicNode, startStatus);

            Integer usersNodeQtde = startUsersNodes.size();
            System.out.println("Users in relationship with topic " + startTopic.getId() + " : " + usersNodeQtde);
            Integer usersNodeQtdeTarget = 0;


            for (Node startUserNode : startUsersNodes) {

                for (Relationship targetRelationship : startUserNode.getRelationships(Direction.OUTGOING, targetStatus)) {
                    Long targetTopicId = (Long) targetRelationship.getEndNode().getProperty("id");
                    System.out.println("Found " + targetStatus + " relationship with topic " + targetTopicId);
                    if (targetTopic.getId().equals(targetTopicId))
                        usersNodeQtdeTarget++;
                }
            }

            System.out.println("UsersNodeQtdeTarget: " + usersNodeQtdeTarget);

            tx.success();

            result.put("total", usersNodeQtde);
            result.put("found", usersNodeQtdeTarget);

            return result;
        }
    }

    private List<Node> getUserNodesByTopic(Node topic, Status status) {
        List<Node> users = new LinkedList<>();
        for (Relationship rel : topic.getRelationships(Direction.INCOMING, status)) {
            System.out.println("Found relationship!");
            Node userNode = rel.getStartNode();
            System.out.println("id: " + userNode.getProperty("id"));
            users.add(userNode);
        }

        return users;
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
    public Set<Long> getSuggestedTopicsByUser(UserProfile user) {

        try (Transaction tx = service.beginTx()) {

            Node userNode = getUserNodeById(user.getId());

            Set<Long> forTopicsId = getForTopicsByUser(userNode);
            Set<Long> againstTopicsId = getAgainstTopicsByUser(userNode);

            Set<Long> suggestedTopics = getSuggestedTopicsByForTopics(forTopicsId, againstTopicsId, user.getId());

            tx.success();

            return suggestedTopics;
        }
    }

    private Set<Long> getForTopicsByUser(Node userNode) {

        Set<Long> topicsId = new TreeSet<>();

        Iterable<Relationship> relationships = userNode.getRelationships(Direction.OUTGOING, Status.FOR);
        Iterator<Relationship> iterator = relationships.iterator();

        while (iterator.hasNext()) {
            topicsId.add((Long) iterator.next().getEndNode().getProperty("id"));
        }

        return topicsId;
    }

    private Set<Long> getAgainstTopicsByUser(Node userNode) {
        Set<Long> topicsId = new TreeSet<>();

        Iterable<Relationship> relationships = userNode.getRelationships(Direction.OUTGOING, Status.AGAINST);
        Iterator<Relationship> iterator = relationships.iterator();

        while (iterator.hasNext()) {
            topicsId.add((Long) iterator.next().getEndNode().getProperty("id"));
        }

        return topicsId;
    }

    private Set<Long> getSuggestedTopicsByForTopics(Set<Long> forTopics, Set<Long> againstTopics, Long userId) {

        Set<Long> suggestTopics = new TreeSet<>();

        for (Long topicId : forTopics) {
            Node forTopic = getTopicNodeById(topicId);

            for (Relationship relationship : forTopic.getRelationships(Direction.INCOMING, Status.FOR)) {

                Node likedUser = relationship.getStartNode();

                Long likedUserId = (Long) likedUser.getProperty("id");

                if(!likedUserId.equals(userId)) {

                    for (Relationship likedUserForTopicRelation : likedUser.getRelationships(Direction.OUTGOING, Status.FOR)) {

                        Node suggestTopic = likedUserForTopicRelation.getEndNode();
                        Long suggestTopicId = (Long) suggestTopic.getProperty("id");

                        if (!forTopics.contains(suggestTopicId) && !againstTopics.contains(suggestTopicId))
                            suggestTopics.add(suggestTopicId);
                    }
                }

            }

        }

        return suggestTopics;
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
        Integer count = 0;
        while (users.hasNext()) {
            count++;
            users.next();
        }

        return count;
    }

    private Integer countRelationship(Iterator<Relationship> iterator) {
        Integer forCount = 0;

        while (iterator.hasNext()) {
            forCount++;

            iterator.next();
        }

        return forCount;
    }
}
