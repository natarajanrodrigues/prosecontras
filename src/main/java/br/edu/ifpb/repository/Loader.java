package br.edu.ifpb.repository;

import br.edu.ifpb.entity.Opinion;
import br.edu.ifpb.entity.Positioning;
import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.entity.UserProfile;
import br.edu.ifpb.enums.Status;
import br.edu.ifpb.utils.LocalDateTimeDeserializer;
import br.edu.ifpb.utils.LocalDateTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kieckegard on 03/09/2016.
 */
public class Loader {

    public static void main(String[] args) {
        /**
         * Testing Redis persistence
         */

        UserProfile user = new UserProfile();
        user.setEmail("pedro@email.com");
        user.setId(1L);
        user.setName("pedro");
        user.setPassword("123456");

        Topic topic = new Topic();
        topic.setName("Alguma coisa");
        topic.setId(302L);
        topic.setActor(user);
        topic.setPostedDateTime(LocalDateTime.now());
        topic.setDescription("Alguma coisa");

        Positioning forPositioning = new Positioning();
        forPositioning.setComment("Por causa disso, disso e disso");
        forPositioning.setTopic(topic);
        forPositioning.setStatus(Status.FOR);

        Positioning againstPositioning = new Positioning();
        againstPositioning.setComment("Por causa disso, disso e disso");
        againstPositioning.setTopic(topic);
        againstPositioning.setStatus(Status.AGAINST);

        Opinion opinion = new Opinion();
        opinion.setUser(user);
        opinion.setDateTime(LocalDateTime.now());

        opinion.addPositioning(forPositioning);
        opinion.addPositioning(againstPositioning);

        /**
         * Persisting opinion on cache. (Redis)
         */
        /*OpinionCacheRepository opinionCacheRepository1 = new OpinionCacheRepositoryRedisImpl();
        opinionCacheRepository1.save(opinion);*/

        /**
         * Removing cached opinion. (Redis)
         */
        /*OpinionCacheRepository opinionCacheRepository = new OpinionCacheRepositoryRedisImpl();
        opinionCacheRepository.remove(user.getEmail());*/

        /**
         * Converting an cached opinion to opinion object
         */
        /*OpinionCacheRepository opinionCacheRepository = new OpinionCacheRepositoryRedisImpl();

        String json = opinionCacheRepository.getCachedOpinion(user.getEmail());
        Opinion op = new Opinion().fromJsonString(json);

        System.out.println(op);*/

        /**
         * First, create database on Mongo using command line "use prosencontras;".
         * Second, create the collection that will be populated, in that case
         * our collection name is gonna be "opinion". So, once you're in prosencontras
         * database, you just have to call a command line "db.createCollection("opinion");"
         * to create the collection.
         *
         * The sample below will save an opinion. (MongoDB)
         */
        /*OpinionRepository opinionRepository = new OpinionRepositoryMongoDbImpl();
        opinionRepository.save(opinion);*/

        /**
         *  Retrieving opinion based on topic (MongoDB)
         **/
        /*OpinionRepository opinionRepository = new OpinionRepositoryMongoDbImpl();
        opinionRepository.listByTopic(topic).forEach(System.out::println);*/


        /**
         * Persisting Vote (Neo4J)
         */
        /*UserTopicRepository userTopicRepository = new UserTopicRepositoryNeo4jImpl();
        userTopicRepository.newRelationship(user,topic,Status.FOR);*/

        UserTopicRepositoryNeo4jImpl rep = new UserTopicRepositoryNeo4jImpl();
        //rep.newRelationship(user,topic,Status.AGAINST);
    }
}
