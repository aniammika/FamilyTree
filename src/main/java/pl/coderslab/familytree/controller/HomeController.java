package pl.coderslab.familytree.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.familytree.model.Home;
import pl.coderslab.familytree.service.HomeService;
import pl.coderslab.familytree.util.ViewNames;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping(path = "/add")
    public String addHome(Model model){
        Home home = new Home();
        model.addAttribute("home", home);
        return ViewNames.ADD_HOME;
    }

    @PostMapping(path = "/add")
    public String receiveForm(@Valid @ModelAttribute Home home, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ViewNames.ADD_HOME;
        } else {
            //jeśli jest wskazana osoba spokrewniona i relacja, stwórz relację podstawową + łańcuch metodą joinInRelation
            homeService.saveHome(home);
            return "success";
        }
    }

    @GetMapping(path = "/showAll")
    public String showAll(Model model){
        model.addAttribute("homes", allHomesList());
        return ViewNames.SHOW_ALL_HOMES;
    }

    @ModelAttribute("allHomesList")
    public List<Home> allHomesList(){
        List<Home> allHomesList = homeService.loadAllHomes();
        return allHomesList;
    }
}
