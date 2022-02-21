package com.intergalaxy.translator.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;


@Controller
public class InterGalaxyController {

    @Value("${spring.application.name}")
    String appName;
    String translated;

    @Autowired
    TranslatorService translatorService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

    @RequestMapping(value="/translate",params="submit",method= RequestMethod.POST)
    public String submit(Model model,@ModelAttribute("notes") String notes) {
        model.addAttribute("appName", appName);
        String results=translatorService.validateInput(notes);
        model.addAttribute("translated", results);
        return "home";
    }

    @RequestMapping(value="/translate",params="clear",method= RequestMethod.POST)
    public String clear(Model model,@ModelAttribute("notes") String notes) {
        model.addAttribute("appName", appName);
        model.addAttribute("translated", "");
        translatorService.reset();
        return "home";
    }

}
