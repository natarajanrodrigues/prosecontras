package br.edu.ifpb.services;

import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.entity.UserProfile;
import br.edu.ifpb.enums.Status;
import br.edu.ifpb.repository.ImageTopicRepository;
import br.edu.ifpb.repository.TopicRepository;
import br.edu.ifpb.repository.UserTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static br.edu.ifpb.repository.ImageTopicRepository.saveTopicImage;

/**
 * Created by susanneferraz on 03/09/16.
 */
@Service
public class TopicoService {


    private static TopicRepository topicRepository;
    @Autowired
    private static UserTopicRepository userTopicRepository;

    @Autowired
    public TopicoService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic saveTopic(String name, String description, UserProfile userProfile, MultipartFile photo) {
        Topic topic = new Topic();
        topic.setName(name);
        topic.setDescription(description);
        topic.setPostedDateTime(LocalDateTime.now());
        topic.setActor(userProfile);

        topic = topicRepository.save(topic);

        try {

            String objid = saveTopicImage(photo, topic.getId());
            topic.setPhotoPath(objid);

            topicRepository.save(topic);

        }
        catch (IOException ex) {

        }

        return topic;
    }

    public List<Topic> findByNameLikeIgnoreCase (String parameterName) {
        return topicRepository.findByNameLikeIgnoreCase("%" + parameterName + "%");
    }

    public Topic findById(String id) {
        return topicRepository.findById(Long.parseLong(id));
    }

    public void voteTopic(UserProfile user, Topic topic, Status status) {
        userTopicRepository.newRelationship(user,topic,status);
    }

    public Float getForPercent(Topic topic) {
        Integer forQtde = userTopicRepository.getForQtde(topic);
        Integer userQtde = userTopicRepository.getWhoVotedQtd();

        return ((float) forQtde/userQtde)*100;
    }

    public Float getAgainstPercent(Topic topic) {
        Integer againstQtde = userTopicRepository.getAgainstQtde(topic);
        Integer userQtde = userTopicRepository.getWhoVotedQtd();

        return ((float) againstQtde/userQtde)*100;
    }

}
