package com.smartfoodsaver.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

  @GetMapping("/")
  public String home(Authentication auth) {

    System.out.println("### Home hit. Authenticated user = " + (auth == null ? "null" : auth.getName()));
    return "home";
  }

  @GetMapping("/meal-planner")
  public String mealPlanning() {
    return "meal-planner";
  }

  @GetMapping("/storage-tips")
  public String storageTips() {
    return "storage-tips";
  }

  @GetMapping("/leftovers")
  public String leftoverIdeas() {
    return "leftovers";
  }

  @GetMapping("/about")
  public String about() {
    return "about";
  }

  @GetMapping("/contact")
  public String contact() {
    return "contact";
  }

}
