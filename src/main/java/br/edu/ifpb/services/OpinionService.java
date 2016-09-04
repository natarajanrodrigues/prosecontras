package br.edu.ifpb.services;

import br.edu.ifpb.entity.Opinion;
import br.edu.ifpb.entity.Positioning;
import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.repository.*;
import org.neo4j.unsafe.impl.batchimport.cache.NumberArrayFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Created by kieckegard on 02/09/2016.
 */
@Service
public class OpinionService {


    private static OpinionRepository opinionRepository;
    private static OpinionCacheRepository opinionCacheRepository;
    private static UserTopicRepository userTopicRepository;

    @Autowired
    public OpinionService(OpinionCacheRepository opinionCacheRepository, OpinionRepository opinionRepository,
                          UserTopicRepository userTopicRepository) {
        this.opinionCacheRepository = opinionCacheRepository;
        this.opinionRepository = opinionRepository;
        this.userTopicRepository = userTopicRepository;

    }

    public void saveOpinionOnCache(Opinion opinion) {
        opinionCacheRepository.save(opinion);
    }

    public void removeCachedOpinion(Opinion opinion) {
        opinionCacheRepository.remove(opinion.getUser().getEmail());
    }

    public Opinion getCachedOpinion(String userEmail) {

        String opString = opinionCacheRepository.getCachedOpinion(userEmail);

        if (opString != null) {
            Opinion o = new Opinion();

            return o.fromJsonString(opString);
        }
        return null;
    }

    public void publishOpinion(Opinion opinion) {
        opinion.setDateTime(LocalDateTime.now());
        opinionRepository.save(opinion);

        //make persiste on neo4j
        for (Positioning p : opinion.getPositionings()) {
            userTopicRepository.newRelationship(opinion.getUser(), p.getTopic(), p.getStatus());
        }

    }

    public List<Opinion> getOpinionsByTopicOrderedByDate(Topic topic) {
        List<Opinion> opinions = opinionRepository.listByTopic(topic);
        Collections.sort(opinions);
        return opinions;
    }

}
