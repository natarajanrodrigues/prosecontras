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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private HttpServletRequest request;


    @RequestMapping(value = "/topic", method=RequestMethod.GET)
    public ModelAndView viewTopico(@RequestParam ("id") String idTopico){

        Topic topic = topicRepository.findById(Integer.parseInt(idTopico));

        ModelAndView mav = new ModelAndView("topico");

        mav.addObject("topic", topic);

        return mav;
    }

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
                String photoPath = PhotoUtils.salvarFoto("src/main/resources/static/img/topic/", topic.getId().toString()+file.getOriginalFilename(), file.getInputStream());
                topic.setPhotoPath(photoPath);

                topicRepository.save(topic);
                modelAndView.getModel().put("succes", true);
            }
            catch (IOException ex) {
                
            }
            
        }
        
        return modelAndView;
    }


    @RequestMapping(value = "/topicsearch", method = RequestMethod.POST)
    public ModelAndView buscar(@RequestParam("parametro") String parametroBusca) {

        ModelAndView mav = new ModelAndView("topicsearch");

        mav.addObject("parametro", parametroBusca);

        mav.addObject("topics", topicRepository.findByNameLikeIgnoreCase("%" + parametroBusca + "%"));

        return mav;

    }

}
