/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.controllers;

import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.entity.UserProfile;
import br.edu.ifpb.repository.TopicRepository;
import br.edu.ifpb.utils.PhotoUtils;
import br.edu.ifpb.validator.TopicValidator;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author kieckegard
 */
@Controller
public class TopicController {
    
    @Autowired
    private HttpSession httpSession;
    
    @Autowired
    private TopicRepository topicRepository;
    
    @RequestMapping(value = "/novoTopico", method=RequestMethod.GET)
    public String novoTopico(TopicValidator topicValidator) {
        return "novoTopico";
    }
    
    @RequestMapping(value="/novoTopico", method=RequestMethod.POST)
    public ModelAndView cadastrarNovoTopico(@Valid TopicValidator topicValidator, BindingResult result, Model model, @RequestParam("photo") MultipartFile  file) {
        
        String name = topicValidator.getName();
        String description = topicValidator.getDescription();
        UserProfile userProfile = (UserProfile) httpSession.getAttribute("user");
        
        model.addAttribute("name", name);
        model.addAttribute("description", description);
        
        ModelAndView modelAndView = new ModelAndView("novoTopico");

        if (result.hasErrors()) {
            return new ModelAndView("novoTopico");
        } else {
            
            Topic topic = new Topic();
            topic.setName(name);
            topic.setDescription(description);
            topic.setPostedDateTime(LocalDateTime.now());
            topic.setActor(userProfile);
            
            topic = topicRepository.save(topic);
            
            try {
                PhotoUtils.salvarFoto("img/topic/", topic.getId().toString()+".jpg", file.getInputStream());
                topic.setPhotoPath("img/topic/"+topic.getId()+".jpg");
                topicRepository.save(topic);
                modelAndView.getModel().put("succes", true);
            }
            catch (IOException ex) {
                
            }
            
        }
        
        return modelAndView;
    }
}
