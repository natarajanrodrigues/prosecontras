package br.edu.ifpb.services;

import br.edu.ifpb.entity.Opinion;
import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.repository.OpinionCacheRepository;
import br.edu.ifpb.repository.OpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * Created by kieckegard on 02/09/2016.
 */
public class OpinionService {

    @Autowired
    private OpinionRepository opinionRepository;
    @Autowired
    private OpinionCacheRepository opinionCacheRepository;

    public void saveOpinionOnCache(Opinion opinion) {
        opinionCacheRepository.save(opinion);
    }

    public void removeCachedOpinion(Opinion opinion) {
        opinionCacheRepository.remove(opinion.getUser().getEmail());
    }

    public void getCachedOpinion(String userEmail) {
        opinionCacheRepository.getCachedOpinion(userEmail);
    }

    public void publishOpinion(Opinion opinion) {
        opinionRepository.save(opinion);
    }

    public List<Opinion> getOpinionsByTopicOrderedByDate(Topic topic) {
        List<Opinion> opinions = opinionRepository.listByTopic(topic);
        Collections.sort(opinions);
        return opinions;
    }

}
