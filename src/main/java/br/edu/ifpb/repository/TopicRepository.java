/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.repository;

import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.entity.UserProfile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kieckegard
 */
public interface TopicRepository extends JpaRepository<Topic, Integer>{

    Topic save(Topic topic);
    
    List<Topic> findByName(String name);
    
    List<Topic> findByActor(UserProfile actor);
    
}
