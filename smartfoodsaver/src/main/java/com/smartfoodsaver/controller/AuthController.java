package com.smartfoodsaver.controller;

import com.smartfoodsaver.dto.RegistrationRequest;
import com.smartfoodsaver.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

  private final UserService userService;
  public AuthController(UserService userService) { this.userService = userService; }

  @GetMapping("/signin")
  public String signin() { return "signin"; }

  @GetMapping("/register")
  public String registerForm(Model model) {
    model.addAttribute("registration", new RegistrationRequest());
    return "register";
  }

  @PostMapping("/register")
  public String handleRegister(@Valid @ModelAttribute("registration") RegistrationRequest registration,
                               BindingResult binding,
                               Model model) {
    if (binding.hasErrors()) return "register";
    try {
      userService.register(registration);
      return "redirect:/signin?registered";
    } catch (IllegalArgumentException ex) {
      model.addAttribute("error", ex.getMessage());
      return "register";
    }
  }
}
