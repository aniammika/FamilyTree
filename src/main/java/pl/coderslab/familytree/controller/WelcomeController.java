package pl.coderslab.familytree.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.familytree.util.ViewNames;

@Slf4j
@Controller
@Secured("ROLE_USER")
public class WelcomeController {


    public String retreiveUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        } else
            log.info("user not login");
        return null;
    }

    @GetMapping(path = "/")
    public String hello(Model model) {
        String username = retreiveUsername();
        model.addAttribute("username", username);
        return ViewNames.HOME;
    }
}
