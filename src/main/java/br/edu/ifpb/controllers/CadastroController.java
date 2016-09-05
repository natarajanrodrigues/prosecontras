package br.edu.ifpb.controllers;

import br.edu.ifpb.entity.Opinion;
import br.edu.ifpb.entity.UserProfile;
import br.edu.ifpb.repository.UserRepository;
import br.edu.ifpb.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by susanneferraz on 01/09/16.
 */
@Controller
public class CadastroController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private HttpSession httpSession;

    @RequestMapping(value="/cadastrar", method=RequestMethod.GET)
    public String cadastro(UserValidator userValidator) {

        return "cadastro";

    }

    @RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
    public String cadastrar(@Valid UserValidator userValidator, BindingResult result, Model model) {
        model.addAttribute("name", userValidator.getName());
        model.addAttribute("email", userValidator.getEmail());
        model.addAttribute("password", userValidator.getPassword());

        if (result.hasErrors()) {

            return "cadastro";

        } else {

            UserProfile user = new UserProfile();
            user.setName(userValidator.getName());
            user.setEmail(userValidator.getEmail());
            user.setPassword(userValidator.getPassword());

            user = userRepository.save(user);
            Opinion opinion = new Opinion();
            opinion.setUser(user);

            if (user.getId() != 0) {

                httpSession.setAttribute("user", user);
                httpSession.setAttribute("opinion", opinion);
            }

            return "redirect:/home";
        }

    }

}
