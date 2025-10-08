package com.smartfoodsaver.controller;

import com.smartfoodsaver.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
  private final ChatService chatService;
  public ChatController(ChatService chatService) { this.chatService = chatService; }

  /** Simple POST: { "message": "text from user" } -> { "reply": "..." } */
  @PostMapping
  public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest req) {
    String reply = chatService.reply(req.message());
    return ResponseEntity.ok(new ChatResponse(reply));
  }

  // DTOs (you can move these into their own files)
  public record ChatRequest(String message) {}
  public record ChatResponse(String reply) {}
}
