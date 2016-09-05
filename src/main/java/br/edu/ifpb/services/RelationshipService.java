package br.edu.ifpb.services;

import br.edu.ifpb.entity.Opinion;
import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.enums.Status;
import br.edu.ifpb.repository.UserTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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


    public Map<String, String> getPercentages(Topic topic) {

        Integer forCounts = userTopicRepository.getForQtde(topic);
        Integer againstCounts = userTopicRepository.getAgainstQtde(topic);

        Integer total = new Integer(forCounts + againstCounts);

        Map<String, String> result = new HashMap<>();

        if (!total.equals(new Integer(0))) {
            Float forPercentage = new Float(forCounts.floatValue() / total.floatValue() * 100);
            Float againstPercentage = new Float(100 - forPercentage.floatValue());

            result.put("against_number", String.format("%.2f", againstPercentage));
            result.put("for_number", String.format("%.2f", forPercentage));
            result.put("against_per", String.format("%.0f", againstPercentage));
            result.put("for_per", String.format("%.0f", forPercentage));

        } else {
            result.put("against_number", "0,0");
            result.put("for_number", "0,0");
            result.put("against_per", "0");
            result.put("for_per", "0");
        }

        return result;
    }

    public Map<String, String> getTrends(Topic startTopic, Status startStatus, Topic targetTopic, Status targetStatus) {

        Map<String, Integer> trends = userTopicRepository.getTrends(startTopic, startStatus, targetTopic, targetStatus);

        Integer forFounds = trends.get("found");
        Integer total = trends.get("total");

        Integer against = new Integer(total - forFounds);

        Map<String, String> result = new HashMap<>();

        if (!total.equals(new Integer(0))) {
            Float forPercentage = new Float(forFounds.floatValue() / total.floatValue() * 100);
            Float againstPercentage = new Float(100 - forPercentage.floatValue());

            result.put("against_number", String.format("%.2f", againstPercentage));
            result.put("for_number", String.format("%.2f", forPercentage));
            result.put("against_per", String.format("%.0f", againstPercentage));
            result.put("for_per", String.format("%.0f", forPercentage));

        } else {
            result.put("against_number", "0,0");
            result.put("for_number", "0,0");
            result.put("against_per", "0");
            result.put("for_per", "0");
        }

        return result;

    }



}
