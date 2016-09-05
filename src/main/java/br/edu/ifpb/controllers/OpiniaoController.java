/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.controllers;

import br.edu.ifpb.entity.Opinion;
import br.edu.ifpb.entity.Positioning;
import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.entity.UserProfile;
import br.edu.ifpb.enums.Status;
import br.edu.ifpb.services.OpinionService;
import br.edu.ifpb.services.TopicoService;
import br.edu.ifpb.utils.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kieckegard
 */
@Controller
public class OpiniaoController {
    
    @Autowired
    private HttpSession httpSession;

    @Autowired
    TopicoService topicoService;

    @Autowired
    OpinionService opinionService;


    @RequestMapping(value = "/opiniao", method=RequestMethod.GET)
    public ModelAndView viewOpinion(){

        ModelAndView mav = new ModelAndView("opiniao");

        mav.addObject("opinion", httpSession.getAttribute("opinion"));

        return mav;
    }

    private void saveOpinionOnCache(HttpSession session, Opinion opinion){

        opinionService.saveOpinionOnCache(opinion);
        httpSession.setAttribute("opinion", opinion);

    }

    @RequestMapping(value = "/positioningadd", method=RequestMethod.GET)
    public ModelAndView addPos(@RequestParam ("id") String idTopico){

        Topic topic = topicoService.findById(idTopico);

        Opinion opiniao = (Opinion) httpSession.getAttribute("opinion");
        if (!opiniao.hasTopic(topic)) {
            Positioning newPositioning = new Positioning();

            newPositioning.setTopic(topic);

            opiniao.addPositioning(newPositioning);

            saveOpinionOnCache(httpSession, opiniao);
        }



        ModelAndView mav = new ModelAndView("opiniao");

        return mav;
    }

    @RequestMapping(value = "/positioningremove", method=RequestMethod.GET)
    public ModelAndView removePos(@RequestParam ("id") String idTopico){

        Opinion opiniao = (Opinion) httpSession.getAttribute("opinion");


        opiniao.remotePositioningByTopic(Long.parseLong(idTopico));

        saveOpinionOnCache(httpSession, opiniao);

        ModelAndView mav = new ModelAndView("redirect:/opiniao");

        return mav;
    }

    @RequestMapping(value = "/positioningcontent", method=RequestMethod.GET)
    @ResponseBody
    public ModelAndView posContente(@RequestParam ("id") String idTopico, Model model){

        Opinion opiniao = (Opinion) httpSession.getAttribute("opinion");

        Positioning pos = opiniao.getPositioningByTopic(Long.parseLong(idTopico));

        Map<String, String> map = new HashMap<>();

        if (pos.getStatus() != null)
            map.put("posicao", pos.getStatus().toString());
        if (pos.getComment() != null)
            map.put("comment", pos.getComment());

        map.put("topicId", pos.getTopic().getId().toString());
        return JsonView.returnJsonFromMap(map);

    }

    @RequestMapping(value = "/positioningsave", method=RequestMethod.POST)
    public ModelAndView removePos(@RequestParam ("id") String idTopico, @RequestParam("motivos") String comment, @RequestParam("posicao") String status){

        Opinion opiniao = (Opinion) httpSession.getAttribute("opinion");

        Positioning pos = opiniao.getPositioningByTopic(Long.parseLong(idTopico));

        pos.setComment(comment);
        if (status.equals("FOR"))
            pos.setStatus(Status.FOR);
        else
            pos.setStatus(Status.AGAINST);

        saveOpinionOnCache(httpSession, opiniao);

        ModelAndView mav = new ModelAndView("redirect:/opiniao");

        return mav;
    }

    @RequestMapping(value = "/publish")
    public String publishOpinion(Model model){

        Opinion opiniao = (Opinion) httpSession.getAttribute("opinion");

        boolean ready = false;
        if (opiniao.getPositionings().size() != 0) {
            ready = true;
            for (Positioning p: opiniao.getPositionings()) {
                if (p.getStatus() == null )
                    ready = false;
            }
        }

        if (ready) {

            opinionService.publishOpinion(opiniao);
            Opinion newOpinion = new Opinion();
            newOpinion.setUser(((UserProfile) httpSession.getAttribute("user")));
            saveOpinionOnCache(httpSession, newOpinion);
            return "home";

        } else {
            if (opiniao.getPositionings().size() > 0)
                model.addAttribute("error", "Não foi possível publicar sua opinião. Ainda existe posicionamento a ser editado.");
            return "opiniao";
        }

    }


    @RequestMapping(value = "/timeline", method=RequestMethod.GET)
    public ModelAndView timeline(){

        List<Opinion> userOpinion = opinionService.getOpinionsByUserOrderedByDate((UserProfile) httpSession.getAttribute("user"));
//        if (userOpinion != null)

        ModelAndView mav = new ModelAndView("timeline");

        mav.addObject("userOpinions", userOpinion);

        return mav;
    }

}
