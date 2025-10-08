package com.smartfoodsaver.service;

import org.springframework.stereotype.Service;

@Service
public class ChatService {
  public String reply(String userText) {
    if (userText == null || userText.isBlank()) return "Hey! Ask me about meal planning, storage tips, or leftovers 😊";

    String q = userText.toLowerCase();
    // Tiny rule-based starter (replace with LLM/RAG later)
    if (q.contains("store") && q.contains("milk")) {
      return "Keep milk at 0–4°C on the middle shelf (not the door). Once opened, use within 3–5 days.";
    }
    if (q.contains("bread")) {
      return "Best: freeze sliced bread in airtight bags. Avoid the fridge (it stales faster).";
    }
    if (q.contains("leftover") || q.contains("left overs")) {
      return "Cool leftovers quickly (within 2 hours), refrigerate in shallow containers, eat within 2–3 days, or freeze.";
    }
    if (q.contains("meal plan") || q.contains("plan meals")) {
      return "Start with perishables first. Pick 2–3 core proteins/veg and plan around them. I can suggest recipes from what you have.";
    }
    return "I can help with storage tips (milk, bread, rice), meal planning, and using leftovers. What do you want to know?";
  }
}
