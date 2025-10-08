package com.smartfoodsaver.controller;

import com.smartfoodsaver.dto.ContactForm;
import com.smartfoodsaver.repository.ContactMessageRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.smartfoodsaver.model.ContactMessage;
import com.smartfoodsaver.service.ContactService;

@Controller
public class PageController {

  private final ContactService contactService;
  private final ContactMessageRepository repo;

  public PageController(ContactService contactService, ContactMessageRepository repo) {
    this.contactService = contactService;
    this.repo = repo;
  }

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
  public String contact(Model model) {
    if (!model.containsAttribute("contactForm")) {
      model.addAttribute("contactForm", new ContactForm());
    }
    return "contact";
  }

  @PostMapping("/contact")
  public String handleContact(
    @Valid @ModelAttribute("contactForm") ContactForm form,
    BindingResult errors,
    RedirectAttributes ra
  ) {
    if (errors.hasErrors()) {
      ra.addFlashAttribute("org.springframework.validation.BindingResult.contactForm", errors);
      ra.addFlashAttribute("contactForm", form);
      return "redirect:/contact";
    }

    ContactMessage msg = new ContactMessage();
    msg.setName(form.getName());
    msg.setEmail(form.getEmail());
    msg.setMessage(form.getMessage());
    contactService.save(msg);

    ra.addFlashAttribute("contactSuccess", "Thank you! We’ve received your message.");
    return "redirect:/contact";
  }

  @GetMapping("/admin/messages")
  public String listMessages(Model model) {
    model.addAttribute("messages", repo.findAll());
    return "admin-messages";
  }

}
