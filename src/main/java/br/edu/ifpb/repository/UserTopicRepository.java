package br.edu.ifpb.repository;

import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.entity.UserProfile;
import br.edu.ifpb.enums.Status;
import java.util.Set;

/**
 * Created by kieckegard on 01/09/2016.
 */
public interface UserTopicRepository {

    void newRelationship(UserProfile user, Topic topic, Status status);
    Set<Long> getSuggestedTopicsByForTopics(Set<Topic> topics);
    Integer getForQtde(Topic topic);
    Integer getAgainstQtde(Topic topic);

}
