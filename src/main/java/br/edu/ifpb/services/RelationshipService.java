package br.edu.ifpb.services;

import br.edu.ifpb.entity.Opinion;
import br.edu.ifpb.repository.UserTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by susanneferraz on 04/09/16.
 */
@Service
public class RelationshipService {

    private static UserTopicRepository userTopicRepository;

    @Autowired
    public RelationshipService(UserTopicRepository userTopicRepository) {
        this.userTopicRepository = userTopicRepository;
    }


    public void saveRelationships(Opinion opinion) {

    }


}
