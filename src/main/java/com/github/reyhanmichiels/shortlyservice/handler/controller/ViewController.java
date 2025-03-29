package com.github.reyhanmichiels.shortlyservice.handler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    @GetMapping(path = "/r/{shortUrl}/input-password")
    public String showInputPassword(
            @PathVariable String shortUrl,
            @RequestParam(required = false) String error,
            Model model
    ) {
        model.addAttribute("url", shortUrl);
        model.addAttribute("error", error);
        return "input-password";
    }

}
