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

  @GetMapping("/leftover-ideas")
  public String leftoverIdeas() {
    return "leftover-ideas";
  }

  @GetMapping("/why-food-waste-matters")
  public String whyFoodWasteMatters() {
    return "why-food-waste-matters";
  }

  @GetMapping("/contact")
  public String contact() {
    return "contact";
  }
}
