package br.edu.ifpb.controllers;

import br.edu.ifpb.entity.UserProfile;
import br.edu.ifpb.repository.UserRepository;
import br.edu.ifpb.utils.JsonView;
import br.edu.ifpb.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by susanneferraz on 01/09/16.
 */
@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private HttpSession httpSession;

    @PostMapping(value = "/login")
    @ResponseBody
    public ModelAndView login(String email, String password) {

        List<UserProfile> users = userRepository.findByEmailAndPassword(email, password);

        Map<String, String> map = new HashMap<>();

        if (users.isEmpty()) {

            map.put("error", "Usuário e senha inválidos");
            return JsonView.returnJsonFromMap(map);

        } else {

            UserProfile user = users.get(0);

            httpSession.setAttribute("user", user);

            return new ModelAndView("redirect:/home");

        }
    }

    @GetMapping(value = "/logout")
    public ModelAndView logout(){

        httpSession.invalidate();

        return new ModelAndView("redirect:/");
    }



}
