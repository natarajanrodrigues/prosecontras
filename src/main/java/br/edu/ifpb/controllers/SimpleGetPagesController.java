/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.controllers;

import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Victor Hugo <victor.hugo.origins@gmail.com>
 */
@Controller
public class SimpleGetPagesController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    TopicRepository topicRepository;


    @GetMapping("/")
    public ModelAndView index() {

        if (httpSession.getAttribute("user") == null) {

            return new ModelAndView("index");

        } else {

            return new ModelAndView("redirect:/home");
        }

    }

    @GetMapping("/home")
    public ModelAndView home() {

        ModelAndView mav = new ModelAndView("home");

        List<Topic> topics = topicRepository.findAll();
        Collections.sort(topics, Topic.Comparators.DATA);
        
        mav.addObject("topics", topics);

        return mav;

    }




}
