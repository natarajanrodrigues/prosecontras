package br.edu.ifpb.services;

import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.entity.UserProfile;
import br.edu.ifpb.repository.ImageTopicRepository;
import br.edu.ifpb.repository.TopicRepository;
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
        return topicRepository.findById(Integer.parseInt(id));
    }

}
