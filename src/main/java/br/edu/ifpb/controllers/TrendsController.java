package br.edu.ifpb.controllers;

import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.entity.UserProfile;
import br.edu.ifpb.enums.Status;
import br.edu.ifpb.services.RelationshipService;
import br.edu.ifpb.services.TopicoService;
import br.edu.ifpb.utils.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by susanneferraz on 05/09/16.
 */
@Controller
public class TrendsController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    TopicoService topicoService;

    @Autowired
    RelationshipService relationshipService;

    @RequestMapping(value = "/trends", method= RequestMethod.GET)
    public ModelAndView enterTrendsArea(){

        ModelAndView mav = new ModelAndView("trends");

        mav.addObject("topics", topicoService.findByNameLikeIgnoreCase(""));

        return mav;
    }

    @RequestMapping(value = "/trendsearch", method= RequestMethod.POST)
    public ModelAndView trendsArea(@RequestParam("targetTopic") String topicTargetId, @RequestParam("statusStart") String statusStart,
                                   @RequestParam("startTopic") String topicStartId){

        ModelAndView mav = new ModelAndView("trendsearch");

        Topic startTopic    = topicoService.findById(topicStartId);
        Topic targetTopic   = topicoService.findById(topicTargetId);

        mav.addObject("targetTopic", targetTopic.getName());
        if (statusStart.equals("FOR"))
            mav.addObject("statusStart", "a favor");
        else
            mav.addObject("statusStart", "contra");
        mav.addObject("startTopic", startTopic.getName());


        Map<String, String> resultTrend = relationshipService.getTrends(startTopic, Status.valueOf(statusStart), targetTopic, Status.FOR);

        mav.addObject("percentages", resultTrend);


        return mav;

    }

    @RequestMapping(value = "/sugestoes", method= RequestMethod.GET)
    public ModelAndView sugestoes(){

        ModelAndView mav = new ModelAndView("sugestoes");

        UserProfile user = (UserProfile) httpSession.getAttribute("user");
        mav.addObject("topics", relationshipService.getSuggestions(user));

        return mav;
    }

}
